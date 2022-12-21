package nl.tudelft.sem.template.commons.entities.notification;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ChangeRequirementEvent extends Event {
    private static final long serialVersionUID = 2839418385L;
    private final String notificationDescription = "An existing requirement has been modified. Check below for details";
    private String oldName;
    private String oldDescription;
    private String newName;
    private String newDescription;
}