package nl.tudelft.sem.template.commons.models;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class HoaModel {
    private String name;
    private String country;
    private String city;

    /**
     * Needed for postman manual testing
     */
    public HoaModel(){

    }


    public HoaModel(String name, String country, String city) {
        this.name = name;
        this.country = country;
        this.city = city;
    }
}
