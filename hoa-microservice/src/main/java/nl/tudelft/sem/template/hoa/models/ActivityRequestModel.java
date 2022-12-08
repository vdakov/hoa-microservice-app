package nl.tudelft.sem.template.hoa.models;

import lombok.Data;

@Data
public class ActivityRequestModel {
    private String name;
    private DateModel time;
    private String description;

}
