package nl.tudelft.sem.template.commons.models.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(as = NotificationCreateReq.class)
public class NotificationCreateReq extends NewNotification {
    @JsonProperty("requirementName")
    private String requirementName;
    @JsonProperty("requirementDescription")
    private String requirementDescription;

    public NotificationCreateReq(List<String> users, String requirementName, String requirementDescription) {
        super(users, NotificationType.CREATE_REQUIREMENT);
        this.requirementName = requirementName;
        this.requirementDescription = requirementDescription;
    }

    public NotificationCreateReq() {

    }

    public String getRequirementName() {
        return requirementName;
    }

    public String getRequirementDescription() {
        return requirementDescription;
    }

    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }

    public void setRequirementDescription(String requirementDescription) {
        this.requirementDescription = requirementDescription;
    }
}
