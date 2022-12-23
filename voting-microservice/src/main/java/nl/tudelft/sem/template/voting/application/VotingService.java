package nl.tudelft.sem.template.voting.application;

import nl.tudelft.sem.template.commons.models.ElectionResultsModel;
import nl.tudelft.sem.template.commons.models.ResultsModel;
import nl.tudelft.sem.template.commons.models.VotingModel;
import nl.tudelft.sem.template.voting.domain.Vote;
import nl.tudelft.sem.template.voting.domain.ElectionVoteBuilder;
import nl.tudelft.sem.template.voting.domain.VotingException;
import nl.tudelft.sem.template.voting.domain.RequirementVoteBuilder;
import nl.tudelft.sem.template.voting.domain.VoteEndCallable;
import nl.tudelft.sem.template.commons.models.VotingType;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Collections;
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
     * @param votingModel the DTO containing the necessary info
     * @param temporalAmount the duration of the elections
     */
    public void registerVoteStartingNow(VotingModel votingModel,
                                        TemporalAmount temporalAmount) {
        Vote vote;
        if (votingModel.getVotingType().equals(VotingType.ELECTIONS_VOTE)) {
            vote = new ElectionVoteBuilder()
                    .forHoaWithId(votingModel.getHoaId())
                    .withOptions(votingModel.getOptions())
                    .startInstantlyWithDuration(temporalAmount)
                    .withEligibleVoters(votingModel.getNumberOfEligibleVoters())
                    .build();
            ongoingElections.put(votingModel.getHoaId(), vote);
            electionEndCalls.schedule(new VoteEndCallable(vote, this),
                    vote.getTimeKeeper().getDurationInSeconds(),
                    TimeUnit.SECONDS);
        } else if (votingModel.getVotingType().equals(VotingType.REQUIREMENTS_VOTE)) {
            vote = new RequirementVoteBuilder()
                    .forHoaWithId(votingModel.getHoaId())
                    .startInstantlyWithDuration(temporalAmount)
                    .withEligibleVoters(votingModel.getNumberOfEligibleVoters())
                    .build();
            ongoingElections.put(votingModel.getHoaId(), vote);
            electionEndCalls.schedule(new VoteEndCallable(vote, this),
                    vote.getTimeKeeper().getDurationInSeconds(),
                    TimeUnit.SECONDS);
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
    public ResultsModel getResults(int hoaId) throws VotingException {
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
