package nl.tudelft.sem.template.hoa.entitites;

import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
public class User extends HasEvents {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "displayName", nullable = false, unique = true)
    private String displayName;

    @OneToMany
    private Set<Hoa> associations;

    public User(String displayName) {
        this.displayName = displayName;
        this.associations = new HashSet<>();
    }


    /**
     * Method to allow joining of a user to an association
     * TO-DO: routing to gateway
     * TO-DO: turning joining into an application process
     *
     * @param - The HOA the user wants to join
     */
    public void joinAssociation(Hoa a) {
        associations.add(a);
    }

    public void leaveAssociation(Hoa b) {
        if (associations.contains(b)) associations.remove(b);
    }

    public void createAssociation() {

    }

    public void submitVoteElection() {
    }

    public void changeVote() {
    }
}
