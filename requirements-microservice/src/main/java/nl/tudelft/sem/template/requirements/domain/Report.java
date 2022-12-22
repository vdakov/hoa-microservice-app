package nl.tudelft.sem.template.requirements.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.commons.entities.HasEvents;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

@Data
@Entity
@Table(name = "report")
@NoArgsConstructor
public class Report extends HasEvents {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "reportedUser", nullable = false)
    private String reportedUser;

    @OneToOne()
    private Requirements requirement;

    public Report(String reportedUser, Requirements requirement) {
        this.reportedUser = reportedUser;
        this.requirement = requirement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return id == report.id && reportedUser.equals(report.reportedUser) && requirement.equals(report.requirement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reportedUser, requirement);
    }
}
