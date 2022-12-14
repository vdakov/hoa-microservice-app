package nl.tudelft.sem.template.hoa.services;

import nl.tudelft.sem.template.hoa.domain.activity.Activity;
import nl.tudelft.sem.template.hoa.domain.activity.ActivityService;
import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.hoa.models.DateModel;
import nl.tudelft.sem.template.hoa.repositories.ActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ActivityServiceTest {

    private ActivityRepository activityRepository;
    private HoaService hoaService;
    private ActivityService service;

    @BeforeEach
    public void setup() {
        activityRepository = mock(ActivityRepository.class);
        hoaService = mock(HoaService.class);
        service = new ActivityService(activityRepository, hoaService);
    }


    @Test
    public void testCreateActivity() throws Exception {
        Hoa hoa = mock(Hoa.class);
        when(hoaService.getHoaById(1)).thenReturn(hoa);

        Activity activity = service.createActivity(1, "Test", new DateModel(2022, 12, 14), "A test activity");

        verify(activityRepository).save(activity);
    }

    @Test
    public void testExistsByName() {
        when(activityRepository.existsByName("Test")).thenReturn(true);
        assertTrue(service.existsByName("Test"));
        verify(activityRepository, times(1) ).existsByName("Test");

        when(activityRepository.existsByName("Tests")).thenReturn(false);
        assertTrue(!service.existsByName("Tests"));
        verify(activityRepository, times(1) ).existsByName("Tests");
    }

    @Test
    public void testGetActivitiesByHoaId() {
        Hoa hoa1 = mock(Hoa.class);
        when(hoa1.getId()).thenReturn(1);
        Hoa hoa2 = mock(Hoa.class);
        when(hoa2.getId()).thenReturn(2);
        when(hoaService.existsById(1)).thenReturn(true);
        when(hoaService.existsById(2)).thenReturn(true);

        List<Activity> activities1 = Arrays.asList(mock(Activity.class), mock(Activity.class), mock(Activity.class));
        List<Activity> activities2 = Arrays.asList(mock(Activity.class), mock(Activity.class));
        when(activityRepository.findAllByHoaId(1)).thenReturn(activities1);
        when(activityRepository.findAllByHoaId(2)).thenReturn(activities2);

        List<Activity> result1 = service.getActivitiesByHoaId(1);
        assertEquals(activities1, result1);

        List<Activity> result2 = service.getActivitiesByHoaId(2);
        assertEquals(activities2, result2);
    }



}
