package nl.tudelft.sem.template.hoa.entitites;


import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.commons.entities.HasEvents;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToOne;

@Data
@Table
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@NoArgsConstructor
/**
 * Currently only a skeleton implementation of vote and will change depending on Elections microservice
 */
public abstract class Results extends HasEvents {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @OneToOne
    private Hoa hoa;

    private int numberOfVotes;


    public Results(Hoa hoa, int numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
        this.hoa = hoa;
    }


}
