package nl.tudelft.sem.template.commons.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateReportModel {
    private String reportedUser;
    private int brokenRequirementId;

    public CreateReportModel(String reportedUser, int brokenRequirementId) {
        this.reportedUser = reportedUser;
        this.brokenRequirementId = brokenRequirementId;
    }
}
