package nl.tudelft.sem.template.voting.domain;

/**
 * An interface which will provide the method to check whether a voter is eligible for an election
 */
public interface VoterEligibilityChecker {

    public boolean isVoterEligible(String userId);
}
