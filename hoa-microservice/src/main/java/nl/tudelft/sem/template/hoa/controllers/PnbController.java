package nl.tudelft.sem.template.hoa.controllers;

import nl.tudelft.sem.template.hoa.entitites.Activity;
import nl.tudelft.sem.template.hoa.services.ActivityService;
import nl.tudelft.sem.template.commons.models.ActivityModel;

import nl.tudelft.sem.template.hoa.services.CreateActivityParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Hello World example controller.
 * <p>
 * This controller shows how you can extract information from the JWT token.
 * </p>
 */
@RestController
@RequestMapping("/pnb")
public class PnbController {

    private final transient ActivityService activityService;

    /**
     * Instantiates a new controller.
     *
     */
    @Autowired
    public PnbController(ActivityService activityService) {
        this.activityService = activityService;
    }

    /**
     * Simple method for testing whether the controller is reachable
     *
     * @return a response to an imaginary Mario
     */
    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello Mario");

    }

    /**
     * Creates an activity based on the request and saves it.
     *
     * @param request the request with the parameters of the activity
     * @return an empty "OK" response entity
     */
    @PostMapping("/createActivity")
    public ResponseEntity<ActivityModel> createActivity(@RequestBody ActivityModel request) {
        try {
            ActivityModel activityModel =
                    activityService.createActivity(new CreateActivityParameters(
                            request.getHoaId(),
                            request.getName(),
                            request.getTime(),
                            request.getDescription()
                    )).toModel();
            return ResponseEntity.ok(activityModel);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private List<ActivityModel> activitiesToModels(List<Activity> list){
        return list.stream()
                .map((x) -> (x.toModel()))
                .collect(Collectors.toList());
    }

    /**
     * Method for retrieving all activities in the repo. Only for testing purposes.
     * @return a response entity with a list of activities.
     */
    @GetMapping("/allActivities")
    public ResponseEntity<List<ActivityModel>> getAllActivities() {
        return ResponseEntity.ok(activitiesToModels(activityService.getAllActivities()));
    }

    /**
     * Method for retrieving all activities a user has access to.
     * @param username the username (NOTE: according to the database on THIS microservice).
     * @return a response entity with a list of activities.
     */
    @GetMapping("/allActivitiesForUser/{username}")
    public ResponseEntity<List<ActivityModel>> getActivitiesForUser(@PathVariable String username) {
        return ResponseEntity.ok(activitiesToModels(activityService.getAllActivitiesForUsername(username)));
    }

    /**
     * Responds with a list of all activities belonging to the given HOA.
     *
     * @param hoaId the ID of the HOA
     * @return a response entity containing the list of relevant activities
     */
    @GetMapping("/activitiesForHoa/{hoaId}")
    public ResponseEntity<List<ActivityModel>> getActivitiesForHoa(@PathVariable int hoaId) {
        try {
            return ResponseEntity.ok(activitiesToModels(activityService.getActivitiesByHoaId(hoaId)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
