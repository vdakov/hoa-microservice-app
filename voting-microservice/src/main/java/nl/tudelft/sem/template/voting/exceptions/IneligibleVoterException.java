package nl.tudelft.sem.template.voting.exceptions;

public class IneligibleVoterException extends VotingException {
    static final long serialVersionUID = 1L;

    public IneligibleVoterException(String errorMessage) {
        super(errorMessage);
    }
}
