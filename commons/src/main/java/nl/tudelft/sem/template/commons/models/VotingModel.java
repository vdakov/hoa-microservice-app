package nl.tudelft.sem.template.commons.models;

public class VotingModel {
    private int hoaId;
    private VotingType votingType;
    private int numberOfEligibleVoters;

    public VotingModel(int hoaId, VotingType votingType, int numberOfEligibleVoters) {
        this.hoaId = hoaId;
        this.votingType = votingType;
        this.numberOfEligibleVoters = numberOfEligibleVoters;
    }
}


