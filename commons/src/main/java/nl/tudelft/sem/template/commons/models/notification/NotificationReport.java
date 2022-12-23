package nl.tudelft.sem.template.commons.models.notification;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class NotificationReport extends NewNotification{
    @JsonProperty("brokenRequirementName")
    private String brokenRequirementName;
    @JsonProperty("brokenRequirementDescription")
    private String brokenRequirementDescription;

    public NotificationReport(List<String> users, String brokenRequirementName, String brokenRequirementDescription) {
        super(users, NotificationType.REPORT);
        this.brokenRequirementName = brokenRequirementName;
        this.brokenRequirementDescription = brokenRequirementDescription;
    }

    public NotificationReport() {

    }

    public String getBrokenRequirementName() {
        return brokenRequirementName;
    }

    public void setBrokenRequirementName(String brokenRequirementName) {
        this.brokenRequirementName = brokenRequirementName;
    }

    public String getBrokenRequirementDescription() {
        return brokenRequirementDescription;
    }

    public void setBrokenRequirementDescription(String brokenRequirementDescription) {
        this.brokenRequirementDescription = brokenRequirementDescription;
    }
}
