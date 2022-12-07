package nl.tudelft.sem.template.pnb.models;

import lombok.Data;

@Data
public class ActivityRequestModel {
    private String name;
    private DateModel time;
    private String description;

}
