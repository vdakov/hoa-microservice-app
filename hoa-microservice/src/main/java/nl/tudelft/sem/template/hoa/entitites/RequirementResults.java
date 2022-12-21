package nl.tudelft.sem.template.hoa.entitites;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@PrimaryKeyJoinColumn(name = "id")
@Data
@Entity
@Table
@NoArgsConstructor
public class RequirementResults extends Results {


    private int votedFor;
    private boolean passed;


    /**
     * Currently only a skeleton that will be expanded once the microservices are linked
     *
     * @param hoa the id of the Hoa this is for
     */
    public RequirementResults(Hoa hoa, int numberOfVotes, int votedFor, boolean passed) {
        super(hoa, numberOfVotes);
        this.votedFor = votedFor;
        this.passed = passed;
    }
}
