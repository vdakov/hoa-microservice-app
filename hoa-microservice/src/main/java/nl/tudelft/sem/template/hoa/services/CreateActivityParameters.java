package nl.tudelft.sem.template.hoa.services;

import nl.tudelft.sem.template.commons.models.DateModel;

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

    public int getHoaId(){
        return this.hoaId;
    }

    public String getName(){
        return this.name;
    }

    public DateModel getTime(){
        return this.time;
    }

    public String getDescription(){
        return this.description;
    }
}
