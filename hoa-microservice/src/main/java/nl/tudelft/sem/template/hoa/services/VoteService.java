package nl.tudelft.sem.template.hoa.services;

import nl.tudelft.sem.template.hoa.entitites.*;
import org.springframework.stereotype.Service;

@Service
public class VoteService {


    //here we will likely need functionality for HTTP requests
    public void submitVoteElection(User user, Vote vote, Hoa hoa){}

    public void changeVoteElection(User user, Vote vote, Hoa hoa){}

    public void submitVoteRequirement(BoardMember member, RequirementVote vote){}

    public void changeVoteRequirement(BoardMember member, RequirementVote vote){}
}
