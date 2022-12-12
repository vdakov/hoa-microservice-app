package nl.tudelft.sem.template.voting.application;

import nl.tudelft.sem.template.voting.domain.Voting;
import nl.tudelft.sem.template.voting.domain.VotingBuilder;
import nl.tudelft.sem.template.voting.domain.VotingException;
import nl.tudelft.sem.template.voting.domain.VotingType;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VotingService {
    private transient Map<Integer, Voting> ongoingElections;

    public VotingService() {
        this.ongoingElections = new HashMap<>();
    }

    /**
     * Register a new voting procedure starting immediately
     * @param hoaId the ID of the HOA where the voting is conducted
     * @param votingType the type of vote
     * @param options the available options to choose from in the voting
     * @param temporalAmount the duration of the elections
     */
    public void registerVotingStartingNow(int hoaId,
                                          VotingType votingType,
                                          List<String> options,
                                          TemporalAmount temporalAmount) {
        Voting voting;
        if (votingType.equals(VotingType.BOARD_ELECTIONS)) {
            voting = new VotingBuilder()
                    .forHoaWithId(hoaId)
                    .withOptions(options)
                    .startInstantlyWithDuration(temporalAmount)
                    .buildBoardElections();
            ongoingElections.put(hoaId, voting);
        } else if (votingType.equals(VotingType.REQUIREMENTS_VOTE)) {
            return; //TODO: build a requirements vote when that is implemented
        }
    }

    public boolean existingHoaVoting(int hoaId) {
        return ongoingElections.containsKey(hoaId);
    }

    public void castVote(int hoaId, String netId, int optionIndex) throws VotingException {
        Voting currentVoting = ongoingElections.get(hoaId);
        currentVoting.castVote(netId, optionIndex);
    }

    public List<String> getOptions(int hoaId) {
        Voting requestedVoting = ongoingElections.get(hoaId);
        if (requestedVoting != null) {
            return requestedVoting.getOptions();
        }
        return Collections.emptyList();
    }

    public Map<String, Integer> getResults(int hoaId) throws VotingException {
        Voting currentVoting = ongoingElections.get(hoaId);
        if (currentVoting.getTimeKeeper().isVoteOngoing()) {
            throw new VotingException("Vote is still ongoing");
        }
        return currentVoting.getResults();
    }

    public Instant getEndTime(int hoaId) {
        return ongoingElections.get(hoaId).getTimeKeeper().getEndTime();
    }
}
