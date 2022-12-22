package nl.tudelft.sem.template.voting.domain;

import java.util.List;

public class ElectionVote extends Vote {

    public ElectionVote(int hoaId,
                        List<String> options,
                        TimeKeeper timeKeeper,
                        VoterEligibilityChecker voterEligibilityChecker,
                        int numberOfEligibleVoters) {
        super(hoaId, options, timeKeeper, voterEligibilityChecker, numberOfEligibleVoters);
    }

}
