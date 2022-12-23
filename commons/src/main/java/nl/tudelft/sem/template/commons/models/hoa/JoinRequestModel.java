package nl.tudelft.sem.template.commons.models.hoa;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JoinRequestModel extends FullAddressModel {
    private String hoaName;
    private String userDisplayName;

    public JoinRequestModel(
        String hoaName, String userDisplayName, String country, 
        String city, String street, String houseNumber, String postalCode
    ) {
        super(country, city, street, houseNumber, postalCode);
        this.hoaName = hoaName;
        this.userDisplayName = userDisplayName;
    }
}

