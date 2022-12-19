package nl.tudelft.sem.template.hoa.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FullUserHoaModel extends UserHoaModel {
    private SimpleUserResponseModel user;
    private SimpleHoaResponseModel hoa;

    public FullUserHoaModel(
        SimpleUserResponseModel user, SimpleHoaResponseModel hoa, 
        String country, String city, String street, String houseNumber, 
        String postalcode
    ) {
        super(country, city, street, houseNumber, postalcode);
        this.user = user;
        this.hoa = hoa;
    }
}
