package nl.tudelft.sem.template.commons.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateRequirementModel {
    private String name;
    private String description;
    private int hoaId;

    public CreateRequirementModel(String name, String description, int hoaId) {
        this.name = name;
        this.description = description;
        this.hoaId = hoaId;
    }
}
