package nl.tudelft.sem.template.requirements.services;


import nl.tudelft.sem.template.requirements.domain.Report;
import nl.tudelft.sem.template.requirements.domain.Requirements;
import nl.tudelft.sem.template.requirements.repositories.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final transient ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report createReport(String reportedUser, Requirements requirement) throws Exception {
        Report req = new Report(reportedUser, requirement);
        reportRepository.save(req);
        return req;
    }

    public List<Report> getAll() {
        return reportRepository.findAll();
    }

    public List<Report> getReportsByHoa(int hoaId){
        return getAll().stream()
                .filter(o -> o.getRequirement().getHoaId() == hoaId)
                .collect(Collectors.toList());
    }

    public Report find(int id) {
        return reportRepository.findById(id);
    }
}
