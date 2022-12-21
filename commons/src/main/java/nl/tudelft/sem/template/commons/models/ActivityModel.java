package nl.tudelft.sem.template.commons.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ActivityModel {
    private int hoaId;
    private String name;
    private DateModel time;
    private String description;

    public ActivityModel(int hoaId, String name, DateModel time, String description) {
        this.hoaId = hoaId;
        this.name = name;
        this.time = time;
        this.description = description;
    }
}
