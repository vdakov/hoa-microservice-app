package nl.tudelft.sem.template.commons.models.notification;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Base model used to create new notifications. Only creates a list of users
 * Always extended by other classes
 */

@NoArgsConstructor
public class NewNotification {
    @JsonProperty("usernames")
    private List<String> usernames;
    @JsonProperty("notificationType")
    private NotificationType notificationType;

    public NewNotification(List<String> usernames, NotificationType notificationType) {
        this.usernames = usernames;
        this.notificationType = notificationType;
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }
}
