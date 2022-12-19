package nl.tudelft.sem.template.hoa.entitites;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@MappedSuperclass
public abstract class HasFullAddress extends HasAddress {
    @Column
    private String street;

    @Column
    private String houseNumber;

    @Column
    private String postalCode;
    
    public HasFullAddress(String country, String city, String street, String houseNumber, String postalCode) {
        super(country, city);
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
    }

}
