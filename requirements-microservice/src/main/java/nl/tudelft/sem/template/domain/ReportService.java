package nl.tudelft.sem.template.requirements.domain;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    private final transient ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report createReport(String reportBy, String reportedUser, Requirements requirement) throws Exception {
        Report req = new Report(reportBy, reportedUser, requirement);
        return reportRepository.save(req);
    }

    public List<Report> getAll() {
        return reportRepository.findAll();
    }
}
