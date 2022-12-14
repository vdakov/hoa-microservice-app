package nl.tudelft.sem.template.hoa.models;

import lombok.Data;

@Data
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
