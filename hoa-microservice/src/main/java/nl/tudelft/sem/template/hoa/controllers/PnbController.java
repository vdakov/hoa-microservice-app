package nl.tudelft.sem.template.hoa.controllers;

import nl.tudelft.sem.template.hoa.domain.activity.Activity;
import nl.tudelft.sem.template.hoa.domain.activity.ActivityService;
import nl.tudelft.sem.template.hoa.models.ActivityRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
     * Gets example by id.
     *
     * @return the example found in the database with the given id
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
     * @throws Exception if an activity with the given name already exists
     */
    @PostMapping("/createActivity")
    public ResponseEntity createActivity(@RequestBody ActivityRequestModel request) throws Exception {
        try {
            int year = request.getTime().getYear();
            int month = request.getTime().getMonth();
            int day = request.getTime().getDay();
            Date time = new GregorianCalendar(year, month, day).getTime();
            activityService.createActivity(request.getName(), time, request.getDescription());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return ResponseEntity.ok().build();
    }


    @GetMapping("/allActivities")
    public ResponseEntity<List<Activity>> getAllActivities() {
        return ResponseEntity.ok(activityService.getAllActivities());
    }


}
