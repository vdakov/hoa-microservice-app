package nl.tudelft.sem.template.hoa.integration;

import nl.tudelft.sem.template.hoa.Application;
import nl.tudelft.sem.template.hoa.controllers.PnbController;
import nl.tudelft.sem.template.hoa.domain.activity.Activity;
import nl.tudelft.sem.template.hoa.domain.activity.ActivityService;
import nl.tudelft.sem.template.hoa.entitites.Hoa;

import nl.tudelft.sem.template.hoa.integration.utils.JsonUtil;
import nl.tudelft.sem.template.hoa.models.ActivityModel;
import nl.tudelft.sem.template.hoa.models.DateModel;
import nl.tudelft.sem.template.hoa.repositories.ActivityRepository;
import nl.tudelft.sem.template.hoa.services.HoaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles({"test", "mockHoaService"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class PnbControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ActivityRepository activityRepository;

    private static final Hoa hoa1 = new Hoa("h1", "USA", "Cincinnati");

    private static final Hoa hoa2 = new Hoa("h2", "Italy", "Siena");

    private static final Activity activity1 = new Activity(
            hoa1, "a1", new GregorianCalendar(2002, 10, 24), "desc1"
    );
    private static final Activity activity2 = new Activity(
            hoa1, "a2", new GregorianCalendar(1999, 6, 1), "desc2"
    );
    private static final Activity activity3 = new Activity(
            hoa2, "a3", new GregorianCalendar(2020, 3, 21), "desc3"
    );

    private static final List<Activity> activities = List.of(activity1, activity2, activity3);

    @Autowired
    private transient HoaService mockHoaService;

    @Test
    public void testHello() throws Exception{
        mockMvc.perform(get("/pnb/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello Mario"));
    }

    @Test
    public void testAddSuccessful() throws Exception{
        when(mockHoaService.getHoaById(1)).thenReturn(hoa1);
        DateModel time = new DateModel(2020, 3, 13);
        ActivityModel model = new ActivityModel(1, "a1", time, "president time");

        mockMvc.perform(post("/pnb/createActivity")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.serialize(model)))
                .andExpect(status().isOk());

        Activity activity = activityRepository.findByName("a1");
        assertThat(activity.getName()).isEqualTo("a1");
        assertThat(activity.getHoa()).isEqualTo(hoa1);
        assertThat(activity.getTime()).isEqualTo(new GregorianCalendar(2020, 3, 13));
        assertThat(activity.getDescription()).isEqualTo("president time");
    }

    //@Test //blocked by adding HOAs
    public void testGetAll() throws Exception{

        when(mockHoaService.getHoaById(hoa1.getId())).thenReturn(hoa1);
        when(mockHoaService.getHoaById(hoa2.getId())).thenReturn(hoa2);

        mockMvc.perform(post("/pnb/createActivity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.serialize(activity1.toModel())))
                        .andExpect(status().isOk());


        mockMvc.perform(post("/pnb/createActivity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.serialize(activity2.toModel())))
                        .andExpect(status().isOk());


        mockMvc.perform(post("/pnb/createActivity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.serialize(activity3.toModel())))
                .andExpect(status().isOk());

        List<Activity> result = activityRepository.findAll();

        assertThat(result).containsExactlyInAnyOrder(activity1, activity2, activity3);
    }


}
