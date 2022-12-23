package nl.tudelft.sem.template.authentication.controllers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import nl.tudelft.sem.template.commons.models.ActivityModel;
import nl.tudelft.sem.template.commons.models.CreateRequirementModel;
import nl.tudelft.sem.template.commons.models.VotingModel;
import nl.tudelft.sem.template.commons.models.hoa.ConnectionRequestModel;
import nl.tudelft.sem.template.commons.models.hoa.FullUserHoaModel;
import nl.tudelft.sem.template.commons.models.hoa.FullHoaResponseModel;
import nl.tudelft.sem.template.commons.models.hoa.HoaRequestModel;
import nl.tudelft.sem.template.commons.models.hoa.FullUserResponseModel;
import nl.tudelft.sem.template.commons.models.hoa.JoinRequestModel;
import nl.tudelft.sem.template.commons.models.UpdateRequirementModel;
import nl.tudelft.sem.template.commons.models.DeleteRequirementModel;
import nl.tudelft.sem.template.commons.models.CreateReportModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.function.Function;

@RestController
@RequestMapping("/gateway")
public class GatewayController {

    private static final String AUTHORIZATION_LITERAL = "Authorization";
    private static final String USER_ID_LITERAL = "userId";
    private static final String HOA_ID_LITERAL = "hoaId";
    private static String token;

    /**
     * Instantiates a new GatewayController.
     */
    @Autowired
    public GatewayController() {
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey("exampleSecret").parseClaimsJws(token).getBody();
    }

    /**
     * Builds a standardized HTTP entity to pass along with HTTP requests
     *
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
     * Routing method used to retrieve activities for HOAs that the user is member of.
     *
     * @return The responseEntity passed back from the method in the HOA microservice.
     */
    @GetMapping("/pnb/allActivities")
    public ResponseEntity allActivities() {
        //Get bearer token
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader(AUTHORIZATION_LITERAL);
        String username = getClaimFromToken(token.split(" ")[1], Claims::getSubject);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(null);
        String url = "http://localhost:8090/pnb/allActivitiesForUser/" + username;
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
        HttpEntity entity = buildEntity(model);
        String url = "http://localhost:8090/pnb/createActivity";

        return restTemplate.exchange(url, HttpMethod.POST, entity, ActivityModel.class);
    }


    /**
     * Routes a request to delete a connection between a user and a homeowners association (HOA).
     *
     * @param request A request model containing the HOA's natural id and the user's display name.
     * @return A response containing the updated user information, as returned by the HOA controller,
     * or a bad request if any of the fields are null
     */
    @DeleteMapping("/users/leaveHoa")
    public ResponseEntity<FullUserResponseModel> leaveHoa(@RequestBody ConnectionRequestModel request) {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader(AUTHORIZATION_LITERAL);

        String userName = getClaimFromToken(token.split(" ")[1], Claims::getSubject);

        request.setDisplayName(userName);

        if (request.anyNull())
            return ResponseEntity.badRequest().build();

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(request);
        String url = "http://localhost:8090/api/users/leaveHoa";

        return restTemplate.exchange(url, HttpMethod.DELETE, entity, FullUserResponseModel.class);
    }


    /**
     * Routes a request to make a connection between a user and a homeowners association (HOA).
     *
     * @param request A request model containing the HOA's name, the user's display name, and full address.
     * @return A response containing the connection made, as returned by the HOA controller,
     * or a bad request if any of the fields are null
     */
    @PostMapping("/users/joinHoa")
    public ResponseEntity<FullUserHoaModel> joinHoa(@RequestBody JoinRequestModel request) {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader(AUTHORIZATION_LITERAL);

        String userName = getClaimFromToken(token.split(" ")[1], Claims::getSubject);

        try {
            RestTemplate restTemplateRegister = new RestTemplate();
            HttpEntity entityRegister = buildEntity(userName);
            String url = "http://localhost:8090/api/users/createNewUser";

            restTemplateRegister.exchange(url, HttpMethod.POST, entityRegister, Object.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setDisplayName(userName);

        if (request.anyNull())
            return ResponseEntity.badRequest().build();

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(request);
        String url = "http://localhost:8090/api/users/joinHoa";

        return restTemplate.exchange(url, HttpMethod.POST, entity, FullUserHoaModel.class);
    }

    /**
     * Routing method used for casting (or replacing an already cast) vote in an election.
     *
     * @param option     The vote to cast.
     * @param userName The ID of the user that casts the vote.
     * @param hoaId    The ID of the HOA for which the election is running.
     * @return the responseEntity passed back from the method in the HOA microservice.
     */
    @PostMapping("/users/castVote/{userName}/{hoaId}")
    public ResponseEntity castVote(@RequestBody int option,
                                   @PathVariable("userName") String userName,
                                   @PathVariable(HOA_ID_LITERAL) int hoaId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(option);
        String url = "http://localhost:8082/" + hoaId + "/castVote/" + userName;

        return restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
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
     * Routing method to check whether a user is a board member
     *
     * @param model the model containing the user natural id
     * @return status of message
     */
    @GetMapping("/isABoardMember")
    public ResponseEntity isBoardMember(@RequestBody ConnectionRequestModel model) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(model);
        String url = "http://localhost:8090/api/users/isInABoard";

        return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);

    }

    /**
     * Routing method used for updating an existing requirement.
     *
     * @param model the request model containing the details of the requirement.
     * @return the ResponseEntity passed back from the endpoint.
     */
    @PostMapping("/requirements/changeRequirement")
    public ResponseEntity updateRequirement(@RequestBody UpdateRequirementModel model) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(model);
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
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(model);
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
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(model);
        String url = "http://localhost:8089/requirements/report";

        return restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
    }

    /**
     * Routing method for starting an election vote - goes through the HOA microservice and then
     * starts a vote in the Voting Microservice
     * <p>
     * Due to the app being a prototype, it is assumed users will behave and only eligible members will be able to start
     * the vote
     *
     * @param hoaId the id of the HOA the election is starting
     * @return Success Message if the message reached HOA
     */
    @GetMapping("/startElectionVote/{hoaId}")
    public ResponseEntity startElectionVote(@PathVariable(HOA_ID_LITERAL) int hoaId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(null);
        String url = "http://localhost:8090/initializeElectionVoting/" + hoaId;

        return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
    }

    /**
     * Routing method for starting an requirement vote - goes through the HOA microservice and then
     * starts a vote in the Voting Microservice
     * <p>
     * Due to the app being a prototype, it is assumed users will
     * behave and only eligible members will be able to start
     * the vote. Additionally, the vote is independent of the requirement
     * and it is assumed the users will behave and they will
     * create the requirement through a different endpoint iff the vote has passed.
     * All board members will be aware of what they are
     * voting for.
     *
     * @param hoaId the id of the HOA the voting is starting
     * @return Success Message if the message reached HOA
     */
    @GetMapping("/startRequirementVote/{hoaId}")
    public ResponseEntity startRequirementVote(@PathVariable(HOA_ID_LITERAL) int hoaId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(null);
        String url = "http://localhost:8090/initializeRequirementVoting/" + hoaId;

        return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
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
    public ResponseEntity createHoa(@RequestBody HoaRequestModel model) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(model);
        String url = "http://localhost:8090/hoa/createHoa";

        return restTemplate.exchange(url, HttpMethod.POST, entity, FullHoaResponseModel.class);
    }
}
