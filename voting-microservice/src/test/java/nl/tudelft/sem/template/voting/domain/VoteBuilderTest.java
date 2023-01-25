package nl.tudelft.sem.template.voting.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

public class VoteBuilderTest {

    private static Vote election;
    private static Vote requirements;
    private static List<String> options;
    private static TimeKeeper mockTimeKeeper;
    private static VoterEligibilityChecker mockVec;

    private static final String user0 = "sem";
    private static final String user1 = "sersem";
    private static final String user2 = "semsersem";

    /**
     * Set up prerequisites for testing the builders
     */
    @BeforeEach
    public void setup() {
        options = new ArrayList<>();
        options.addAll(Arrays.asList("candidate0", "candidate1", "candidate2"));

        mockTimeKeeper = Mockito.mock(TimeKeeper.class);
        mockVec = Mockito.mock(VoterEligibilityChecker.class);

        when(mockTimeKeeper.isVoteOngoing()).thenReturn(true);
        when(mockTimeKeeper.getDurationInSeconds()).thenReturn(15L);
        ArgumentMatcher<String> eligibleVotersMatcher = new ArgumentMatcher<String>() {
            @Override
            public boolean matches(String argument) {
                return argument.equals(user0) || argument.equals(user1) || argument.equals(user2);
            }
        };
        // when(mockVec.isVoterEligible(anyString())).thenReturn(false);
        when(mockVec.isVoterEligible(argThat(eligibleVotersMatcher))).thenReturn(true);
    }

    @Test
    public void noVoterEligibilityCheckerTest() {
        election = new ElectionVoteBuilder()
                .forHoaWithId(0)
                .withOptions(options)
                .withTimeKeeper(mockTimeKeeper)
                .build();

        assertEquals(new UrlVoterEligibilityChecker("http://localhost:8090/api/users/isInHoa/", 0),
                election.getVoterEligibilityChecker());
    }

    @Test
    public void instantStartTest() { // boundary
        election = new ElectionVoteBuilder()
                .forHoaWithId(0)
                .withOptions(options)
                .startInstantlyWithDuration(Duration.ofSeconds(1L))
                .build();

        TimeKeeper tk = election.getTimeKeeper();
        assertTrue(tk.isVoteOngoing());
        long timeDiff = (Instant.now().getEpochSecond() - tk.getStartTime().getEpochSecond());
        assertTrue(-5 <= timeDiff && timeDiff <= 5);
        try {
            Thread.sleep(1000); //wait 1 second so that the vote ends
        } catch (InterruptedException e) {
            System.err.println("Test interrupted");
        }
        assertFalse(tk.isVoteOngoing());
    }

    @Test
    public void optionsAreRemembered() {
        election = new RequirementVoteBuilder()
                .forHoaWithId(0)
                .withOptions(options)
                .startInstantlyWithDuration(Duration.ofSeconds(1L))
                .build();

        assertEquals(options, election.getOptions());
    }
}
