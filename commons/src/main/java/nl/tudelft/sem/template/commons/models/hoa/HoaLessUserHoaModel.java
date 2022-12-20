package nl.tudelft.sem.template.commons.models.hoa;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HoaLessUserHoaModel extends UserHoaModel {
    private SimpleUserResponseModel user;

    public HoaLessUserHoaModel(
        SimpleUserResponseModel user, String country, String city, String street, String houseNumber, String postalCode
    ) {
        super(country, city, street, houseNumber, postalCode);
        this.user = user;
    }
}
