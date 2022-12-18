package nl.tudelft.sem.template.hoa.entitites;


import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.commons.entities.HasEvents;
import nl.tudelft.sem.template.commons.models.ResultsModel;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
/**
 * Currently only a skeleton implementation of vote and will change depending on Elections microservice
 */
public abstract class Results extends HasEvents {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    private Hoa hoa;

   //ResultsModel results



    public Results(Hoa hoa, ResultsModel results) {
       // this.results = results;
        this.hoa = hoa;
    }


}
