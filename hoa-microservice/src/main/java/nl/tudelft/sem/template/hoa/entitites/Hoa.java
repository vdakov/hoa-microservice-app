package nl.tudelft.sem.template.hoa.entitites;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.hoa.models.FullHoaResponseModel;
import nl.tudelft.sem.template.hoa.models.SimpleHoaResponseModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;

@Data
@Entity
@Table
@NoArgsConstructor
public class Hoa extends HasEvents {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "city", nullable = false)
    private String city;

    @ManyToMany
    private Set<User> members;

    @OneToMany
    private Set<BoardMember> boardMembers;


    /**
     * Constructor for HOA.
     */
    public Hoa(String name, String country, String city) {
        this.name = name;
        this.city = city;
        this.country = country;
        this.members = new HashSet<>();
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeCountry(String country) {
        this.country = country;
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

}
