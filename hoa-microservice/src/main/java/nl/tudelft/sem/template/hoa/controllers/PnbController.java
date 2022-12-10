package nl.tudelft.sem.template.hoa.controllers;

import nl.tudelft.sem.template.hoa.domain.activity.Activity;
import nl.tudelft.sem.template.hoa.domain.activity.ActivityService;
import nl.tudelft.sem.template.hoa.domain.hoa.Hoa;
import nl.tudelft.sem.template.hoa.domain.hoa.HoaService;
import nl.tudelft.sem.template.hoa.models.ActivityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
    private final transient HoaService hoaService;

    /**
     * Instantiates a new controller.
     *
     */
    @Autowired
    public PnbController(ActivityService activityService, HoaService hoaService) {
        this.activityService = activityService;
        this.hoaService = hoaService;
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
    public ResponseEntity createActivity(@RequestBody ActivityModel request) throws Exception {
        try {
            int year = request.getTime().getYear();
            int month = request.getTime().getMonth();
            int day = request.getTime().getDay();
            GregorianCalendar time = new GregorianCalendar(year, month, day);

            Hoa hoa = hoaService.getHoaById(request.getHoaId());
            System.out.println(hoa);

            activityService.createActivity(hoa, request.getName(), time, request.getDescription());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    private List<ActivityModel> activitiesToModels(List<Activity> list){
        return list.stream()
                .map((x) -> (x.toModel()))
                .collect(Collectors.toList());
    }


    @GetMapping("/allActivities")
    public ResponseEntity<List<ActivityModel>> getAllActivities() {
        return ResponseEntity.ok(
                activitiesToModels(
                        activityService.getAllActivities()
                )
        );
    }

    /**
     * Responds with a list of all activities belonging to the given HOA.
     *
     * @param hoaId the ID of the HOA
     * @return a response entity containing the list of relevant activities
     * @throws Exception
     */
    @PostMapping("/activitiesForHoa")
    public ResponseEntity<List<ActivityModel>> getActivitiesForHoa(@RequestBody int hoaId) throws Exception {

        try {
            hoaService.getHoaById(hoaId); //to check if it exists
            return ResponseEntity.ok(
                    activitiesToModels(
                            activityService.getActivitiesByHoaId(hoaId)
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }


}
