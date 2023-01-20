package nl.tudelft.sem.template.voting.domain;

import nl.tudelft.sem.template.voting.application.VotingService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import java.util.concurrent.ConcurrentHashMap;

public class VotingServiceTest {

    private VotingService votingService = new VotingService();

    @Test
    void getOptions_VoteIsNotOngoing(){
        int hoaId = 1;
        ConcurrentHashMap<Integer, Vote> ongoingElections = Mockito.mock(ConcurrentHashMap.class);
        Mockito.when(ongoingElections.get(hoaId)).thenReturn(null);

        assertThat(votingService.getOptions(1)).isEmpty();
    }
}
