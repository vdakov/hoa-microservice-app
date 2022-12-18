package nl.tudelft.sem.template.hoa.entitites;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.NaturalId;

import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
public abstract class HasAddress extends HasEvents {
    
    @Column(name = "country", nullable = false)
    @NaturalId
    private String country;

    @Column(name = "city", nullable = false)
    @NaturalId
    private String city;

    public HasAddress(String country, String city) {
        this.country = country;
        this.city = city;
    }
}
