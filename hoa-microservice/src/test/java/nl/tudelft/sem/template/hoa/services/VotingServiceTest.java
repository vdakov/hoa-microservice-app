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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.argThat;

public class VotingServiceTest {

    @Test
    public void testStoreElectionResults() {
        HoaService hoaService = mock(HoaService.class);
        when(hoaService.getHoaById(1)).thenReturn(new Hoa());

        UserService userService = mock(UserService.class);
        when(userService.findByDisplayName("1")).thenReturn(new User("1"));

        ResultsRepository resultsRepository = mock(ResultsRepository.class);
        when(resultsRepository.save(any(ElectionResults.class))).thenReturn(new ElectionResults());

        VoteService voteService = new VoteService(resultsRepository, hoaService, userService, null);
        HashMap<String, Integer> voteDistributions = new HashMap<>();
        voteDistributions.put("1", 10);
        voteDistributions.put("2", 5);

        ElectionResultsModel results = new ElectionResultsModel(15, 15, voteDistributions);
        voteService.storeElectionResults(1, results);

        verify(resultsRepository).save(argThat(new ElectionResultsMatcher(new Hoa(), 15, new User("1"))));
    }


    @Test
    public void testStoreRequirementResults() {
        HoaService hoaService = mock(HoaService.class);
        when(hoaService.getHoaById(1)).thenReturn(new Hoa());

        ResultsRepository resultsRepository = mock(ResultsRepository.class);
        when(resultsRepository.save(any(RequirementResults.class))).thenReturn(new RequirementResults());

        VoteService voteService = new VoteService(resultsRepository, hoaService, null, null);
        RequirementResultsModel results = new RequirementResultsModel(10, 10, 10, true);
        voteService.storeRequirementResults(1, results);

        verify(resultsRepository).save(argThat(new RequirementResultsMatcher(new Hoa(), 10, 10, true)));
    }

    @Test
    public void testStartElectionVote() {
        HoaService hoaService = mock(HoaService.class);
        Hoa hoa = new Hoa();
        hoa.setMembers(new HashSet<>(Arrays.asList(new UserHoa())));
        when(hoaService.getHoaById(1)).thenReturn(hoa);


        VoteService voteService = new VoteService(null, hoaService, null, null);
        VotingModel votingModel = voteService.startElectionVote(1);


        assertEquals(1, votingModel.getHoaId());
        assertEquals(VotingType.ELECTIONS_VOTE, votingModel.getVotingType());
        assertEquals(1, votingModel.getNumberOfEligibleVoters());


    }

    @Test
    public void testStartRequirementVote() {
        HoaService hoaService = mock(HoaService.class);
        Hoa hoa = new Hoa();
        hoa.setMembers(new HashSet<>(Arrays.asList(new UserHoa())));
        when(hoaService.getHoaById(1)).thenReturn(hoa);

        VoteService voteService = new VoteService(null, hoaService, null, null);
        VotingModel votingModel = voteService.startRequirementVote(1);

        assertEquals(1, votingModel.getHoaId());
        assertEquals(VotingType.REQUIREMENTS_VOTE, votingModel.getVotingType());
        assertEquals(1, votingModel.getNumberOfEligibleVoters());
    }


}
