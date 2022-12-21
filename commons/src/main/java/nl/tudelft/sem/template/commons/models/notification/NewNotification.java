package nl.tudelft.sem.template.commons.models.notification;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Base model used to create new notifications. Only creates a list of users
 * Always extended by other classes
 */
@Data
@NoArgsConstructor
public class NewNotification {
    private List<String> usernames;
    private NotificationType notificationType;

    public NewNotification(List<String> usernames, NotificationType notificationType) {
        this.usernames = usernames;
        this.notificationType = notificationType;
    }
}
