package nl.tudelft.sem.template.hoa.services;

import nl.tudelft.sem.template.commons.models.ElectionResultsModel;
import nl.tudelft.sem.template.commons.models.RequirementResultsModel;
import nl.tudelft.sem.template.commons.models.VotingModel;
import nl.tudelft.sem.template.commons.models.VotingType;
import nl.tudelft.sem.template.hoa.matchers.ElectionResultsMatcher;
import nl.tudelft.sem.template.hoa.matchers.RequirementResultsMatcher;
import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.hoa.entitites.User;
import nl.tudelft.sem.template.hoa.entitites.ElectionResults;
import nl.tudelft.sem.template.hoa.entitites.UserHoa;
import nl.tudelft.sem.template.hoa.entitites.RequirementResults;
import nl.tudelft.sem.template.hoa.repositories.HoaRepository;
import nl.tudelft.sem.template.hoa.repositories.ResultsRepository;
import nl.tudelft.sem.template.hoa.repositories.UserRepository;
import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class VotingServiceTest {

    @Test
    public void testStoreElectionResults() {
        HoaRepository hoaRepository = mock(HoaRepository.class);
        when(hoaRepository.findById(1)).thenReturn(new Hoa());

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findUserById(1)).thenReturn(new User());

        ResultsRepository resultsRepository = mock(ResultsRepository.class);
        when(resultsRepository.save(any(ElectionResults.class))).thenReturn(new ElectionResults());

        VoteService voteService = new VoteService(resultsRepository, hoaRepository, userRepository);
        HashMap<Integer, Integer> voteDistributions = new HashMap<>();
        voteDistributions.put(1, 10);
        voteDistributions.put(2, 5);
        int winnerId = 1;
        ElectionResultsModel results = new ElectionResultsModel(15, 15, voteDistributions, winnerId);
        voteService.storeElectionResults(1, results);

        verify(resultsRepository).save(argThat(new ElectionResultsMatcher(new Hoa(), 15, new User())));
    }


    @Test
    public void testStoreRequirementResults() {
        HoaRepository hoaRepository = mock(HoaRepository.class);
        when(hoaRepository.findById(1)).thenReturn(new Hoa());

        ResultsRepository resultsRepository = mock(ResultsRepository.class);
        when(resultsRepository.save(any(RequirementResults.class))).thenReturn(new RequirementResults());

        VoteService voteService = new VoteService(resultsRepository, hoaRepository, null);
        RequirementResultsModel results = new RequirementResultsModel(10, 10, 10, true);
        voteService.storeRequirementResults(1, results);

        verify(resultsRepository).save(argThat(new RequirementResultsMatcher(new Hoa(), 10, 10, true)));
    }

    @Test
    public void testStartElectionVote() {
        HoaRepository hoaRepository = mock(HoaRepository.class);
        Hoa hoa = new Hoa();
        hoa.setMembers(new HashSet<>(Arrays.asList(new UserHoa())));
        when(hoaRepository.findById(1)).thenReturn(hoa);


        VoteService voteService = new VoteService(null, hoaRepository, null);
        VotingModel votingModel = voteService.startElectionVote(1);


        assertEquals(1, votingModel.getHoaId());
        assertEquals(VotingType.ELECTIONS_VOTE, votingModel.getVotingType());
        assertEquals(1, votingModel.getNumberOfEligibleVoters());


    }

    @Test
    public void testStartRequirementVote() {
        HoaRepository hoaRepository = mock(HoaRepository.class);
        Hoa hoa = new Hoa();
        hoa.setMembers(new HashSet<>(Arrays.asList(new UserHoa())));
        when(hoaRepository.findById(1)).thenReturn(hoa);

        VoteService voteService = new VoteService(null, hoaRepository, null);
        VotingModel votingModel = voteService.startRequirementVote(1);

        assertEquals(1, votingModel.getHoaId());
        assertEquals(VotingType.REQUIREMENTS_VOTE, votingModel.getVotingType());
        assertEquals(1, votingModel.getNumberOfEligibleVoters());
    }


}
