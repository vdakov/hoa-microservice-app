package nl.tudelft.sem.template.commons.models;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultsModel {
    private int eligibleVotes;
    private int numberOfVotes;

    public ResultsModel(int eligibleVotes, int numberOfVotes) {
        this.eligibleVotes = eligibleVotes;
        this.numberOfVotes = numberOfVotes;
    }
}
