package nl.tudelft.sem.template.requirements.models;

import lombok.Data;

@Data
public class CreateReportModel {
    private String reportBy;
    private String reportedUser;
    private int brokenRequirementId;
}
