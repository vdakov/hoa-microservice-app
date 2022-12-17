package nl.tudelft.sem.template.hoa.entities;

import nl.tudelft.sem.template.hoa.domain.activity.Activity;
import nl.tudelft.sem.template.hoa.entitites.Hoa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ActivityTest {
    private Activity activity;
    private static final Hoa hoa = new Hoa("h1", "USA", "Cincinnati");
    private static final String name = "Activity Name";
    private static final GregorianCalendar time = new GregorianCalendar(2022, 12, 17);
    private static final String description = "Activity Description";

    @BeforeEach
    public void setup() {
        activity = new Activity(hoa, name, time, description);
    }

    @Test
    public void testChangeTime() {
        GregorianCalendar newTime = new GregorianCalendar(2002, 10, 24);
        activity.changeTime(newTime);
        assertEquals(newTime, activity.getTime());
    }

    @Test
    public void testChangeDescription() {
        String newDescription = "New Description";
        activity.changeDescription(newDescription);
        assertEquals(newDescription, activity.getDescription());
    }

    @Test
    public void testGetName() {
        assertEquals(name, activity.getName());
    }

    @Test
    public void testGetTime(){
        assertEquals(time, activity.getTime());
    }

    @Test
    public void testGetDescription(){
        assertEquals(description, activity.getDescription());
    }

    @Test
    public void testGetHoa(){
        assertEquals(hoa, activity.getHoa());
    }

    @Test
    public void testEqualsSameNameAndTime() {
        Activity activity2 = new Activity(hoa, name, time, "Different Description");
        assertEquals(activity, activity2);
        assertEquals(activity.hashCode(), activity2.hashCode());
    }

    @Test
    public void testEqualsDifferentName() {
        Activity activity2 = new Activity(hoa, "Different Name", time, description);
        assertNotEquals(activity, activity2);
        assertNotEquals(activity.hashCode(), activity2.hashCode());
    }

    @Test
    public void testEqualsDifferentTime() {
        Activity activity2 = new Activity(hoa, name, new GregorianCalendar(2022, 10, 24), description);
        assertNotEquals(activity, activity2);
        assertNotEquals(activity.hashCode(), activity2.hashCode());
    }

    @Test
    public void testEqualsNull() {
        assertNotEquals(activity, null);
    }

    @Test
    public void testEqualsDifferentClass() {
        assertNotEquals(activity, "Activity");
    }

    @Test
    public void testEqualsSelf(){
        assertEquals(activity, activity);
    }


}
