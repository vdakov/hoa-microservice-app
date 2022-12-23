package nl.tudelft.sem.template.authentication.domain.notification;

import nl.tudelft.sem.template.commons.entities.notification.CreateRequirementEvent;
import nl.tudelft.sem.template.commons.entities.notification.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class NotificationTest {

    Event event;
    Notification notification;
    List<String> usernames;

    @BeforeEach
    void setup() {
        event = new CreateRequirementEvent();
        ((CreateRequirementEvent) event).setRequirementName("Parking rules");
        ((CreateRequirementEvent) event).setRequirementDescription("Park in front of your house only");
        usernames = new ArrayList<>();
        usernames.add("Frank");
        usernames.add("Ben");
        notification = new Notification(event, usernames);
    }

    @Test
    void getId() {
        assertEquals(0, notification.getId());
    }

    @Test
    void setId() {
        notification.setId(2);
        assertEquals(2, notification.getId());
    }

    @Test
    void getUsers() {
        Set<String> users = new HashSet<>();
        users.add("Frank");
        users.add("Ben");
        assertEquals(users, notification.getUsers().keySet());
        users.remove("Ben");
        assertNotEquals(users, notification.getUsers());
    }

    @Test
    void setUsers() {
        Map<String, Boolean> users = new HashMap<>();
        users.put("Frank", false);
        users.put("Ben", false);
        users.put("Rick", false);
        notification.setUsers(users);
        assertEquals(users, notification.getUsers());
    }

    @Test
    void getEvent() {
        assertEquals(event, notification.getEvent());
    }

    @Test
    void setEvent() {
        ((CreateRequirementEvent) event).setRequirementDescription("Park in front of your neighbour only");
        notification.setEvent(event);
        assertNotEquals("Park in front of your neighbour only",
                ((CreateRequirementEvent) notification.getEvent()).getNotificationDescription());
    }

    @Test
    void equalsTest() {
        assertEquals(notification, notification);
        assertNotEquals(notification, null);
        Notification notification2 = new Notification(event, usernames);
        assertEquals(notification2, notification);
        notification2.setId(2);
        assertNotEquals(notification2, notification);
        Map<String, Boolean> users = new HashMap<>();
        users.put("Frank", false);
        notification2.setUsers(users);
        assertNotEquals(notification2, notification);
        ((CreateRequirementEvent) event).setRequirementDescription("Park in front of your neighbour only");
        notification2.setEvent(event);
        assertNotEquals(notification2, notification);
    }

    @Test
    void hashTest() {
        int hash = notification.hashCode();
        assertEquals(notification.hashCode(), notification.hashCode());
        notification.setId(100);
        assertNotEquals(hash, notification.hashCode());
    }
}