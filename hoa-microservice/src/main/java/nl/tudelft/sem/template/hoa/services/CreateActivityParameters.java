package nl.tudelft.sem.template.hoa.services;

import lombok.Getter;
import nl.tudelft.sem.template.commons.models.DateModel;

@Getter
public class CreateActivityParameters {
    private final int hoaId;
    private final String name;
    private final DateModel time;
    private final String description;

    public CreateActivityParameters(int hoaId, String name, DateModel time, String description){
        this.hoaId = hoaId;
        this.name = name;
        this.time = time;
        this.description = description;
    }
}
