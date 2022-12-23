package nl.tudelft.sem.template.commons.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateRequirementModel {
    private String newName;
    private String newDescription;
    private int requirementId;

    public UpdateRequirementModel(String newName, String newDescription, int requirementId) {
        this.newName = newName;
        this.newDescription = newDescription;
        this.requirementId = requirementId;
    }
}
