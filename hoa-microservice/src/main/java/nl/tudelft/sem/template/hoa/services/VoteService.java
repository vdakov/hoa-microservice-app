package nl.tudelft.sem.template.hoa.services;

import nl.tudelft.sem.template.commons.models.ElectionResultsModel;
import nl.tudelft.sem.template.commons.models.RequirementResultsModel;
import nl.tudelft.sem.template.hoa.entitites.ElectionResults;
import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.hoa.entitites.RequirementResults;
import nl.tudelft.sem.template.hoa.entitites.User;
import nl.tudelft.sem.template.hoa.repositories.HoaRepository;
import nl.tudelft.sem.template.hoa.repositories.ResultsRepository;
import nl.tudelft.sem.template.hoa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;


/**
 * Skeleton implementation for voting service
 * <p>
 * Will likely be filled with HTTP request to be given to the voting microservice, but currently only left like
 * this to minimize refactoring of other classes and will be expanded depending on the implementation of
 * the Voting Microservice and how it will be implemented
 */
@Service
public class VoteService {

    @Autowired
    private transient ResultsRepository resultsRepository;
    @Autowired
    private transient HoaRepository hoaRepository;
    @Autowired
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
        User winner = userRepository.findUserById(Collections.max(results.getVoteDistributions().entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey());
        resultsRepository.save(new ElectionResults(hoa, results.getNumberOfVotes(), results.getVoteDistributions(), winner));
    }

    public void storeRequirementResults(int hoaId, RequirementResultsModel results) {
        Hoa hoa = hoaRepository.findById(hoaId);

        resultsRepository.save(new RequirementResults(hoa, results.getNumberOfVotes(), results.getVotedFor(),
                results.isPassed()));
    }


}
