package nl.tudelft.sem.template.hoa.entitites;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private List<Integer> idsOfCandidates;

    @OneToOne
    private User winner;

    public ElectionResults(Hoa hoa, int numberOfVotes, HashMap<Integer, Integer> votes, User winner) {
        super(hoa, numberOfVotes);
        this.idsOfCandidates = new ArrayList<>();
        this.votesPerUser = new ArrayList<>();
        this.idsOfCandidates.addAll(votes.keySet());
        for (int id : votes.keySet()) {
            votesPerUser.add(votes.get(id));
        }
        this.winner = winner;

    }

}
