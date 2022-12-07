package nl.tudelft.sem.template.pnb.application.activity;

import nl.tudelft.sem.template.pnb.domain.activity.ActivityWasCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public class ActivityWasCreatedListener {

    public void onActivityWasCreated(ActivityWasCreatedEvent event) {
        System.out.println("Activity with name \'" + event.getName() + "\' was created.");
    }
}
