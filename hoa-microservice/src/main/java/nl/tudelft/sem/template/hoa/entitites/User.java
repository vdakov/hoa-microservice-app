package nl.tudelft.sem.template.hoa.entitites;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.hoa.models.FullUserResponseModel;
import nl.tudelft.sem.template.hoa.models.SimpleUserResponseModel;
import nl.tudelft.sem.template.commons.entities.HasEvents;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.Column;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Data
@Table
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
public class User extends HasEvents {
    /**
     * The primary key of the User class/table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    /**
     * The only distinguishable column of the user class so far
     */
    @Column(name = "displayName", nullable = false, unique = true)
    private String displayName;

    /**
     * Constructor of the User class
     * @param displayName the name the user will be visible as to other people (unique? - yeah, probably)
     */
    public User(String displayName) {
        this.displayName = displayName;
        this.associations = new HashSet<>();
    }

    /**
     * Relationship of user to the Hoa class/table
     *
     * This Spring annotation creates another table that links the
     * two based on their primary keys
     *
     * The reason it is a "ManyToMany" relationship is that many users belong
     * to the same association, but also a user can be a part of multiple associations
     */
    @OneToMany(mappedBy = "user")
    @EqualsAndHashCode.Exclude
    private Set<UserHoa> associations = new HashSet<>();


    /**
     * Method to allow joining of a user to an association
     * TO-DO: routing to gateway
     * TO-DO: turning joining into an application process since right now
     * the user just joins
     * IMPORTANT: check whether modifying the set modifies the table since I am not sure
     *
     * @param - The HOA the user wants to join
     * @return - true iff the joining is successful, false otherwise
     */
    public User joinAssociation(UserHoa connection) {

        this.associations.add(connection);

        return this;
    }

    /**
     * Method to allow the leaving of an association - analogous to upper method, but a user can always leave anyway
     * TO-DO: Routing to gateway
     * IMPORTANT: check whether modifying the set modifies the table since I am not sure
     *
     * @param b - The HOA the user wants to leave
     * @return - true iff the leaving is successful, false otherwise (will only fail if not part of association)
     */
    public boolean leaveAssociation(UserHoa b) {
        if (!associations.contains(b)) return false;
        associations.remove(b);
        return true;
    }

    public SimpleUserResponseModel toSimpleModel() {
        return new SimpleUserResponseModel(this.displayName);
    }


    /**
     * Converts this User object to a FullUserResponseModel object.
     * This DTO (Data Transfer Object) is used to prevent infinite loops when serializing the User object.
     * @return a FullUserResponseModel object with the associations and display name from this User object
     */
    public FullUserResponseModel toFullModel() {
        return new FullUserResponseModel(
            this.getAssociations().stream().map(userHoa -> {
                return userHoa.toUserLessModel();
            }).collect(Collectors.toSet()), 
            this.displayName
        );
    }


}
