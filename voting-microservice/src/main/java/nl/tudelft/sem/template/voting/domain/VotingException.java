package nl.tudelft.sem.template.voting.domain;

public class VotingException extends Exception{
    static final long serialVersionUID = 1L;

    public VotingException(String errorMessage) {
        super(errorMessage);
    }
}
