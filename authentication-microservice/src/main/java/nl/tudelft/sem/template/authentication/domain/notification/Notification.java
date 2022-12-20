package nl.tudelft.sem.template.authentication.domain.notification;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.authentication.domain.user.AppUser;
import nl.tudelft.sem.template.commons.entities.notification.Event;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ElementCollection
    private List<AppUser> users;

    @ElementCollection
    private List<Boolean> markedRead;

    @Column(name = "event", nullable = false)
    private Event event;

    public Notification(Event event) {
        this.users = new ArrayList<>();
        this.markedRead = new ArrayList<>();
        this.event = event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<AppUser> getUsers() {
        return users;
    }

    public List<Boolean> getMarkedRead() {
        return markedRead;
    }

    public void setUsers(List<AppUser> users) {
        this.users = users;
    }

    public void setMarkedRead(List<Boolean> markedRead) {
        this.markedRead = markedRead;
    }

    public Event getEvent() {
        return event;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
