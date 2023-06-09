package nl.tudelft.sem.template.commons.models.hoa;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class UserHoaModel extends FullAddressModel {

    public UserHoaModel(String country, String city, String street, String houseNumber, String postalCode) {
        super(country, city, street, houseNumber, postalCode);
    }
}
