package nl.tudelft.sem.template.voting.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public class UrlVoterEligibilityChecker implements VoterEligibilityChecker{

    private final transient String url;

    public boolean isVoterEligible(String netId) {
        return true; //TODO: make a request to the given URL and
    }
}
