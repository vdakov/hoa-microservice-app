package nl.tudelft.sem.template.voting.domain;

import lombok.Getter;
import nl.tudelft.sem.template.commons.models.ResultsModel;
import nl.tudelft.sem.template.voting.exceptions.IneligibleVoterException;
import nl.tudelft.sem.template.voting.exceptions.InvalidOptionException;
import nl.tudelft.sem.template.voting.exceptions.VoteClosedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Vote {

    protected final transient int hoaId;
    protected transient List<String> options;
    protected transient Map<String, Integer> votes; // we have to store the votes, not persisted to the database yet
    protected transient TimeKeeper timeKeeper;
    protected transient VoterEligibilityChecker voterEligibilityChecker;
    protected transient int numberOfEligibleVoters;
    protected transient ResultsCollator resultsCollator;

    /**
     * Initialize a voting procedure
     * @param hoaId the ID of the association within which the vote is conducted
     * @param options a list of strings denoting the options to vote for
     * @param timeKeeper a TimeKeeper object that keeps track of when the election is over
     */
    protected Vote(int hoaId,
                   List<String> options,
                   TimeKeeper timeKeeper,
                   VoterEligibilityChecker voterEligibilityChecker,
                   int numberOfEligibleVoters,
                   ResultsCollator resultsCollator) {
        this.hoaId = hoaId;
        this.options = options;
        this.votes = new HashMap<>();
        this.timeKeeper = timeKeeper;
        this.voterEligibilityChecker = voterEligibilityChecker;
        this.numberOfEligibleVoters = numberOfEligibleVoters;
        this.resultsCollator = resultsCollator;
    }

    public boolean isVoterEligible(String netId) {
        return voterEligibilityChecker.isVoterEligible(netId);
    }

    /**
     * Cast a vote in the running voting procedure
     * @param netId The user who is voting
     * @param optionIndex the index of the option the user is voting for
     * @throws IneligibleVoterException if the voter is not eligible
     * @throws InvalidOptionException if the chosen option is not within the list
     * @throws VoteClosedException if the vote is closed
     */
    public void castVote(String netId, int optionIndex)
            throws IneligibleVoterException, InvalidOptionException, VoteClosedException {
        if (!isVoterEligible(netId))
            throw new IneligibleVoterException("Voter is not eligible");
        if (optionIndex >= options.size())
            throw new InvalidOptionException("Chosen option index is invalid");
        if (!timeKeeper.isVoteOngoing())
            throw new VoteClosedException("Voting is closed");
        votes.put(netId, optionIndex);
    }

    /**
     * Return the aggregated election results. Gets called only when the voting is closed.
     * @return -
     */
    @SuppressWarnings("PMD")
    public ResultsModel getResults() {
        return this.resultsCollator.collateResults(votes, options, numberOfEligibleVoters);
    }
}
