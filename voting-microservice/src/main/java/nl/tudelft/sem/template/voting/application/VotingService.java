package nl.tudelft.sem.template.voting.application;

import nl.tudelft.sem.template.voting.domain.Vote;
import nl.tudelft.sem.template.voting.domain.ElectionVoteBuilder;
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
    private transient Map<Integer, Vote> ongoingElections;

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
    public void registerVoteStartingNow(int hoaId,
                                        VotingType votingType,
                                        List<String> options,
                                        TemporalAmount temporalAmount) {
        Vote vote;
        if (votingType.equals(VotingType.BOARD_ELECTIONS)) {
            vote = new ElectionVoteBuilder()
                    .forHoaWithId(hoaId)
                    .withOptions(options)
                    .startInstantlyWithDuration(temporalAmount)
                    .build();
            ongoingElections.put(hoaId, vote);
        } else if (votingType.equals(VotingType.REQUIREMENTS_VOTE)) {
            return; //TODO: build a requirements vote when that is implemented
        }
    }

    public boolean existingHoaVoting(int hoaId) {
        return ongoingElections.containsKey(hoaId);
    }

    public void castVote(int hoaId, String netId, int optionIndex) throws VotingException {
        Vote currentVote = ongoingElections.get(hoaId);
        currentVote.castVote(netId, optionIndex);
    }

    public List<String> getOptions(int hoaId) {
        Vote requestedVote = ongoingElections.get(hoaId);
        if (requestedVote != null) {
            return requestedVote.getOptions();
        }
        return Collections.emptyList();
    }

    public Map<Integer, Integer> getResults(int hoaId) throws VotingException {
        Vote currentVote = ongoingElections.get(hoaId);
        if (currentVote.getTimeKeeper().isVoteOngoing()) {
            throw new VotingException("Vote is still ongoing");
        }
        return currentVote.getResults();
    }

    public Instant getEndTime(int hoaId) {
        return ongoingElections.get(hoaId).getTimeKeeper().getEndTime();
    }
}
