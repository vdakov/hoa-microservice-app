package nl.tudelft.sem.template.pnb.domain.activity;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {
    private final transient ActivityRepository activityRepository;

    /**
     * Creates a new activity service with the given repository.
     *
     * @param activityRepository the repository
     */
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    /**
     * Saves an activity with the given parameters into the repository.
     *
     * @param name the name of the activity
     * @param time the date of the activity
     * @param description the description of the activity
     * @return the activity that has been created
     * @throws Exception if an activity with the given name already exists
     */
    public Activity createActivity(String name, Date time, String description) throws Exception {
        if (checkNameIsUnique(name)) {
            Activity activity = new Activity(name, time, description);
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
        return !activityRepository.existsById(name.hashCode());
    }
}
