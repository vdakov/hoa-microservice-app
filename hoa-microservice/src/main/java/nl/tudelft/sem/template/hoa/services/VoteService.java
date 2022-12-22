package nl.tudelft.sem.template.hoa.services;

import nl.tudelft.sem.template.commons.models.ElectionResultsModel;
import nl.tudelft.sem.template.commons.models.RequirementResultsModel;
import nl.tudelft.sem.template.commons.models.VotingModel;
import nl.tudelft.sem.template.commons.models.VotingType;
import nl.tudelft.sem.template.hoa.entitites.ElectionResults;
import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.hoa.entitites.RequirementResults;
import nl.tudelft.sem.template.hoa.entitites.User;
import nl.tudelft.sem.template.hoa.repositories.HoaRepository;
import nl.tudelft.sem.template.hoa.repositories.ResultsRepository;
import nl.tudelft.sem.template.hoa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Arrays;
import java.util.List;


@Service
public class VoteService {

    private transient ResultsRepository resultsRepository;
    private transient HoaRepository hoaRepository;
    private transient UserRepository userRepository;

    public VoteService(ResultsRepository resultsRepository, HoaRepository hoaRepository, UserRepository userRepository) {
        this.resultsRepository = resultsRepository;
        this.hoaRepository = hoaRepository;
        this.userRepository = userRepository;
    }

    /**
     * @param hoaId   the id of the hoa
     * @param results the results of the election
     */
    public void storeElectionResults(int hoaId, ElectionResultsModel results) {
        Hoa hoa = hoaRepository.findById(hoaId);
        User winner = userRepository.findUserById(Collections.max(results.getVoteDistributions().entrySet(),
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
        Hoa hoa = hoaRepository.findById(hoaId);
        int numEligibleVotes = hoa.getMembers().size();
        VotingType type = VotingType.REQUIREMENTS_VOTE;
        return new VotingModel(hoaId, type, numEligibleVotes, Collections.emptyList());
    }


}
