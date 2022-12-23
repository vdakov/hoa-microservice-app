package nl.tudelft.sem.template.commons.entities.notification;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * A new requirement notification
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CreateRequirementEvent extends Event{
    private static final long serialVersionUID = 2839418385L;
    private final String notificationDescription = "A new requirement has been added to your HOA. Check below for details";
    private String requirementName;
    private String requirementDescription;
}
