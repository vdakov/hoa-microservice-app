package nl.tudelft.sem.template.commons.models.notification;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class NotificationCreateReq extends NewNotification {
    @JsonProperty("field_name")
    private String requirementName;
    private String requirementDescription;

    public NotificationCreateReq(List<String> users, String requirementName, String requirementDescription) {
        super(users, NotificationType.CREATE_REQUIREMENT);
        this.requirementName = requirementName;
        this.requirementDescription = requirementDescription;
    }
}
