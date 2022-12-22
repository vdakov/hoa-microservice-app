package nl.tudelft.sem.template.voting.domain;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TemporaryVoterEligibilityChecker implements VoterEligibilityChecker{

    public boolean isVoterEligible(String url) {
        return true;
    }
}
