package nl.tudelft.sem.template.hoa.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SimpleHoaResponseModel extends HoaResponseModel {
    
    public SimpleHoaResponseModel(String name, String country, String city) {
        super(name, country, city);
    }
}
