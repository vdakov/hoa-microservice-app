package nl.tudelft.sem.template.authentication.controllers;

import nl.tudelft.sem.template.commons.entities.ElectionVote;
import nl.tudelft.sem.template.commons.models.ActivityModel;
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

        //TODO: Define return type, define parameters (HOA).
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(token, null);
        String url = "http://localhost:8090/pnb/allActivities";
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        System.out.println(response);
        return response;
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

        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        return response;
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

        ResponseEntity<ActivityModel> response = restTemplate.exchange(url, HttpMethod.POST, entity, ActivityModel.class);
        return response;
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
    public ResponseEntity test(@RequestBody ElectionVote vote,
                               @PathVariable("userId") int user_id,
                               @PathVariable("hoaId") int hoa_id) {
        //Get bearer token
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(token, vote);
        String url = "http://localhost:8090/api/users/submitVoteElection/" + user_id + "/" + hoa_id;

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        System.out.println(response);
        return response;
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
        String url = "http://localhost:8090/api/users/changeVoteElectoin/" + userId + "/" + hoaId;

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
        return response;
    }

    
}
