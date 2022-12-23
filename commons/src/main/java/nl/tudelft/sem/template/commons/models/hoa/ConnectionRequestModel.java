package nl.tudelft.sem.template.commons.models.hoa;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConnectionRequestModel extends HoaRequestModel {
    private String displayName;

    public ConnectionRequestModel(String displayName, String hoaName, String country, String city) {
        super(hoaName, country, city);
        this.displayName = displayName;
    }

    public boolean anyNull() {
        return this.displayName == null || super.anyNull();
    }

}
