package nl.tudelft.sem.template.commons.models;


import lombok.Data;

@Data
public class RequirementResultsModel extends ResultsModel {

    private RequirementModel requirement;
    private int votedFor;
    private boolean passed;

    public RequirementResultsModel(int eligibleVotes, int numberOfVotes, int votedFor, boolean passed) {
        super(eligibleVotes, numberOfVotes);
        this.votedFor = votedFor;
        this.passed = passed;
    }
}
