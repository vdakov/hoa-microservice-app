package nl.tudelft.sem.template.requirements.domain;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "requirements")
@NoArgsConstructor
public class Requirements extends HasEvents {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "requirementName", nullable = false, unique = true)
    private String requirementName;

    @Column(name = "requirementDescription", nullable = false)
    private String requirementDescription;

    public Requirements(String name, String description) {
        this.requirementName = name;
        this.requirementDescription = description;
    }

    public int getId() {
        return id;
    }

    public String getRequirementName() {
        return requirementName;
    }

    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }

    public String getRequirementDescription() {
        return requirementDescription;
    }

    public void setRequirementDescription(String requirementDescription) {
        this.requirementDescription = requirementDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Requirements that = (Requirements) o;
        return id == that.id && requirementName.equals(that.requirementName)
                && requirementDescription.equals(that.requirementDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, requirementName, requirementDescription);
    }
}
