package nl.tudelft.sem.template.commons.models.hoa;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JoinModel extends FullAddressModel {
    private String hoaName;
    private String userDisplayName;

    public JoinModel(String country, String city, String street, String houseNumber,
                     String postalCode, String hoaName, String userDisplayName){
        super(country, city, street, houseNumber, postalCode);
        this.hoaName = hoaName;
        this.userDisplayName = userDisplayName;
    }
}

