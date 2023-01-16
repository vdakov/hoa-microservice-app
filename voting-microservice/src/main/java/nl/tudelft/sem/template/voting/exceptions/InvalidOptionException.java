package nl.tudelft.sem.template.voting.exceptions;

public class InvalidOptionException extends VotingException {
    static final long serialVersionUID = 1L;

    public InvalidOptionException(String errorMessage) {
        super(errorMessage);
    }
}
