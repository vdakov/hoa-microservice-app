package nl.tudelft.sem.template.commons.models.notification;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.commons.entities.notification.Event;

import java.util.List;

@Data
@NoArgsConstructor
public class NotificationModel {
    private final String description = "Your un-read notifications are below. To delete a notification, go to "
            + "/notification/markRead/id";
    private List<Event> notificationList;

    public NotificationModel(List<Event> notificationList) {
        this.notificationList = notificationList;
    }
}
