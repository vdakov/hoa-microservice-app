package nl.tudelft.sem.template.commons.entities.notification;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ReportEvent extends Event {
    private static final long serialVersionUID = 2839418385L;
    private final String notificationDescription = "You have been reported to your HOA. Check below for reason";
    private String brokenRequirementName;
    private String brokenRequirementDescription;
}
