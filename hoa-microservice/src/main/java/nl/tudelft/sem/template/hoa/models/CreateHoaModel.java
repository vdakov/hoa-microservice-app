package nl.tudelft.sem.template.hoa.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateHoaModel {
    private String name;
    private String country;
    private String city;

    public CreateHoaModel(String name, String country, String city) {
        this.name = name;
        this.country = country;
        this.city = city;
    }

}