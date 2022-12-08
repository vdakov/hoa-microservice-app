package nl.tudelft.sem.template.hoa.entitites;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "hoas")
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

    /**
     * Constructor for HOA.
     */
    public Hoa(String name, String country, String city) {
        this.name = name;
        this.city = city;
        this.country = country;
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

}
