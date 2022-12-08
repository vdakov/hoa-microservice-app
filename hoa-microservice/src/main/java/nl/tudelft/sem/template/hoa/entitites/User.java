package nl.tudelft.sem.template.hoa.entitites;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User extends HasEvents {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "displayName", nullable = false, unique = true)
    private String displayName;

    public User(String displayName) {
        this.displayName = displayName;
    }

    public User() {

    }

    /**
     * Method to allow joining of a user to an association
     * TO-DO: routing to gateway
     * TO-DO: turning joining into an application process
     * @param id the id of the assocation in the database
     */
    public void joinAssociation(int id) {
        //if(!memberships.containsKey(id)) memberships.put(id,)
    }

    // =============================== TO- DO ================================
    public void leaveAssociation(int id) {
    }

    public void createAssociation() {
    }

    public void submitVoteElection() {
    }

    public void changeVote() {
    }


}
