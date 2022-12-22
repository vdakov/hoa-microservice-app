package nl.tudelft.sem.template.commons.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
public class HoaModel {
    private String name;
    private String country;
    private String city;



    public HoaModel(String name, String country, String city) {
        this.name = name;
        this.country = country;
        this.city = city;
    }
}
