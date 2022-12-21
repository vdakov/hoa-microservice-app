package nl.tudelft.sem.template.commons.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateReportModel {
    private String reportBy;
    private String reportedUser;
    private int brokenRequirementId;
}
