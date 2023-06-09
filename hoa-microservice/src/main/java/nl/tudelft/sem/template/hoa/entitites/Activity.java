package nl.tudelft.sem.template.hoa.entitites;

import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.hoa.events.ActivityTimeWasChangedEvent;
import nl.tudelft.sem.template.hoa.events.ActivityWasCreatedEvent;
import nl.tudelft.sem.template.hoa.entitites.converters.TimeAttributeConverter;
import nl.tudelft.sem.template.commons.entities.HasEvents;
import nl.tudelft.sem.template.commons.models.ActivityModel;
import nl.tudelft.sem.template.commons.models.DateModel;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "activities")
@NoArgsConstructor
public class Activity extends HasEvents {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "hoaId")
    private Hoa hoa;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Convert(converter = TimeAttributeConverter.class)
    @Column(name = "time", nullable = false)
    private DateModel time;

    @Column(name = "description", nullable = false)
    private String description;

    /**
     * Creates new Activity.
     */
    public Activity(Hoa hoa, String name, DateModel time, String description) {
        this.hoa = hoa;
        this.name = name;
        this.time = time;
        this.description = description;

        this.recordThat(new ActivityWasCreatedEvent(name));
    }

    public void changeTime(DateModel time) {
        this.time = time;
        this.recordThat(new ActivityTimeWasChangedEvent(this));
    }

    public void changeDescription(String description) {
        this.description = description;
        this.recordThat(new ActivityTimeWasChangedEvent(this));
    }

    public Hoa getHoa() {
        return hoa;
    }

    public String getName() {
        return name;
    }

    public DateModel getTime() {
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
        return name.equals(activity.name) && time.equals(activity.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, time);
    }

    public ActivityModel toModel(){
        return new ActivityModel(hoa.getId(), name, time, description);
    }
}
