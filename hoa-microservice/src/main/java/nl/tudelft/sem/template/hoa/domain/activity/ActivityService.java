package nl.tudelft.sem.template.hoa.domain.activity;

import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.hoa.models.DateModel;
import nl.tudelft.sem.template.hoa.repositories.ActivityRepository;
import nl.tudelft.sem.template.hoa.services.HoaService;

import org.springframework.stereotype.Service;

import java.util.GregorianCalendar;
import java.util.List;

@Service
public class ActivityService {
    private final transient ActivityRepository activityRepository;
    private final transient HoaService hoaService;

    /**
     * Creates a new activity service with the given repository.
     *
     * @param activityRepository the repository
     */
    public ActivityService(ActivityRepository activityRepository, HoaService hoaService) {
        this.activityRepository = activityRepository;
        this.hoaService = hoaService;
    }

    /**
     * Saves an activity with the given parameters into the repository.
     *
     * @param name the name of the activity
     * @param dateModel the date of the activity
     * @param description the description of the activity
     * @return the activity that has been created
     * @throws Exception if an activity with the given name already exists
     */
    public Activity createActivity(int hoaId, String name, DateModel dateModel, String description) throws Exception {

        if (checkNameIsUnique(name)) {
            Hoa hoa = hoaService.getHoaById(hoaId);

            int year = dateModel.getYear();
            int month = dateModel.getMonth();
            int day = dateModel.getDay();
            GregorianCalendar time = new GregorianCalendar(year, month, day);

            Activity activity = new Activity(hoa, name, time, description);
            activityRepository.save(activity);
            return activity;
        }
        throw new ActivityNameAlreadyInUseException(name);
    }

    /**
     * Returns all activities in the repository.
     *
     * @return a list of all activities in the repository
     */
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    /**
     * Checks whether an activity with the given name already exists in the repository.
     *
     * @param name the name in question
     * @return whether it is unique
     */
    public boolean checkNameIsUnique(String name) {
        return !activityRepository.existsByName(name);
    }

    /**
     * Returns a list of activities with the given HOA ID.
     *
     * @param hoaId the HOA ID
     * @return the list of activities that belong to the given HOA
     */
    public List<Activity> getActivitiesByHoaId(int hoaId) {
        return activityRepository.findAllByHoaId(hoaId);
    }
}
