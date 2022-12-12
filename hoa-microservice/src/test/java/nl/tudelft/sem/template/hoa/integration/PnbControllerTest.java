package nl.tudelft.sem.template.hoa.integration;

import nl.tudelft.sem.template.hoa.Application;
import nl.tudelft.sem.template.hoa.controllers.PnbController;
import nl.tudelft.sem.template.hoa.domain.activity.Activity;
import nl.tudelft.sem.template.hoa.domain.activity.ActivityService;
import nl.tudelft.sem.template.hoa.entitites.Hoa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles({"test", "mockActivityService"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class PnbControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final Hoa hoa1 = new Hoa("h1", "USA", "Cincinnati");
    private static final Activity activity1 = new Activity(
            hoa1, "a1", new GregorianCalendar(2002, 10, 24), "desc1"
    );
    private static final Activity activity2 = new Activity(
            hoa1, "a2", new GregorianCalendar(1999, 6, 1), "desc2"
    );
    private static final List<Activity> activities = List.of(activity1, activity2);

    @Autowired
    private transient ActivityService mockActivityService;

    /**
     * Sets up the mock activity service.
     */
    //@BeforeEach
    public void setup(){

        when(mockActivityService.getAllActivities()).thenReturn(activities);
        when(mockActivityService.existsByName("h1")).thenReturn(true);
        when(mockActivityService.existsByName("h3")).thenReturn(false);
        when(mockActivityService.getActivitiesByHoaId(1)).thenReturn(activities);
        when(mockActivityService.getActivitiesByHoaId(2)).thenReturn(new ArrayList<>());
    }

    @Test
    public void testHello() throws Exception{
        ResultActions resultActions = mockMvc
                .perform(get("/pnb/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello Mario"));
    }


}
