package nl.tudelft.sem.template.requirements.services;

import nl.tudelft.sem.template.requirements.domain.Report;
import nl.tudelft.sem.template.requirements.domain.Requirements;
import nl.tudelft.sem.template.requirements.repositories.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReportServiceTest {

    private ReportRepository reportRepository;

    private ReportService reportService;

    private Requirements requirement1;

    @BeforeEach
    public void setup() {
        reportRepository = mock(ReportRepository.class);
        reportService = new ReportService(reportRepository);
        requirement1 = mock(Requirements.class);
    }


    @Test
    public void creatorTest() throws Exception {
        Report report = reportService.createReport("sem2", requirement1);
        verify(reportRepository, times(1)).save(report);
    }

    @Test
    public void getAllTest() throws Exception {
        Report report = reportService.createReport("sem1", requirement1);
        Report report2 = reportService.createReport("sem2", requirement1);
        Report report3 = reportService.createReport("sem3", requirement1);
        List<Report> reportList = Arrays.asList(report, report2, report3);

        when(reportService.getAll()).thenReturn(reportList);

        assertEquals(reportList, reportService.getAll());
    }

    @Test
    public void findTest() throws Exception {
        Report report2 = reportService.createReport("sem2", requirement1);
        Report report3 = reportService.createReport("sem3", requirement1);

        when(reportService.find(2)).thenReturn(report3);
        when(reportService.find(1)).thenReturn(report2);
        when(reportService.find(0)).thenReturn(null);

        assertEquals(report3, reportService.find(2));
        assertEquals(report2, reportService.find(1));
        assertNull(reportService.find(0));
    }
}
