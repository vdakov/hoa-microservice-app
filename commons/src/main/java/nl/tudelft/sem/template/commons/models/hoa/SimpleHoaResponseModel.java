package nl.tudelft.sem.template.commons.models.hoa;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SimpleHoaResponseModel extends HoaResponseModel {
    
    public SimpleHoaResponseModel(String name, String country, String city) {
        super(name, country, city);
    }
}
