package nl.tudelft.sem.template.voting.domain;

import nl.tudelft.sem.template.commons.models.ElectionResultsModel;
import nl.tudelft.sem.template.commons.models.RequirementResultsModel;
import nl.tudelft.sem.template.commons.models.VotingModel;
import nl.tudelft.sem.template.voting.application.VotingService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

public class VoteEndCallable implements Callable<Boolean> {

    private transient Vote vote;
    private transient VotingService votingService;

    public VoteEndCallable(Vote vote, VotingService votingService) {
        this.vote = vote;
        this.votingService = votingService;
    }

    /**
     * Method to build an HTTP entity
     */
    public HttpEntity buildEntity(String token, Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity<>(body, headers);
        return entity;
    }

    /**
     * The method that gets executed when an election is over
     * @return true if the operation is successful
     */
    @SuppressWarnings("PMD")
    public Boolean call() {
        var tmp = vote.getResults();
        System.out.println(tmp);
        RestTemplate restTemplate = new RestTemplate();


        //there is no token involved in this request, hence why pmd warnings are ignored
        HttpEntity entity = buildEntity(null, null); //so as to initialize the entity var
        String url = "http://localhost:8090/";
        if (tmp instanceof ElectionResultsModel) {
            url = url + "receiveElectionResults/";
            entity = buildEntity(null, (ElectionResultsModel) tmp);

        } else if (tmp instanceof RequirementResultsModel) {
            url = url + "receiveRequirementResults/";
            entity = buildEntity(null, (RequirementResultsModel) tmp);
        }

        url = url + vote.getHoaId();
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            System.out.println(response);
            votingService.evictVote(vote.hoaId);
            System.out.println("Sent results");
        }catch(Exception e){
            System.out.println("That didn't work, this vote is not removed from the system");
        }
        return true;
    }
}
