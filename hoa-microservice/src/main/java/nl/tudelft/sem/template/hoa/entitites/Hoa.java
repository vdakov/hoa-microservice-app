package nl.tudelft.sem.template.hoa.entitites;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.hoa.domain.activity.Activity;

import javax.persistence.CascadeType;
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

    @OneToMany(cascade = {CascadeType.ALL})
    private Set<Activity> activities;


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

    public void changeCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Hoa)) {
            return false;
        }
        Hoa hoa = (Hoa) o;
        return id == hoa.id && Objects.equals(name, hoa.name) && Objects.equals(country, hoa.country)
                && Objects.equals(city, hoa.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country, city);
    }

    public int getId(){
        return id;
    }

}
