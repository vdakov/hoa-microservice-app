package nl.tudelft.sem.template.voting.exceptions;

public class VoteOngoingException extends VotingException{
    static final long serialVersionUID = 1L;
    public VoteOngoingException(String errorMessage) {
        super(errorMessage);
    }
}
