package nl.tudelft.sem.template.pnb.domain.activity;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.pnb.domain.HasEvents;

@Entity
@Table(name = "activities")
@NoArgsConstructor
public class Activity extends HasEvents {

    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "time", nullable = false)
    private Date time;

    @Column(name = "description", nullable = false)
    private String description;

    /**
     * Creates new Activity.
     */
    public Activity(String name, Date time, String description) {
        this.name = name;
        this.time = time;
        this.description = description;

        this.recordThat(new ActivityWasCreatedEvent(name));
    }

    public void changeTime(Date time) {
        this.time = time;
        this.recordThat(new ActivityTimeWasChangedEvent(this));
    }

    public void changeDescription(String description) {
        this.description = description;
        this.recordThat(new ActivityTimeWasChangedEvent(this));
    }

    public String getName() {
        return name;
    }

    public Date getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Activity activity = (Activity) o;
        return name.equals(activity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
