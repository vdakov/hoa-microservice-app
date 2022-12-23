package nl.tudelft.sem.template.commons.models;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequirementResultsModel extends ResultsModel {

    private int votedFor;
    private boolean passed;

    public RequirementResultsModel(int eligibleVotes, int numberOfVotes, int votedFor, boolean passed) {
        super(eligibleVotes, numberOfVotes);
        this.votedFor = votedFor;
        this.passed = passed;
    }
}
