package nl.tudelft.sem.template.authentication.controllers;

import nl.tudelft.sem.template.commons.entities.ElectionVote;
import nl.tudelft.sem.template.commons.entities.RequirementVote;
import nl.tudelft.sem.template.commons.models.ActivityModel;
import nl.tudelft.sem.template.commons.models.CreateReportModel;
import nl.tudelft.sem.template.commons.models.CreateRequirementModel;
import nl.tudelft.sem.template.commons.models.hoa.CreateHoaModel;
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
    private static String token;

    /**
     * Instantiates a new UsersController.
     */
    @Autowired
    public GatewayController() {
    }

    /**
     * Builds a standardized HTTP entity to pass along with HTTP requests
     * @param body The body of the request, can be null.
     * @return an HttpEntity with the provided body
     */
    public HttpEntity buildEntity(Object body) {
        token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader(AUTHORIZATION_LITERAL);
        //Remove the "bearer" from the beginning of the token.
        token = token.split(" ")[1];
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return new HttpEntity(body, headers);
    }

    /**
     * Routing method used to retrieve activities for a certain HOA, if authorized.
     *
     * @return The responseEntity passed back from the method in the HOA microservice.
     */
    @GetMapping("/pnb/allActivities")
    public ResponseEntity allActivities() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(null);
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
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(null);
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
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(null);
        String url = "http://localhost:8090/pnb/createActivity";

        return restTemplate.exchange(url, HttpMethod.POST, entity, ActivityModel.class);
    }

    /**
     * Routing method used for casting a vote in an election.
     *
     * @param vote   The vote to cast.
     * @param userId The ID of the user that casts the vote.
     * @param hoaId  The ID of the HOA for which the election is running.
     * @return the responseEntity passed back from the method in the HOA microservice.
     */
    @PostMapping("/users/castVoteElection/{userId}/{hoaId}")
    public ResponseEntity submitVoteElection(@RequestBody ElectionVote vote,
                                             @PathVariable(USER_ID_LITERAL) int userId,
                                             @PathVariable(HOA_ID_LITERAL) int hoaId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(vote);
        String url = "http://localhost:8090/api/users/submitVoteElection/" + userId + "/" + hoaId;

        return restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
    }

    /**
     * Routing method used for changing your vote in an election
     *
     * @param vote   the new vote to submit.
     * @param userId the ID of the user that is submitting the vote.
     * @param hoaId  the ID of the hoa to submit the vote to.
     * @return the ResponseEntity passed back from the method in the HOA microservice.
     */
    @PutMapping("/users/changeVoteElection/{userId}/{hoaId}")
    public ResponseEntity changeVoteElection(@RequestBody ElectionVote vote,
                                             @PathVariable(USER_ID_LITERAL) int userId,
                                             @PathVariable(HOA_ID_LITERAL) int hoaId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(vote);
        String url = "http://localhost:8090/api/users/changeVoteElection/" + userId + "/" + hoaId;

        return restTemplate.exchange(url, HttpMethod.PUT, entity, Object.class);
    }

    /**
     * Routing method used for casting a vote about a change in requirements..
     *
     * @param vote   The vote to cast.
     * @param userId The ID of the user that casts the vote.
     * @return the responseEntity passed back from the method in the HOA microservice.
     */
    @PostMapping("/users/submitRequirementsVote/{userId}/{hoaId}")
    public ResponseEntity submitRequirementsVote(@RequestBody RequirementVote vote,
                                                 @PathVariable(USER_ID_LITERAL) int userId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(vote);
        String url = "http://localhost:8090/api/users/submitVoteRequirement/" + userId;

        return restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
    }

    /**
     * Routing method used for changing your vote for a change in requirements
     *
     * @param vote   the new vote to submit.
     * @param userId the ID of the user that is submitting the vote.
     * @return the ResponseEntity passed back from the method in the HOA microservice.
     */
    @PutMapping("/users/changeVoteRequirement/{userId}")
    public ResponseEntity changeVoteRequirement(@RequestBody RequirementVote vote,
                                                @PathVariable(USER_ID_LITERAL) int userId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(vote);
        String url = "http://localhost:8090/api/users/changeVoteRequirement/" + userId;

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
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(model);
        String url = "http://localhost:8089/requirements/createRequirement";

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
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(model);
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
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(null);
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
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(null);
        String url = "http://localhost:8089/requirements/getReports/" + hoaId;

        return restTemplate.exchange(url, HttpMethod.GET, entity, ResponseEntity.class);
    }

    /**
     * Routing method to create a new HOA.
     *
     * @param model the model containing the details about the HOA to create.
     * @return the ResponseEntity passed back from the method in the HOA microservice.
     */
    @PostMapping("/hoa/createHoa")
    public ResponseEntity createHoa(@RequestBody CreateHoaModel model){
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(model);
        String url = "http://localhost:8090/hoa/crateHoa";

        return restTemplate.exchange(url, HttpMethod.POST, entity, ResponseEntity.class);
    }
}
