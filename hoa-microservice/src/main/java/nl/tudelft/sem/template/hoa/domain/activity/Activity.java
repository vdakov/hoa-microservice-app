package nl.tudelft.sem.template.hoa.domain.activity;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.hoa.domain.HasEvents;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "activities")
@NoArgsConstructor
public class Activity extends HasEvents {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Convert(converter = TimeAttributeConverter.class)
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
