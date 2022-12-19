package nl.tudelft.sem.template.hoa.entitites;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.hoa.models.FullHoaResponseModel;
import nl.tudelft.sem.template.hoa.models.SimpleHoaResponseModel;
import nl.tudelft.sem.template.commons.entities.HasEvents;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table
@NoArgsConstructor
public class Hoa extends HasAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    @NaturalId
    private String name;

    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "hoa")
    @EqualsAndHashCode.Exclude
    private Set<UserHoa> members = new HashSet<>();

    @OneToMany
    @JsonIgnore
    private transient Set<BoardMember> boardMembers = new HashSet<>();


    public Hoa addMember(UserHoa connection) {
        this.members.add(connection);

        return this;
    }

    @OneToMany(cascade = {CascadeType.ALL})
    private Set<Activity> activities;


    /**
     * Constructor for HOA.
     */
    public Hoa(String name, String country, String city) {
        super(country, city);
        this.name = name;
        this.members = new HashSet<>();
        this.boardMembers = new HashSet<>();
    }

    public Hoa(int id, String name, String country, String city, Set<BoardMember> boardMembers, Set<UserHoa> members) {
        super(country, city);
        this.id = id;
        this.name = name;
        this.boardMembers = boardMembers;
        this.members = members;
    }


    /**
     * Converts this Hoa object to a FullHoaResponseModel object.
     * This DTO is used to prevent infinite loops when serializing the Hoa object.
     * @return a FullHoaResponseModel object with the members and address information from this Hoa object
     */
    public FullHoaResponseModel toFullModel() {
        return new FullHoaResponseModel(
            this.members.stream().map(member -> {
                return member.toHoaLessModel();
            }).collect(Collectors.toSet()), 
            this.name, this.getCountry(), this.getCity()
        );
    }


    public SimpleHoaResponseModel toSimpleModel() {
        return new SimpleHoaResponseModel(this.name, this.getCountry(), this.getCity());
    }

    public int getId(){
        return id;
    }

}
