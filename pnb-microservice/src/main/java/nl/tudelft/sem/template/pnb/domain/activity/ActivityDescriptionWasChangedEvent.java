package nl.tudelft.sem.template.pnb.domain.activity;

/**
 * A DDD domain event indicating a password had changed.
 */
public class ActivityDescriptionWasChangedEvent {
    private final Activity activity;

    public ActivityDescriptionWasChangedEvent(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return this.activity;
    }
}
