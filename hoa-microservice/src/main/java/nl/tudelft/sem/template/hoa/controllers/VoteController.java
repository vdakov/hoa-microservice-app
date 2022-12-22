package nl.tudelft.sem.template.hoa.controllers;

import java.util.List;

import nl.tudelft.sem.template.commons.models.ElectionResultsModel;
import nl.tudelft.sem.template.commons.models.RequirementResultsModel;
import nl.tudelft.sem.template.commons.models.VotingModel;
import nl.tudelft.sem.template.hoa.entitites.User;
import nl.tudelft.sem.template.hoa.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
public class VoteController {


    private final transient VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }


    public HttpEntity buildEntity(String token, Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity<>(body, headers);
        return entity;
    }

    /**
     * Test for sample routing to voting microservice : WILL DELETE AFTER TOKEN PASSING IS ADDED
     */
    @GetMapping("/testRemote")
    public void testRemote() {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization");
        System.out.println(token);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(null, token);
        String url = "http://localhost:8082/hello";
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        System.out.println(response);
    }


    /**
     * Test for internal routing: WILL DELETE AFTER TOKEN PASSING IS ADDED
     */
    @GetMapping("/hello")
    public void hello() {
        System.out.println("hello");
    }

    /**
     * Receives the DTO from Voting to be later stored in the DB
     *
     * @param results the DTO from the request body
     * @param hoaId   the id of the associations this is for
     * @return status of the message
     */
    @PostMapping("/receiveElectionResults/{hoaId}")
    public ResponseEntity<String> getElectionResults(@RequestBody ElectionResultsModel results,
                                                     @PathVariable("hoaId") int hoaId) {
        voteService.storeElectionResults(hoaId, results);
        return ResponseEntity.ok().build();
    }

    /**
     * Analogous with above JavaDoc
     */
    @PostMapping("/receiveRequirementResults/{hoaId}")
    public ResponseEntity<String> getRequirementResults(@RequestBody RequirementResultsModel results,
                                                        @PathVariable("hoaId") int hoaId) {
        voteService.storeRequirementResults(hoaId, results);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint for getting a list of eligible members in the HOA (used for requirement votings)
     */
    @GetMapping("/getListEligibleMembers/{hoadId}")
    public ResponseEntity<List<User>> getListEligibleMembers() {
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint for telling the voting microservice to start an election vote
     * <p>
     * Inside of it has implementation for sending the HTTP request (not part of the business logic,
     * which is why it is here.) All that is left to do is to complete the token setup.
     */
    @GetMapping("/initializeElectionVoting/{hoaId}")
    public ResponseEntity<String> startElectionVote(@PathVariable("hoaId") int hoaId) {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization");

        RestTemplate restTemplate = new RestTemplate();
        VotingModel votingModel = voteService.startElectionVote(hoaId);

        HttpEntity entity = buildEntity(token, votingModel);
        String url = "http://localhost:8082/initializeVoting";
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        System.out.println(response);

        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint for telling the voting microservice to start a requirement vote
     * <p>
     * Inside of it has implementation for sending the HTTP request (not part of the business logic,
     * which is why it is here.) All that is left to do is to complete the token setup.
     */
    @GetMapping("/initializeRequirementVoting/{hoaId}")
    public ResponseEntity<String> startRequirementVote(@PathVariable("hoaId") int hoaId) {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization");

        RestTemplate restTemplate = new RestTemplate();
        VotingModel votingModel = voteService.startElectionVote(hoaId);

        HttpEntity entity = buildEntity(token, votingModel);
        String url = "http://localhost:8082/initializeVoting";
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        System.out.println(response);

        return ResponseEntity.ok().build();
    }


}
