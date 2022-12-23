package nl.tudelft.sem.template.hoa.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import nl.tudelft.sem.template.commons.models.ActivityModel;
import nl.tudelft.sem.template.commons.models.DateModel;
import nl.tudelft.sem.template.commons.models.hoa.JoinRequestModel;
import nl.tudelft.sem.template.hoa.entitites.Activity;
import nl.tudelft.sem.template.hoa.entitites.Hoa;

import nl.tudelft.sem.template.hoa.integration.utils.JsonUtil;
import nl.tudelft.sem.template.hoa.repositories.ActivityRepository;
import nl.tudelft.sem.template.hoa.repositories.HoaRepository;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.web.servlet.MvcResult;

import java.util.GregorianCalendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles({"test"/*, "mockHoaService"*/})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class PnbControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private HoaRepository hoaRepository;

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

    @BeforeEach
    public void setup() throws Exception{
        hoaRepository.save(hoa1);
        hoaRepository.save(hoa2);
    }

    @Test
    public void testHello() throws Exception{
        mockMvc.perform(get("/pnb/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello Mario"));
    }

    @Test
    public void testAddSuccessful() throws Exception{
        DateModel time = new DateModel(2020, 3, 13);
        ActivityModel model = new ActivityModel(1, "a1", time, "president time");

        mockMvc.perform(post("/pnb/createActivity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.serialize(model)))
                .andExpect(status().isOk());

        Activity activity = activityRepository.findByName("a1");
        assertThat(activity.getName()).isEqualTo("a1");
        assertThat(activity.getHoa().getId()).isEqualTo(hoa1.getId());
        assertThat(activity.getTime()).isEqualTo(new GregorianCalendar(2020, 3, 13));
        assertThat(activity.getDescription()).isEqualTo("president time");
    }

    @Test
    public void testGetAll() throws Exception{

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

        MvcResult result = mockMvc.perform(get("/pnb/allActivities"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        TypeReference<List<ActivityModel>> typeReference = new TypeReference<>() {};
        List<ActivityModel> activityModels = JsonUtil.deserialize(result.getResponse().getContentAsString(),
                typeReference);

        assertThat(activityModels).containsExactlyInAnyOrder(activity1.toModel(),
                activity2.toModel(),
                activity3.toModel());
    }

    @Test
    public void testGetByHoaId() throws Exception{

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

        TypeReference<List<ActivityModel>> typeReference = new TypeReference<>() {};

        MvcResult result1 = mockMvc.perform(get("/pnb/activitiesForHoa/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        List<ActivityModel> activityModels1 = JsonUtil.deserialize(result1.getResponse().getContentAsString(),
                typeReference);

        assertThat(activityModels1).containsExactlyInAnyOrder(activity1.toModel(),
                activity2.toModel());


        MvcResult result2 = mockMvc.perform(get("/pnb/activitiesForHoa/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        List<ActivityModel> activityModels2 = JsonUtil.deserialize(result2.getResponse().getContentAsString(),
                typeReference);

        assertThat(activityModels2).containsExactlyInAnyOrder(activity3.toModel());
    }

    @Test
    public void testGetForUsername() throws Exception{

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

        mockMvc.perform(post("/api/users/createNewUser")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("user1"))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/users/createNewUser")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("user2"))
                .andExpect(status().isOk());

        JoinRequestModel join1 = new JoinRequestModel("h1", "user1", "USA", "Cincinnati", "Main street", "12a",
                "5555");
        JoinRequestModel join2 = new JoinRequestModel("h2", "user2", "Italy", "Siena", "Piazza del Campo", "1",
                "53100");

        mockMvc.perform(post("/api/users/joinHoa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.serialize(join1)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/users/joinHoa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.serialize(join2)))
                .andExpect(status().isOk());

        TypeReference<List<ActivityModel>> typeReference = new TypeReference<>() {};

        MvcResult result1 = mockMvc.perform(get("/pnb/allActivitiesForUser/user1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        List<ActivityModel> activityModels1 = JsonUtil.deserialize(result1.getResponse().getContentAsString(),
                typeReference);

        assertThat(activityModels1).containsExactlyInAnyOrder(activity1.toModel(),
                activity2.toModel());


        MvcResult result2 = mockMvc.perform(get("/pnb/allActivitiesForUser/user2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        List<ActivityModel> activityModels2 = JsonUtil.deserialize(result2.getResponse().getContentAsString(),
                typeReference);

        assertThat(activityModels2).containsExactlyInAnyOrder(activity3.toModel());
    }

    @Test
    public void testCreateActivityFail() throws Exception{

        mockMvc.perform(post("/pnb/createActivity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.serialize(activity1.toModel())))
                .andExpect(status().isOk());

        mockMvc.perform(post("/pnb/createActivity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.serialize(activity1.toModel())))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetByHoaIdFail() throws Exception{

        TypeReference<List<ActivityModel>> typeReference = new TypeReference<>() {};

        mockMvc.perform(get("/pnb/activitiesForHoa/3"))
                .andExpect(status().isBadRequest());
    }


}
