package nl.tudelft.sem.template.voting.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Voting {

    protected final int hoaId;
    protected List<String> options;
    protected Map<Integer, Integer> votes; // we have to store the votes, not persisted to the database yet
    protected TimeKeeper timeKeeper;

    /**
     * Initialize a voting procedure
     * @param hoaId the ID of the association within which the vote is conducted
     * @param options
     * @param timeKeeper
     */
    protected Voting(int hoaId, List<String> options, TimeKeeper timeKeeper) {
        this.hoaId = hoaId;
        this.options = options;
        this.votes = new HashMap<>();
        this.timeKeeper = timeKeeper;
    }

    public abstract boolean isVoterEligible(int userId);

    public void castVote(int userId, int optionIndex) throws VotingException{
        if (!isVoterEligible(userId))
            throw new VotingException("Voter is not eligible");
        if (optionIndex >= options.size())
            throw new VotingException("Chosen option index is invalid");
        votes.put(userId, optionIndex);
    }
}
