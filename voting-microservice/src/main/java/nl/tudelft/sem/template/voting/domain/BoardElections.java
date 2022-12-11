package nl.tudelft.sem.template.voting.domain;

import java.util.List;

public class BoardElections extends Voting {

    public BoardElections(int hoaId, List<String> options, TimeKeeper timeKeeper) {
        super(hoaId, options, timeKeeper);
    }

    public boolean isVoterEligible(int userId) {
        return true; // all members of an HOA are eligible to vote in a board election
    }
}
