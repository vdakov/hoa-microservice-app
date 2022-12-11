package nl.tudelft.sem.template.hoa.entitites;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Column;


@PrimaryKeyJoinColumn(name = "id")
@Entity
@Table
@Data
@NoArgsConstructor
public class BoardMember extends User {

    @ManyToOne
    private Hoa board;

    @Column(name = "yearsOnBoard")
    private int yearsOnBoard;

}
