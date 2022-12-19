package nl.tudelft.sem.template.commons.models;

import lombok.Data;

@Data
public class CreateRequirementModel {
    private String name;
    private String description;
    private int hoaId;
}
