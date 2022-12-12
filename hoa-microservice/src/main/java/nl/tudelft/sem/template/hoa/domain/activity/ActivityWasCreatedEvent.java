package nl.tudelft.sem.template.hoa.domain.activity;

/**
 * A DDD domain event that indicated an Activity was created.
 */
public class ActivityWasCreatedEvent {
    private final String name;

    public ActivityWasCreatedEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
