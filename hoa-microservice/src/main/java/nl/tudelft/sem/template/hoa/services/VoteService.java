package nl.tudelft.sem.template.hoa.services;

import nl.tudelft.sem.template.commons.entities.ElectionVote;
import nl.tudelft.sem.template.commons.entities.RequirementVote;
import org.springframework.stereotype.Service;


/**
 * Skeleton implementation for voting service
 *
 * Will likely be filled with HTTP request to be given to the voting microservice, but currently only left like
 * this to minimize refactoring of other classes and will be expanded depending on the implementation of
 * the Voting Microservice and how it will be implemented
 */
@Service
public class VoteService {


    public void submitVoteElection(int id, ElectionVote vote, int hoaId) {
    }

    public void changeVoteElection(int id, ElectionVote vote, int hoaId) {
    }

    public void submitVoteRequirement(int id, RequirementVote vote) {
    }

    public void changeVoteRequirement(int id, RequirementVote vote) {
    }

    public void enrolElection(int id, int hoaId) {
    }

    public void unenrolElection(int id, int hoaId) {
    }
}
