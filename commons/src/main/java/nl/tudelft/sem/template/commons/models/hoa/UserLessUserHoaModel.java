package nl.tudelft.sem.template.commons.models.hoa;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLessUserHoaModel extends UserHoaModel {
    private SimpleHoaResponseModel hoa;

    public UserLessUserHoaModel(
        SimpleHoaResponseModel hoa, String country,
        String city, String street, String houseNumber, String postalCode
    ) {
        super(country, city, street, houseNumber, postalCode);
        this.hoa = hoa;
    }
}
