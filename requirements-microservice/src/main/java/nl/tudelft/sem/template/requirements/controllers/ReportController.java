package nl.tudelft.sem.template.requirements.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import nl.tudelft.sem.template.commons.models.CreateReportModel;
import nl.tudelft.sem.template.commons.models.notification.NotificationReport;
import nl.tudelft.sem.template.requirements.domain.Report;
import nl.tudelft.sem.template.requirements.domain.Requirements;
import nl.tudelft.sem.template.requirements.models.ReportResponseModel;
import nl.tudelft.sem.template.requirements.services.ReportService;
import nl.tudelft.sem.template.requirements.services.RequirementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/report")
public class ReportController {
    private final transient ReportService reportService;

    private final transient RequirementsService requirementsService;

    /**
     * Instantiates a new controller.
     *
     * @param reportService used for communication with reports service
     */
    @Autowired
    public ReportController(ReportService reportService,
                            RequirementsService requirementsService) {
        this.reportService = reportService;
        this.requirementsService = requirementsService;
    }

    /**
     * Method used to send a report notification to the gateway
     * @param req broken requirement
     * @param user username
     */
    public void reportNotification(Requirements req, String user) throws JsonProcessingException {
        List<String> username = new ArrayList<>();
        username.add(user);
        NotificationReport report = new NotificationReport(username,
                req.getRequirementName(),
                req.getRequirementDescription());

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = new HttpEntity(Util.serialize(report), null);
        String processUrl = "http://localhost:8081/notification/processNotification/";
        restTemplate.exchange(processUrl, HttpMethod.POST, entity, String.class);
    }

    /**
     * Report a member of the HOA for breaking a requirement
     * @param request reportedUser - user that broke the requirement
     *                brokenRequirementId - ID of the requirement that was broken
     * @return
     * @throws Exception
     */
    @PostMapping("/submit")
    public ResponseEntity report(@RequestBody CreateReportModel request) throws Exception {
        try {
            int brokenRequirementId = request.getBrokenRequirementId();
            if (brokenRequirementId != -1) {
                Requirements requirement = requirementsService.findById(brokenRequirementId);
                if (requirement != null) {
                    String reportedUser = request.getReportedUser();
                    reportService.createReport(reportedUser, requirement);
                    reportNotification(requirement, reportedUser);
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Requirement does not exist");
                }
            } else {
                reportService.createReport(request.getReportedUser(), requirementsService.findById(-brokenRequirementId));
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    /**
     * Returns a list of all reports
     * @param hoaId the ID of the hoa to get the lsit of reports for.
     * @return
     * @throws Exception
     */
    @GetMapping("/getReports/{hoaId}")
    public ResponseEntity<ReportResponseModel> getReports(@PathVariable("hoaId") int hoaId) {
        try {
            if (hoaId != -1) {
                if (Util.hoaExists(hoaId)) {
                    return ResponseEntity.ok(new ReportResponseModel(reportService.getReportsByHoa(hoaId)));
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
            }
            return ResponseEntity.ok(new ReportResponseModel(reportService.getReportsByHoa(hoaId)));

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
