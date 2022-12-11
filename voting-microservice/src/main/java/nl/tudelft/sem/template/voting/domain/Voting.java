package nl.tudelft.sem.template.voting.domain;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Voting {

    protected final transient int hoaId;
    @Getter
    protected transient List<String> options;
    protected transient Map<String, Integer> votes; // we have to store the votes, not persisted to the database yet
    protected transient TimeKeeper timeKeeper;

    /**
     * Initialize a voting procedure
     * @param hoaId the ID of the association within which the vote is conducted
     * @param options a list of strings denoting the options to vote for
     * @param timeKeeper a TimeKeeper object that keeps track of when the election is over
     */
    protected Voting(int hoaId, List<String> options, TimeKeeper timeKeeper) {
        this.hoaId = hoaId;
        this.options = options;
        this.votes = new HashMap<>();
        this.timeKeeper = timeKeeper;
    }

    public abstract boolean isVoterEligible(String netId);

    /**
     * Cast a vote in the running voting procedure
     * @param netId The user who is voting
     * @param optionIndex the index of the option the user is voting for
     * @throws VotingException if the voter is not eligible or if the chosen option is not within the list
     */
    public void castVote(String netId, int optionIndex) throws VotingException{
        if (!isVoterEligible(netId))
            throw new VotingException("Voter is not eligible");
        if (optionIndex >= options.size())
            throw new VotingException("Chosen option index is invalid");
        votes.put(netId, optionIndex);
    }
}
