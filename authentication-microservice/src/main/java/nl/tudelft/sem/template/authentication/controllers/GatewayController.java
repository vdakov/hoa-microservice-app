package nl.tudelft.sem.template.authentication.controllers;

import nl.tudelft.sem.template.commons.entities.ElectionVote;
import nl.tudelft.sem.template.commons.entities.RequirementVote;
import nl.tudelft.sem.template.commons.models.ActivityModel;
import nl.tudelft.sem.template.commons.models.CreateReportModel;
import nl.tudelft.sem.template.commons.models.CreateRequirementModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@RestController
@RequestMapping("/gateway")
public class GatewayController {

    /**
     * Instantiates a new UsersController.
     */
    @Autowired
    public GatewayController() {
    }

    public HttpEntity buildEntity(String token, Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity<>(body, headers);
        return entity;
    }

    /**
     * Routing method used to retrieve activities for a certain HOA, if authorized.
     *
     * @return The responseEntity passed back from the method in the HOA microservice.
     */
    @GetMapping("/pnb/allActivities")
    public ResponseEntity allActivities() {
        //Get bearer token
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization");
        System.out.println(token);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(token, null);
        String url = "http://localhost:8090/pnb/allActivities";
        return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
    }

    /**
     * Routing method used for retrieving all PNB activities for a certain HOA.
     *
     * @param hoaId The ID of the HOA to retrieve activities from.
     * @return the responseEntity passed back from the method in the HOA microservice.
     */
    @GetMapping("/pnb/activitiesForHoa/{HoaId}")
    public ResponseEntity activitiesForHoa(@PathVariable("HoaId") int hoaId) {
        //Get bearer token
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(token, null);
        String url = "http://localhost:8090/pnb/activitiesForHoa/" + hoaId;

        return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
    }

    /**
     * Routing method used for creating an activity.
     *
     * @param model the ActivityModel containing the information of the activity to create.
     * @return the responseEntity passed back from the method in the HOA microservice.
     */
    @PostMapping("/pnb/createActivity")
    public ResponseEntity<ActivityModel> createActivity(@RequestBody ActivityModel model) {
        //Get bearer token
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(token, null);
        String url = "http://localhost:8090/pnb/createActivity";

       return restTemplate.exchange(url, HttpMethod.POST, entity, ActivityModel.class);
    }

    /**
     * Routing method used for casting a vote in an election.
     *
     * @param vote The vote to cast.
     * @param user_id The ID of the user that casts the vote.
     * @param hoa_id The ID of the HOA for which the election is running.
     * @return the responseEntity passed back from the method in the HOA microservice.
     */
    @PostMapping("/users/castVoteElection/{userId}/{hoaId}")
    public ResponseEntity submitVoteElection(@RequestBody ElectionVote vote,
                                             @PathVariable("userId") int user_id,
                                             @PathVariable("hoaId") int hoa_id) {
        //Get bearer token
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(token, vote);
        String url = "http://localhost:8090/api/users/submitVoteElection/" + user_id + "/" + hoa_id;

        return restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
    }

    /**
     * Routing method used for changing your vote in an election
     * @param vote the new vote to submit.
     * @param userId the ID of the user that is submitting the vote.
     * @param hoaId the ID of the hoa to submit the vote to.
     * @return the ResponseEntity passed back from the method in the HOA microservice.
     */
    @PutMapping("/users/changeVoteElection/{userId}/{hoaId}")
    public ResponseEntity changeVoteElection(@RequestBody ElectionVote vote,
                                             @PathVariable("userId") int userId,
                                             @PathVariable("hoaId") int hoaId) {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(token, vote);
        String url = "http://localhost:8090/api/users/changeVoteElection/" + userId + "/" + hoaId;

        return restTemplate.exchange(url, HttpMethod.PUT, entity, Object.class);
    }

    /**
     * Routing method used for casting a vote about a change in requirements..
     *
     * @param vote The vote to cast.
     * @param user_id The ID of the user that casts the vote.
     * @return the responseEntity passed back from the method in the HOA microservice.
     */
    @PostMapping("/users/submitRequirementsVote/{userId}/{hoaId}")
    public ResponseEntity submitRequirementsVote(@RequestBody RequirementVote vote,
                                                 @PathVariable("userId") int user_id) {
        //Get bearer token
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(token, vote);
        String url = "http://localhost:8090/api/users/submitVoteRequirement/" + user_id;

        return restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
    }

    /**
     * Routing method used for changing your vote for a change in requirements
     * @param vote the new vote to submit.
     * @param userId the ID of the user that is submitting the vote.
     * @return the ResponseEntity passed back from the method in the HOA microservice.
     */
    @PutMapping("/users/changeVoteRequirement/{userId}")
    public ResponseEntity changeVoteRequirement(@RequestBody RequirementVote vote,
                                                @PathVariable("userId") int userId) {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(token, vote);
        String url = "http://localhost:8090/api/users/changeVoteRequirement/" + userId;

        return restTemplate.exchange(url, HttpMethod.PUT, entity, Object.class);
    }

    @PostMapping("/requirements/createRequirement")
    public ResponseEntity createRequirement(@RequestBody CreateRequirementModel model) {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(token, model);
        String url = "http://localhost:8089/requirements/createRequirement";

        return restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
    }

    @PostMapping("/requirements/report")
    public ResponseEntity report(@RequestBody CreateReportModel model) {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(token, model);
        String url = "http://localhost:8089/requirements/report";

        return restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
    }

    @GetMapping("/requirements/getRequirements/{hoaId}")
    public ResponseEntity getRequirements(@PathVariable("hoaId") int hoaId) {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(token, null);
        String url = "http://localhost:8089/requirements/getRequirements/" + hoaId;

        return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
    }

    @GetMapping("/requirements/getReports/{hoaId")
    public ResponseEntity getReports(@PathVariable("hoaId") int hoaId) {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(token, null);
        String url = "http://localhost:8089/requirements/getReports/" + hoaId;

        return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
    }
}
