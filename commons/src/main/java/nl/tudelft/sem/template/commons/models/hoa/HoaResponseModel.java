package nl.tudelft.sem.template.commons.models.hoa;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class HoaResponseModel extends AddressModel {
    private String name;
    
    public HoaResponseModel(String name, String country, String city) {
        super(country, city);
        this.name = name;
    }
}
