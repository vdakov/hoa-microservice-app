package nl.tudelft.sem.template.commons.models.notification;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class NotificationDeleteReq extends NewNotification{
    @JsonProperty("requirementName")
    private String requirementName;
    @JsonProperty("requirementDescription")
    private String requirementDescription;

    public NotificationDeleteReq(List<String> users, String requirementName, String requirementDescription) {
        super(users, NotificationType.DELETE_REQUIREMENT);
        this.requirementName = requirementName;
        this.requirementDescription = requirementDescription;
    }

    public NotificationDeleteReq() {

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
