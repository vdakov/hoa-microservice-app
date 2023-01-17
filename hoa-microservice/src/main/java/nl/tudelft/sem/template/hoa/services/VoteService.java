package nl.tudelft.sem.template.hoa.services;

import nl.tudelft.sem.template.commons.models.ElectionResultsModel;
import nl.tudelft.sem.template.commons.models.RequirementResultsModel;
import nl.tudelft.sem.template.commons.models.VotingModel;
import nl.tudelft.sem.template.commons.models.VotingType;
import nl.tudelft.sem.template.hoa.entitites.User;
import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.hoa.entitites.ElectionResults;
import nl.tudelft.sem.template.hoa.entitites.BoardMember;
import nl.tudelft.sem.template.hoa.entitites.RequirementResults;
import nl.tudelft.sem.template.hoa.repositories.ResultsRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Arrays;


@Service
public class VoteService {

    private transient ResultsRepository resultsRepository;
    
    private transient HoaService hoaService;
    private transient UserService userService;
    private transient BoardMemberService boardMemberService;

    public VoteService(ResultsRepository resultsRepository, HoaService hoaService,
                       UserService userService, BoardMemberService boardMemberService) {
        this.resultsRepository = resultsRepository;
        this.hoaService = hoaService;
        this.userService = userService;
        this.boardMemberService = boardMemberService;
    }

    /**
     * @param hoaId   the id of the hoa
     * @param results the results of the election
     */
    public void storeElectionResults(int hoaId, ElectionResultsModel results) {
        Hoa hoa = hoaService.getHoaById(hoaId);
        User winner = userService.findByDisplayName(Collections.max(results.getVoteDistributions().entrySet(),
                Comparator.comparingInt(Map.Entry::getValue)).getKey());

        resultsRepository.save(new ElectionResults(hoa, results.getNumberOfVotes(), results.getVoteDistributions(), winner));
    }

    /**
     * Converts the DTO object received into a DB object for the HOA and stores it
     */
    public void storeRequirementResults(int hoaId, RequirementResultsModel results) {
        Hoa hoa = hoaService.getHoaById(hoaId);

        resultsRepository.save(new RequirementResults(hoa, results.getNumberOfVotes(), results.getVotedFor(),
                results.isPassed()));
    }

    /**
     * Creates a DTO for starting an election vote once requested from Gateway Microservice
     */
    public VotingModel startElectionVote(int hoaId) {
        Hoa hoa = hoaService.getHoaById(hoaId);
        int numEligibleVotes = hoa.getMembers().size();
        VotingType type = VotingType.ELECTIONS_VOTE;
        List<String> candidates = Arrays.asList("candidate0", "candidate1", "candidate2");
        //TODO: get a list of candidates later!
        VotingModel votingModel = new VotingModel(hoaId, type, numEligibleVotes, candidates);
        System.out.println(votingModel);
        return votingModel;
    }

    /**
     * Creates a DTO for starting a requirement vote once requested from Gateway
     */
    public VotingModel startRequirementVote(int hoaId) {
        Hoa hoa = hoaService.getHoaById(hoaId);
        int numEligibleVotes = hoa.getMembers().size();
        VotingType type = VotingType.REQUIREMENTS_VOTE;
        return new VotingModel(hoaId, type, numEligibleVotes, Collections.emptyList());
    }

    public List<BoardMember> getListEligibleMembers(int hoaId){
        Hoa hoa = hoaService.getHoaById(hoaId);
        return this.boardMemberService.findBoardMemberByBoard(hoa);
    }


}
