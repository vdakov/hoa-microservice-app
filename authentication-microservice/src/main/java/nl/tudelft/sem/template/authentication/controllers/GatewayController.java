package nl.tudelft.sem.template.authentication.controllers;

import nl.tudelft.sem.template.commons.models.ActivityModel;
import nl.tudelft.sem.template.commons.models.CreateRequirementModel;
import nl.tudelft.sem.template.commons.models.VotingModel;
import nl.tudelft.sem.template.commons.models.UpdateRequirementModel;
import nl.tudelft.sem.template.commons.models.DeleteRequirementModel;
import nl.tudelft.sem.template.commons.models.CreateReportModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/gateway")
public class GatewayController {

    private static final String AUTHORIZATION_LITERAL = "Authorization";
    private static final String USER_ID_LITERAL = "userId";
    private static final String HOA_ID_LITERAL = "hoaId";
    /**
     * Instantiates a new GatewayController.
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
                .getRequest().getHeader(AUTHORIZATION_LITERAL);
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
    @GetMapping("/pnb/activitiesForHoa/{hoaId}")
    public ResponseEntity activitiesForHoa(@PathVariable(HOA_ID_LITERAL) int hoaId) {
        //Get bearer token
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader(AUTHORIZATION_LITERAL);
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
                .getRequest().getHeader(AUTHORIZATION_LITERAL);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(token, model);
        String url = "http://localhost:8090/pnb/createActivity";

        return restTemplate.exchange(url, HttpMethod.POST, entity, ActivityModel.class);
    }

    /**
     * Routing method used for casting a vote in an election.
     *
     * @param vote The vote to cast.
     * @param userId The ID of the user that casts the vote.
     * @param hoaId The ID of the HOA for which the election is running.
     * @return the responseEntity passed back from the method in the HOA microservice.
     */
    @PostMapping("/users/castVote/{userId}/{hoaId}")
    public ResponseEntity castVote(@RequestBody VotingModel vote,
                                             @PathVariable(USER_ID_LITERAL) int userId,
                                             @PathVariable(HOA_ID_LITERAL) int hoaId) {
        //Get bearer token
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader(AUTHORIZATION_LITERAL);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(token, vote);
        String url = "http://localhost:8082/vote/"+ hoaId + "/castVote";

        return restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
    }

    /**
     * Routing method used for changing your vote in an election
     * @param vote the new vote to submit.
     * @param userId the ID of the user that is submitting the vote.
     * @param hoaId the ID of the hoa to submit the vote to.
     * @return the ResponseEntity passed back from the method in the HOA microservice.
     */
    @PutMapping("/users/changeVote/{userId}/{hoaId}")
    public ResponseEntity changeVoteElection(@RequestBody VotingModel vote,
                                             @PathVariable(USER_ID_LITERAL) int userId,
                                             @PathVariable(HOA_ID_LITERAL) int hoaId) {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader(AUTHORIZATION_LITERAL);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(token, vote);
        //temporary
        String url = "http://localhost:8082/vote/"+ hoaId + "/castVote";

        return restTemplate.exchange(url, HttpMethod.PUT, entity, Object.class);
    }




    /**
     * Routing method used for creating a new requirement.
     *
     * @param model the request model containing the details of the requirement.
     * @return the ResponseEntity passed back from the endpoint.
     */
    @PostMapping("/requirements/createRequirement")
    public ResponseEntity createRequirement(@RequestBody CreateRequirementModel model) {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader(AUTHORIZATION_LITERAL);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(token, model);
        String url = "http://localhost:8089/requirements/createRequirement";

        return restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
    }

    /**
     * Routing method used for updating an existing requirement.
     *
     * @param model the request model containing the details of the requirement.
     * @return the ResponseEntity passed back from the endpoint.
     */
    @PostMapping("/requirements/changeRequirement")
    public ResponseEntity updateRequirement(@RequestBody UpdateRequirementModel model) {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader(AUTHORIZATION_LITERAL);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(token, model);
        String url = "http://localhost:8089/requirements/changeRequirement";

        return restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
    }

    /**
     * Routing method used for deleting an existing requirement.
     *
     * @param model the request model containing the details of the requirement.
     * @return the ResponseEntity passed back from the endpoint.
     */
    @PostMapping("/requirements/deleteRequirement")
    public ResponseEntity deleteRequirement(@RequestBody DeleteRequirementModel model) {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader(AUTHORIZATION_LITERAL);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(token, model);
        String url = "http://localhost:8089/requirements/deleteRequirement";

        return restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
    }

    /**
     * Routing method for reporting a user.
     *
     * @param model the model containing information about the report.
     * @return the ResponseEntity passed back from the endpoint.
     */
    @PostMapping("/requirements/report")
    public ResponseEntity report(@RequestBody CreateReportModel model) {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader(AUTHORIZATION_LITERAL);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(token, model);
        String url = "http://localhost:8089/requirements/report";

        return restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
    }

    /**
     * Routing method used for retrieving the requirements for a specific HOA.
     *
     * @param hoaId the ID of the HOA to retrieve requirements from.
     * @return a ResponseEntity containing the list of requirements.
     */
    @GetMapping("/requirements/getRequirements/{hoaId}")
    public ResponseEntity getRequirements(@PathVariable(HOA_ID_LITERAL) int hoaId) {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader(AUTHORIZATION_LITERAL);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(token, null);
        String url = "http://localhost:8089/requirements/getRequirements/" + hoaId;

        return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
    }

    /**
     * Routing method used for retrieving a list of reports for a specific HOA.
     *
     * @param hoaId the ID of the HOA to retrieve the reports from.
     * @return a ResponseEntity containing the list of reports.
     */
    @GetMapping("/requirements/getReports/{hoaId}")
    public ResponseEntity getReports(@PathVariable(HOA_ID_LITERAL) int hoaId) {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader(AUTHORIZATION_LITERAL);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(token, null);
        String url = "http://localhost:8089/requirements/getReports/" + hoaId;

        return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
    }
}
