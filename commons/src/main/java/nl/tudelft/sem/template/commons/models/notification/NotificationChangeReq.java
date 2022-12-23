package nl.tudelft.sem.template.commons.models.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Model used for a change requirement notification
 */

@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(as = NotificationChangeReq.class)
public class NotificationChangeReq extends NewNotification {
    @JsonProperty("oldName")
    private String oldName;
    @JsonProperty("oldDescription")
    private String oldDescription;
    @JsonProperty("newName")
    private String newName;
    @JsonProperty("newDescription")
    private String newDescription;

    public NotificationChangeReq(List<String> users, String oldName, String oldDesc, String newName, String newDesc) {
        super(users, NotificationType.CHANGE_REQUIREMENT);
        this.oldName = oldName;
        this.oldDescription = oldDesc;
        this.newName = newName;
        this.newDescription = newDesc;
    }

    public NotificationChangeReq() {

    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getOldDescription() {
        return oldDescription;
    }

    public void setOldDescription(String oldDescription) {
        this.oldDescription = oldDescription;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getNewDescription() {
        return newDescription;
    }

    public void setNewDescription(String newDescription) {
        this.newDescription = newDescription;
    }
}
