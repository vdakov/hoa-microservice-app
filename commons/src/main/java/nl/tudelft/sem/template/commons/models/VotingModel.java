package nl.tudelft.sem.template.commons.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
/**
 * A DTO for starting a vote
 */
public class VotingModel {
    private int hoaId;
    private VotingType votingType;
    private int numberOfEligibleVoters;
    private List<String> options;

    public VotingModel(int hoaId, VotingType votingType, int numberOfEligibleVoters, List<String> options) {
        this.hoaId = hoaId;
        this.votingType = votingType;
        this.numberOfEligibleVoters = numberOfEligibleVoters;
        this.options = options;
    }
}


