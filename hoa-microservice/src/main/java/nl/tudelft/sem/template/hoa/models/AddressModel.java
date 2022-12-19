package nl.tudelft.sem.template.hoa.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class AddressModel {
    private String country;
    private String city;

    public AddressModel(String country, String city) {
        this.country = country;
        this.city = city;
    }
}
