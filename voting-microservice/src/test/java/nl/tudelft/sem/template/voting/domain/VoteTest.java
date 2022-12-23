package nl.tudelft.sem.template.voting.domain;

import nl.tudelft.sem.template.commons.models.ElectionResultsModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;

public class VoteTest {

    private static Vote election;
    private static Vote requirements;
    private static List<String> options;
    private static TimeKeeper mockTimeKeeper;
    private static VoterEligibilityChecker mockVec;

    private static final String user0 = "sem";
    private static final String user1 = "sersem";
    private static final String user2 = "semsersem";

    /**
     * Set up an ElectionVote for the given tests
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

        election = new ElectionVoteBuilder()
                .forHoaWithId(0)
                .withOptions(options)
                .withTimeKeeper(mockTimeKeeper)
                .withVoterEligibilityChecker(mockVec)
                .build();
    }

    @Test
    public void castSingleVoteTest() {
        try {
            election.castVote(user0, 0);
            assertEquals(0, election.getVotes().get(user0));
        } catch(VotingException e) {
            fail();
        }
    }

    @Test
    public void recastVoteTest() {
        try {
            election.castVote(user0, 0);
            election.castVote(user0, 2);
            election.castVote(user0, 1);
            assertEquals(1, election.getVotes().get(user0));
        } catch(VotingException e) {
            fail();
        }
    }

    @Test
    public void twoVotersTest() {
        try {
            election.castVote(user0, 0);
            election.castVote(user1, 2);
            election.castVote(user0, 1);
            assertEquals(1, election.getVotes().get(user0));
            assertEquals(2, election.getVotes().get(user1));
        } catch(VotingException e) {
            fail();
        }
    }

    @Test
    public void twoVotersRecastVotesTest() {
        try {
            election.castVote(user0, 0);
            election.castVote(user1, 2);
            election.castVote(user1, 0);
            election.castVote(user0, 1);
            assertEquals(1, election.getVotes().get(user0));
            assertEquals(0, election.getVotes().get(user1));
        } catch(VotingException e) {
            fail();
        }
    }

    @Test
    public void invalidIndexTest() { //boundary
        assertThrows(VotingException.class, () -> election.castVote(user0, 3));
        verify(mockVec).isVoterEligible(anyString());
        verify(mockTimeKeeper, never()).isVoteOngoing();
    }

    @Test
    public void ineligibleTest() {
        assertThrows(VotingException.class, () -> election.castVote("outsider", 3));
        verify(mockVec).isVoterEligible(anyString());
    }

    @Test
    public void voteClosedTest() {
        // set up a new election where the vote is closed
        mockTimeKeeper = Mockito.mock(TimeKeeper.class);
        mockVec = Mockito.mock(VoterEligibilityChecker.class);

        when(mockTimeKeeper.isVoteOngoing()).thenReturn(false);
        when(mockTimeKeeper.getDurationInSeconds()).thenReturn(0L);
        ArgumentMatcher<String> eligibleVotersMatcher = new ArgumentMatcher<String>() {
            @Override
            public boolean matches(String argument) {
                return argument.equals("sem") || argument.equals("sersem");
            }
        };

        when(mockVec.isVoterEligible(argThat(eligibleVotersMatcher))).thenReturn(true);

        election = new ElectionVoteBuilder()
                .forHoaWithId(0)
                .withOptions(options)
                .withTimeKeeper(mockTimeKeeper)
                .withVoterEligibilityChecker(mockVec)
                .build();

        assertThrows(VotingException.class, () -> election.castVote(user0, 1));
        verify(mockTimeKeeper).isVoteOngoing();
        verify(mockVec).isVoterEligible(eq(user0));
    }

    @Test
    public void aggregateResultsTest() {
        try {
            election.castVote(user0, 0);
            election.castVote(user1, 2);
            election.castVote(user1, 0);
            election.castVote(user0, 1);
            election.castVote(user2, 1);
            assertEquals(1, election.getVotes().get(user0));
            assertEquals(0, election.getVotes().get(user1));
            assertEquals(1, election.getVotes().get(user2));

            Map<String, Integer> expectedVoteDistributions = new HashMap<>();
            expectedVoteDistributions.put(options.get(0), 1);
            expectedVoteDistributions.put(options.get(1), 2);
            expectedVoteDistributions.put(options.get(2), 0);

            ElectionResultsModel expected = new ElectionResultsModel(3,
                    3,
                    expectedVoteDistributions);
            assertEquals(expected, election.getResults());
        } catch(VotingException e) {
            fail();
        }
    }

    @Test
    public void aggregateResultsUnanimousVoteTest() {
        try {
            election.castVote(user1, 1);
            election.castVote(user0, 1);
            election.castVote(user2, 1);
            assertEquals(1, election.getVotes().get(user0));
            assertEquals(1, election.getVotes().get(user1));
            assertEquals(1, election.getVotes().get(user2));

            Map<String, Integer> expectedVoteDistributions = new HashMap<>();
            expectedVoteDistributions.put(options.get(0), 0);
            expectedVoteDistributions.put(options.get(1), 3);
            expectedVoteDistributions.put(options.get(2), 0);

            ElectionResultsModel expected = new ElectionResultsModel(3,
                    3,
                    expectedVoteDistributions);
            assertEquals(expected, election.getResults());
        } catch(VotingException e) {
            fail();
        }
    }

    @Test
    public void aggregateResultsTieTest() {
        try {
            election.castVote(user1, 0);
            election.castVote(user0, 1);
            assertEquals(1, election.getVotes().get(user0));
            assertEquals(0, election.getVotes().get(user1));

            Map<String, Integer> expectedVoteDistributions = new HashMap<>();
            expectedVoteDistributions.put(options.get(0), 1);
            expectedVoteDistributions.put(options.get(1), 1);
            expectedVoteDistributions.put(options.get(2), 0);

            ElectionResultsModel expected = new ElectionResultsModel(3,
                    2,
                    expectedVoteDistributions);
            assertEquals(expected, election.getResults());
        } catch(VotingException e) {
            fail();
        }
    }
}
