package nl.tudelft.sem.template.commons.models;

import lombok.Data;

import java.util.Map;

@Data
public class ElectionResultsModel extends ResultsModel {
    private Map<String, Integer> voteDistributions;

    public ElectionResultsModel(int eligibleVotes, int numberOfVotes,
                                Map<String, Integer> voteDistributions) {
        super(eligibleVotes, numberOfVotes);
        this.voteDistributions = voteDistributions;
    }
}
