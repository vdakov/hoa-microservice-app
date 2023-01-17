package nl.tudelft.sem.template.voting.exceptions;

public class VoteClosedException extends VotingException {
    static final long serialVersionUID = 1L;

    public VoteClosedException(String errorMessage) {
        super(errorMessage);
    }
}
