package nl.tudelft.sem.template.hoa.entitites;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.ElementCollection;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@PrimaryKeyJoinColumn(name = "id")
@Entity
@Table
@NoArgsConstructor
@Data
public class ElectionResults extends Results {

    /**
     * Collection to store how many votes each member got in the election
     */

    @ElementCollection
    private List<Integer> votesPerUser;

    @ElementCollection
    private List<String> idsOfCandidates;

    @OneToOne
    private User winner;

    /**
     * Constructor
     */
    public ElectionResults(Hoa hoa, int numberOfVotes, Map<String, Integer> votes, User winner) {
        super(hoa, numberOfVotes);
        this.idsOfCandidates = new ArrayList<>();
        this.votesPerUser = new ArrayList<>();
        this.idsOfCandidates.addAll(votes.keySet());
        for (String id : votes.keySet()) {
            votesPerUser.add(votes.get(id));
        }
        this.winner = winner;

    }

}
