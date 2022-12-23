package nl.tudelft.sem.template.commons.models;

import lombok.Data;


import java.util.Map;

@Data
public class ElectionResultsModel extends ResultsModel {
    private Map<Integer, Integer> voteDistributions;
    private int winnerId;

    public ElectionResultsModel(int eligibleVotes, int numberOfVotes,
                                Map<Integer, Integer> voteDistributions, int winnerId) {
        super(eligibleVotes, numberOfVotes);
        this.voteDistributions = voteDistributions;
        this.winnerId = winnerId;
    }
}
