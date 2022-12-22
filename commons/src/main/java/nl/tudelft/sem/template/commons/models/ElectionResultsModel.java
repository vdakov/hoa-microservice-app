package nl.tudelft.sem.template.commons.models;

import lombok.Data;


import java.util.HashMap;

@Data
public class ElectionResultsModel extends ResultsModel {
    private HashMap<Integer, Integer> voteDistributions;
    private int winnerId;

    public ElectionResultsModel(int eligibleVotes, int numberOfVotes,
                                HashMap<Integer, Integer> voteDistributions, int winnerId) {
        super(eligibleVotes, numberOfVotes);
        this.voteDistributions = voteDistributions;
        this.winnerId = winnerId;
    }
}
