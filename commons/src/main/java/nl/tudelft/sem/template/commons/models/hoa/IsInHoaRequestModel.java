package nl.tudelft.sem.template.commons.models.hoa;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IsInHoaRequestModel extends HoaRequestModel {
    private String displayName;

    public IsInHoaRequestModel(String displayName, String hoaName, String country, String city) {
        super(hoaName, country, city);
        this.displayName = displayName;
    }

    public boolean anyNull() {
        return this.displayName == null || super.anyNull();
    }

}
