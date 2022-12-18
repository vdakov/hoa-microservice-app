package nl.tudelft.sem.template.commons.entities;


import lombok.Data;

@Data
/**
 * Currently only a skeleton implementation of vote and will change depending on Elections microservice
 */
public abstract class Vote extends HasEvents {
    private int hoaId;

    public Vote(int hoaId) {
        this.hoaId = hoaId;
    }
}
