package nl.tudelft.sem.template.requirements.controllers;

import nl.tudelft.sem.template.requirements.authentication.AuthManager;
import nl.tudelft.sem.template.requirements.domain.Report;
import nl.tudelft.sem.template.requirements.domain.ReportService;
import nl.tudelft.sem.template.requirements.domain.Requirements;
import nl.tudelft.sem.template.requirements.domain.RequirementsService;
import nl.tudelft.sem.template.requirements.models.CreateReportModel;
import nl.tudelft.sem.template.requirements.models.CreateRequirementModel;
import nl.tudelft.sem.template.requirements.models.ReportResponseModel;
import nl.tudelft.sem.template.requirements.models.RequirementsResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * Hello World example controller.
 * <p>
 * This controller shows how you can extract information from the JWT token.
 * </p>
 */
@RestController
public class RequirementsController {

    private final transient AuthManager authManager;
    private final transient RequirementsService requirementsService;
    private final transient ReportService reportService;

    /**
     * Instantiates a new controller.
     *
     * @param authManager Spring Security component used to authenticate and authorize the user
     */
    @Autowired
    public RequirementsController(AuthManager authManager,
                                  RequirementsService requirementsService,
                                  ReportService reportService) {
        this.authManager = authManager;
        this.requirementsService = requirementsService;
        this.reportService = reportService;
    }

    /**
     * Gets example by id.
     *
     * @return the example found in the database with the given id
     */
    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello " + authManager.getNetId());

    }

    /**
     * Creates a new requirement for the HOA members
     * @param request Name and description of the requirement
     * @return
     * @throws Exception
     */
    @PostMapping("/createRequirement")
    public ResponseEntity createRequirement(@RequestBody CreateRequirementModel request) throws Exception {

        try {
            String name = request.getName();
            String description = request.getDescription();
            requirementsService.createRequirement(name, description);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    /**
     * Report a member of the HOA for breaking a requirement
     * @param request reportedUser - user that broke the requirement
     *                brokenRequirementId - ID of the requirement that was broken
     * @return
     * @throws Exception
     */
    @PostMapping("/report")
    public ResponseEntity report(@RequestBody CreateReportModel request) throws Exception {
        try {
            int brokenRequirementId = request.getBrokenRequirementId();
            Optional<Requirements> requirement = requirementsService.get(brokenRequirementId);
            if (requirement.isPresent()) {
                String reportBy = authManager.getNetId();
                String reportedUser = request.getReportedUser();
                reportService.createReport(reportBy, reportedUser, requirement.get());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Requirement does not exist");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    /**
     * Returns a list of all requirements TODO: once HOA is done, change this to return only requirements within the HOA
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/getRequirements")
    public ResponseEntity<RequirementsResponseModel> getRequirements(@RequestBody RequirementsResponseModel request)
            throws Exception {
        List<Requirements> requirementsList = requirementsService.getAll();
        return ResponseEntity.ok(new RequirementsResponseModel(requirementsList));
    }

    /**
     * Returns a list of all reports TODO: once HOA is done, change this to return only reports within the HOA
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/showAllReports")
    public ResponseEntity<ReportResponseModel> getRequirements(@RequestBody ReportResponseModel request)
            throws Exception {
        List<Report> reportList = reportService.getAll();
        return ResponseEntity.ok(new ReportResponseModel(reportList));
    }
}
