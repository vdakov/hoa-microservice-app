package nl.tudelft.sem.template.commons.models;

import lombok.Data;

@Data
public class CreateReportModel {
    private String reportedUser;
    private int brokenRequirementId;
}
