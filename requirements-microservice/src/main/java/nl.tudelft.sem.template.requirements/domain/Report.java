package nl.tudelft.sem.template.requirements.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

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
public class Report extends HasEvents{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "reportBy", nullable = false)
    private String reportBy;

    @Column(name = "reportedUser", nullable = false)
    private String reportedUser;

    @OneToOne()
    private Requirements requirement;

    public Report(String reportBy, String reportedUser, Requirements requirement) {
        this.reportBy = reportBy;
        this.reportedUser = reportedUser;
        this.requirement = requirement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return id == report.id && reportBy.equals(report.reportBy)
                && reportedUser.equals(report.reportedUser) && requirement.equals(report.requirement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reportBy, reportedUser, requirement);
    }
}
