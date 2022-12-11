package nl.tudelft.sem.template.voting.domain;

public class VotingException extends Exception{
    public VotingException(String errorMessage) {
        super(errorMessage);
    }
}
