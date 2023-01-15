package nl.tudelft.sem.template.requirements.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import nl.tudelft.sem.template.commons.models.DeleteRequirementModel;
import nl.tudelft.sem.template.commons.models.UpdateRequirementModel;
import nl.tudelft.sem.template.commons.models.hoa.FullHoaResponseModel;
import nl.tudelft.sem.template.commons.models.hoa.HoaLessUserHoaModel;
import nl.tudelft.sem.template.commons.models.notification.NotificationChangeReq;
import nl.tudelft.sem.template.commons.models.notification.NotificationCreateReq;
import nl.tudelft.sem.template.commons.models.notification.NotificationDeleteReq;
import nl.tudelft.sem.template.requirements.services.RequirementsService;
import nl.tudelft.sem.template.requirements.domain.Requirements;
import nl.tudelft.sem.template.commons.models.CreateRequirementModel;
import nl.tudelft.sem.template.requirements.models.RequirementsResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/requirements")
public class RequirementsController {
    private final transient RequirementsService requirementsService;
    private final transient String processUrl = "http://localhost:8081/notification/processNotification/";

    /**
     * Instantiates a new controller.
     *
     * @param requirementsService used for communication with requirements service
     */
    @Autowired
    public RequirementsController(RequirementsService requirementsService) {
        this.requirementsService = requirementsService;
    }

    /**
     * Get a list of hoa members (will be used for remembering which users will receive the notification)
     * @param hoaId id of the hoa
     * @return a list of usernames
     */
    public List<String> getHoaMembers(int hoaId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = new HttpEntity(null, null);
        String url = "http://localhost:8090/hoa/getHoaModel/" + hoaId;

        FullHoaResponseModel hoa = restTemplate.exchange(url, HttpMethod.GET, entity, FullHoaResponseModel.class).getBody();
        if (hoa != null) {
            if (hoa.getMembers().size() == 0) return null;
            List<String> usernames = new ArrayList<>();
            for (HoaLessUserHoaModel usr: hoa.getMembers()) {
                usernames.add(usr.getUser().getDisplayName());
            }
            return usernames;
        } else {
            return null;
        }
    }

    /**
     * Method used to send a new requirement created notification to the gateway
     * @param req the new requirement
     * @param hoaId the id of the HOA
     */
    public void createRequirementNotification(Requirements req, int hoaId) throws JsonProcessingException {
        List<String> usernames = getHoaMembers(hoaId);

        if (usernames != null) {
            NotificationCreateReq body = new NotificationCreateReq(usernames,
                    req.getRequirementName(),
                    req.getRequirementDescription());

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity entity = new HttpEntity(Util.serialize(body), null);
            restTemplate.exchange(processUrl, HttpMethod.POST, entity, String.class);
        }
    }
    /**
     * Method used to send a new changed requirement notification to the gateway
     * @param req the new and old details of the requirement
     * @param hoaId the id of the HOA
     */
    public void changeRequirementNotification(Requirements req, int hoaId, String newName, String newDescription)
            throws JsonProcessingException {
        List<String> usernames = getHoaMembers(hoaId);

        if (usernames != null) {
            NotificationChangeReq body = new NotificationChangeReq(usernames,
                    req.getRequirementName(),
                    req.getRequirementDescription(),
                    newName,
                    newDescription);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity entity = new HttpEntity(Util.serialize(body), null);
            restTemplate.exchange(processUrl, HttpMethod.POST, entity, String.class);
        }
    }

    /**
     * Method used to send a 'delete requirement' notification to the gateway
     * @param req the details of the requirement
     * @param hoaId the id of the HOA
     */
    public void deleteRequirementNotification(Requirements req, int hoaId)
            throws JsonProcessingException {
        List<String> usernames = getHoaMembers(hoaId);

        if (usernames != null) {
            NotificationDeleteReq body = new NotificationDeleteReq(usernames,
                    req.getRequirementName(),
                    req.getRequirementDescription());

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity entity = new HttpEntity(Util.serialize(body), null);
            restTemplate.exchange(processUrl, HttpMethod.POST, entity, String.class);
        }
    }

    /**
     * Creates a new requirement for the HOA members
     * @param request Name and description of the requirement
     * @return OK status -> requirement created
     * @throws Exception bad request if the body (CreateRequirementModel) is invalid
     */
    @PostMapping("/createRequirement")
    public ResponseEntity createRequirement(@RequestBody CreateRequirementModel request) throws Exception {

        try {
            int hoaId = request.getHoaId();
            if (hoaId != -1) {
                if (Util.hoaExists(hoaId)) {
                    String name = request.getName();
                    String description = request.getDescription();
                    Requirements req = requirementsService.createRequirement(hoaId, name, description);
                    createRequirementNotification(req, hoaId);
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
            } else {
                requirementsService.createRequirement(hoaId, request.getName(), request.getDescription());
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    /**
     * Changes an existing requirement for the HOA members
     * @param request Name and description of the requirement
     * @return OK status -> requirement modified
     * @throws Exception bad request if the body (UpdateRequirementModel) is invalid
     */
    @PostMapping("/changeRequirement")
    public ResponseEntity changeRequirement(@RequestBody UpdateRequirementModel request) throws Exception {
        try {
            Requirements requirement = requirementsService.findById(request.getRequirementId());
            if (requirement != null) {
                changeRequirementNotification(requirement, requirement.getHoaId(), request.getNewName(),
                        request.getNewDescription());
                requirementsService.updateRequirement(requirement, request.getNewName(), request.getNewDescription());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Requirement not found.");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes an existing requirement if it's not needed anymore for the HOA members
     * @param request Name and description of the requirement
     * @return OK status -> requirement deleted
     * @throws Exception bad request if the body (DeleteRequirementModel) is invalid
     */
    @PostMapping("/deleteRequirement")
    public ResponseEntity deleteRequirement(@RequestBody DeleteRequirementModel request) throws Exception {
        try {
            Requirements requirement = requirementsService.findById(request.getRequirementId());
            if (requirement != null) {
                deleteRequirementNotification(requirement, requirement.getHoaId());
                requirementsService.deleteRequirement(requirement);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Requirement not found.");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Returns a list of all requirements
     * @param hoaId the ID of the hoa to get the list of requirements for.
     * @return a list containing all the requirements for an hoa
     * @throws Exception bad request if the hoa doesn't exist/invalid hoaId
     */
    @GetMapping("/getRequirements/{hoaId}")
    public ResponseEntity<RequirementsResponseModel> getRequirements(@PathVariable("hoaId") int hoaId)
            throws Exception {
        try {
            if (hoaId != -1) {
                if (Util.hoaExists(hoaId)) {
                    List<Requirements> requirementsList = requirementsService.getAll()
                            .stream().filter(o -> o.getHoaId() == hoaId)
                            .collect(Collectors.toList());
                    return ResponseEntity.ok(new RequirementsResponseModel(requirementsList));
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
            } else {
                List<Requirements> requirementsList = requirementsService.getAll()
                        .stream().filter(o -> o.getHoaId() == hoaId)
                        .collect(Collectors.toList());
                return ResponseEntity.ok(new RequirementsResponseModel(requirementsList));
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
