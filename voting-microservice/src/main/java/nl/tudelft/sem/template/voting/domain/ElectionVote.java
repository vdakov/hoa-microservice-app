package nl.tudelft.sem.template.voting.domain;

import java.util.List;

public class ElectionVote extends Vote {

    public ElectionVote(int hoaId, List<String> options, TimeKeeper timeKeeper) {
        super(hoaId, options, timeKeeper);
    }

    public boolean isVoterEligible(String netId) {
        return true; // TODO: return truth value based on user's authorities
    }
}
