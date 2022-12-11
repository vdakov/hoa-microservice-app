package nl.tudelft.sem.template.requirements.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.requirements.domain.Report;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponseModel {
    private List<Report> reportList;
}
