package nl.tudelft.sem.template.voting.domain;

import nl.tudelft.sem.template.commons.models.VotingModel;
import nl.tudelft.sem.template.commons.models.VotingType;
import nl.tudelft.sem.template.voting.application.VotingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class VotingServiceTest {

    private VotingService votingService = new VotingService();
    private Vote vote;
    private List<String> options;
    @BeforeEach
    void setup(){
        options = new ArrayList<>();
        options.addAll(Arrays.asList("candidate0", "candidate1", "candidate2"));

        TimeKeeper mockTimeKeeper = Mockito.mock(TimeKeeper.class);
        VoterEligibilityChecker mockVec = Mockito.mock(VoterEligibilityChecker.class);
        ResultsCollator collator = Mockito.mock(ResultsCollator.class);

        vote = new Vote(1,options,mockTimeKeeper,mockVec, 5, collator);
    }

    @Test
    void getOptions_VoteIsOngoing(){
        int hoaId = 1;
        VotingModel votingModel = new VotingModel(hoaId, VotingType.ELECTIONS_VOTE, 5, options);
        votingService.registerVoteStartingNow(votingModel, Duration.ofMinutes(1L));

        assertThat(votingService.getOptions(hoaId)).isEqualTo(vote.getOptions());
    }

    @Test
    void getOptions_VoteIsNotOngoing(){
        assertThat(votingService.getOptions(1)).isEmpty();
    }
}
