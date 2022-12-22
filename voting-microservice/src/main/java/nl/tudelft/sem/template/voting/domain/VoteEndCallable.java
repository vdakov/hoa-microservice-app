package nl.tudelft.sem.template.voting.domain;

import nl.tudelft.sem.template.voting.application.VotingService;

import java.util.concurrent.Callable;

public class VoteEndCallable implements Callable<Boolean> {

    private transient Vote vote;
    private transient VotingService votingService;

    public VoteEndCallable(Vote vote, VotingService votingService) {
        this.vote = vote;
        this.votingService = votingService;
    }

    /**
     * The method that gets executed when an election is over
     * @return true if the operation is successful
     */
    public Boolean call() {
        var tmp = vote.getResults();
        System.out.println(tmp);
        System.out.println("Sent results");
        votingService.evictVote(vote.hoaId);
        return true;
    }
}
