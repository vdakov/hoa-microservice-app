package nl.tudelft.sem.template.requirements.models;

import lombok.Data;

@Data
public class RequirementModel {
    private int hoaId;
    private String requirementName;
    private String requirementDescription;

    public RequirementModel(int hoaId, String requirementName, String requirementDescription) {
        this.hoaId = hoaId;
        this.requirementName = requirementName;
        this.requirementDescription = requirementDescription;
    }
}
