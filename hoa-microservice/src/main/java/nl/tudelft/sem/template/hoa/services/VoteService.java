package nl.tudelft.sem.template.hoa.services;

import nl.tudelft.sem.template.commons.models.ElectionResultsModel;
import nl.tudelft.sem.template.commons.models.RequirementResultsModel;
import nl.tudelft.sem.template.commons.models.VotingModel;
import nl.tudelft.sem.template.commons.models.VotingType;
import nl.tudelft.sem.template.hoa.entitites.*;
import nl.tudelft.sem.template.hoa.repositories.BoardMemberRepository;
import nl.tudelft.sem.template.hoa.repositories.HoaRepository;
import nl.tudelft.sem.template.hoa.repositories.ResultsRepository;
import nl.tudelft.sem.template.hoa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;



@Service
public class VoteService {

    private transient ResultsRepository resultsRepository;
    private transient HoaRepository hoaRepository;
    private transient UserRepository userRepository;
    private transient BoardMemberRepository boardMemberRepository;

    public VoteService(ResultsRepository resultsRepository, HoaRepository hoaRepository, UserRepository userRepository, BoardMemberRepository boardMemberRepository) {
        this.resultsRepository = resultsRepository;
        this.hoaRepository = hoaRepository;
        this.userRepository = userRepository;
        this.boardMemberRepository = boardMemberRepository;
    }

    /**
     * @param hoaId   the id of the hoa
     * @param results the results of the election
     */
    public void storeElectionResults(int hoaId, ElectionResultsModel results) {
        Hoa hoa = hoaRepository.findById(hoaId);
        User winner = userRepository.findByDisplayName(Collections.max(results.getVoteDistributions().entrySet(),
                Comparator.comparingInt(Map.Entry::getValue)).getKey());

        resultsRepository.save(new ElectionResults(hoa, results.getNumberOfVotes(), results.getVoteDistributions(), winner));
    }

    /**
     * Converts the DTO object received into a DB object for the HOA and stores it
     */
    public void storeRequirementResults(int hoaId, RequirementResultsModel results) {
        Hoa hoa = hoaRepository.findById(hoaId);

        resultsRepository.save(new RequirementResults(hoa, results.getNumberOfVotes(), results.getVotedFor(),
                results.isPassed()));
    }

    /**
     * Creates a DTO for starting an election vote once requested from Gateway Microservice
     */
    public VotingModel startElectionVote(int hoaId) {
        Hoa hoa = hoaRepository.findById(hoaId);
        int numEligibleVotes = hoa.getMembers().size();
        VotingType type = VotingType.ELECTIONS_VOTE;

        return new VotingModel(hoaId, type, numEligibleVotes);
    }

    /**
     * Creates a DTO for starting a requirement vote once requested from Gateway
     */
    public VotingModel startRequirementVote(int hoaId) {
        Hoa hoa = hoaRepository.findById(hoaId);
        int numEligibleVotes = hoa.getMembers().size();
        VotingType type = VotingType.REQUIREMENTS_VOTE;

        return new VotingModel(hoaId, type, numEligibleVotes);
    }

    public List<BoardMember> getListEligibleMembers(int hoaId){
        Hoa hoa = hoaRepository.findById(hoaId);
        return this.boardMemberRepository.findBoardMemberByBoard(hoa);
    }


}
