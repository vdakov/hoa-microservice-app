package nl.tudelft.sem.template.voting.domain;

import lombok.Getter;

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
                   int numberOfEligibleVoters) {
        this.hoaId = hoaId;
        this.options = options;
        this.votes = new HashMap<>();
        this.timeKeeper = timeKeeper;
        this.voterEligibilityChecker = voterEligibilityChecker;
        this.numberOfEligibleVoters = numberOfEligibleVoters;
    }

    public boolean isVoterEligible(String netId) {
        return voterEligibilityChecker.isVoterEligible(netId);
    }

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
        if (!timeKeeper.isVoteOngoing())
            throw new VotingException("Voting is closed");
        votes.put(netId, optionIndex);
    }

    /**
     * Return the aggregated election results. Gets called only when the voting is closed.
     * @return -
     */
    @SuppressWarnings("PMD")
    public Map<Integer, Integer> getResults() {

        // PMD was throwing warnings because values in the map are first zero-initialized, and then
        // incremented; rule DataflowAnomalyAnalysis
        Map<Integer, Integer> aggregatedResults = new HashMap<>();
        for (int option = 0; option < this.options.size(); option++) { // initialize the map containing aggregated results
            aggregatedResults.put(option, 0);
        }

        for (Integer vote : votes.values()) {
            int currentNumber = aggregatedResults.get(vote);
            currentNumber++;
            aggregatedResults.replace(vote, currentNumber);
        }
        return aggregatedResults;
    }
}
