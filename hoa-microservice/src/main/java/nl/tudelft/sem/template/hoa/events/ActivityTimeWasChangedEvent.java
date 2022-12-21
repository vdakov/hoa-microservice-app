package nl.tudelft.sem.template.hoa.events;

import nl.tudelft.sem.template.hoa.entitites.Activity;

/**
 * A DDD domain event indicating a password had changed.
 */
public class ActivityTimeWasChangedEvent {
    private final Activity activity;

    public ActivityTimeWasChangedEvent(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return this.activity;
    }
}
