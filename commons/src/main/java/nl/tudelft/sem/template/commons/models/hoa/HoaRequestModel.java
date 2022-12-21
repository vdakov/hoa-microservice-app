package nl.tudelft.sem.template.commons.models.hoa;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HoaRequestModel extends AddressModel {
    private String name;

    public HoaRequestModel(String name, String country, String city) {
        super(country, city);
        this.name = name;
    }

    public boolean anyNull() {
        return this.name == null || super.anyNull();
    }

}