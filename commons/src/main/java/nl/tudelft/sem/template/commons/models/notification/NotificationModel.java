package nl.tudelft.sem.template.commons.models.notification;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.commons.entities.notification.Event;

import java.util.List;

@Data
@NoArgsConstructor
public class NotificationModel {
    private List<Event> notificationList;

    public NotificationModel(List<Event> notificationList) {
        this.notificationList = notificationList;
    }
}
