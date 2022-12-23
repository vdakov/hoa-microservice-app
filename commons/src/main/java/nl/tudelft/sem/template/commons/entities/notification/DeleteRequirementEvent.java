package nl.tudelft.sem.template.commons.entities.notification;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class DeleteRequirementEvent extends Event {
    private static final long serialVersionUID = 2839418385L;
    private final String notificationDescription = "A requirement has been deleted from your HOA. Check below for details";
    private String requirementName;
    private String requirementDescription;
}
