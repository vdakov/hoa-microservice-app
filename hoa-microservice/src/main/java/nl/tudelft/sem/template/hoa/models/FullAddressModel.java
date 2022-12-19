package nl.tudelft.sem.template.hoa.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FullAddressModel extends AddressModel {
    
    private String street;
    private String houseNumber;
    private String postalCode;

    public FullAddressModel(String country, String city, String street, String houseNumber, String postalCode) {
        super(country, city);
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
    }

}
