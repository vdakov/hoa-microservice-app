package nl.tudelft.sem.template.requirements.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.commons.entities.HasEvents;

@Data
@Entity
@Table(name = "requirements")
@NoArgsConstructor
public class Requirements{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "hoaId", nullable = false)
    private int hoaId;

    @Column(name = "requirementName", nullable = false)
    private String requirementName;

    @Column(name = "requirementDescription", nullable = false)
    private String requirementDescription;

    public Requirements(int hoaId, String name, String description) {
        this.hoaId = hoaId;
        this.requirementName = name;
        this.requirementDescription = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Requirements that = (Requirements) o;
        return id == that.id && hoaId == that.hoaId
                && requirementName.equals(that.requirementName)
                && requirementDescription.equals(that.requirementDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hoaId, requirementName, requirementDescription);
    }
}
