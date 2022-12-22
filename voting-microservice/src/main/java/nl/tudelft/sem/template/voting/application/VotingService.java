package nl.tudelft.sem.template.voting.application;

import nl.tudelft.sem.template.voting.domain.Vote;
import nl.tudelft.sem.template.voting.domain.ElectionVoteBuilder;
import nl.tudelft.sem.template.voting.domain.VotingException;
import nl.tudelft.sem.template.commons.models.VotingType;
import nl.tudelft.sem.template.voting.domain.VoteEndCallable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class VotingService {
    private transient Map<Integer, Vote> ongoingElections;
    private transient ScheduledExecutorService electionEndCalls;

    public VotingService() {
        this.ongoingElections = new ConcurrentHashMap<>();
        electionEndCalls = new ScheduledThreadPoolExecutor(2);
        //TODO: separate this value (corePoolSize) into a "config" class, posibly injecting the ScheduledExecutorService
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
        if (votingType.equals(VotingType.ELECTIONS_VOTE)) {
            vote = new ElectionVoteBuilder()
                    .forHoaWithId(hoaId)
                    .withOptions(options)
                    .startInstantlyWithDuration(temporalAmount)
                    .build();
            ongoingElections.put(hoaId, vote);
            electionEndCalls.schedule(new VoteEndCallable(vote, this),
                    vote.getTimeKeeper().getDurationInSeconds(),
                    TimeUnit.SECONDS);
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

    /**
     * A method for getting the results from a vote
     * @param hoaId the ID of the HOA which had a vote
     * @return a Map that stores the collated results
     * @throws VotingException if the mehtod is called before the end of the vote
     */
    public Map<Integer, Integer> getResults(int hoaId) throws VotingException {
        Vote currentVote = ongoingElections.get(hoaId);
        if (currentVote.getTimeKeeper().isVoteOngoing()) {
            throw new VotingException("Vote is still ongoing");
        }
        System.out.println();
        return currentVote.getResults();
    }

    public Instant getEndTime(int hoaId) {
        return ongoingElections.get(hoaId).getTimeKeeper().getEndTime();
    }


    public void evictVote(int hoaId) {
        ongoingElections.remove(hoaId);
    }
}
