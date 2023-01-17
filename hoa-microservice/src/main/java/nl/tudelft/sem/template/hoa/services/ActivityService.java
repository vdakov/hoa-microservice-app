package nl.tudelft.sem.template.hoa.services;

import nl.tudelft.sem.template.hoa.entitites.Activity;
import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.hoa.exceptions.ActivityNameAlreadyInUseException;
import nl.tudelft.sem.template.commons.models.DateModel;
import nl.tudelft.sem.template.hoa.repositories.ActivityRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ActivityService {
    private final transient ActivityRepository activityRepository;

    private transient ServiceParameterClass services;

    /**
     * Creates a new activity service with the given repository.
     *
     * @param activityRepository the repository
     */
    public ActivityService(ActivityRepository activityRepository, HoaService hoaService) {
        this.activityRepository = activityRepository;
        this.services = new ServiceParameterClass(hoaService);
    }

    /**
     * Saves an activity with the given parameters into the repository.
     *
     * @param p the parameters for this activity
     * @return the activity that has been created
     * @throws Exception if an activity with the given name already exists
     */
    public Activity createActivity(CreateActivityParameters p) throws Exception {
        if (existsByNameAndTime(p.getName(), p.getTime())) throw new ActivityNameAlreadyInUseException(p.getName());
        Hoa hoa = this.services.getHoaService().getHoaById(hoaId);
        Activity activity = new Activity(hoa, p.getName(), p.getTime(), p.getDescription());
        activityRepository.save(activity);
        return activity;
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
     * Returns all activities that a certain username is associated with.
     *
     * @return a list of activities
     */
    public List<Activity> getAllActivitiesForUsername(String username) {
        return activityRepository.findActivitiesForUser(username);
    }

    /**
     * Checks whether an activity with the given name and time already exists in the repository.
     *
     * @param name the name in question
     * @param time the time in question
     * @return whether it is unique
     */
    public boolean existsByNameAndTime(String name, DateModel time) {
        return activityRepository.existsByNameAndTime(name, time);
    }

    /**
     * Returns a list of activities with the given HOA ID.
     *
     * @param hoaId the HOA ID
     * @return the list of activities that belong to the given HOA
     */
    public List<Activity> getActivitiesByHoaId(int hoaId) {
        if (!this.services.getHoaService().existsById(hoaId)) throw new NoSuchElementException();
        return activityRepository.findAllByHoaId(hoaId);
    }
}
