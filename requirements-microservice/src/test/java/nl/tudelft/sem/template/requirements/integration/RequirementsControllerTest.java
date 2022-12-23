package nl.tudelft.sem.template.requirements.integration;

import nl.tudelft.sem.template.commons.models.CreateReportModel;
import nl.tudelft.sem.template.commons.models.CreateRequirementModel;
import nl.tudelft.sem.template.requirements.domain.Report;
import nl.tudelft.sem.template.requirements.domain.Requirements;
import nl.tudelft.sem.template.requirements.integration.utils.JsonUtil;
import nl.tudelft.sem.template.requirements.repositories.ReportRepository;
import nl.tudelft.sem.template.requirements.repositories.RequirementsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles({"test"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class RequirementsControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RequirementsRepository requirementsRepository;

    @Autowired
    private ReportRepository reportRepository;

    Requirements requirement1;
    Requirements requirement2;
    Report report1;

    @BeforeEach
    void setup() {
        requirement1 = new Requirements(1, "First requirement", "Some details");
        requirement2 = new Requirements(2, "Second requirement", "More details");
        report1 = new Report("sem2", requirement1);
    }


    public CreateReportModel model(Report report){
        return new CreateReportModel(report.getReportedUser(), report.getRequirement().getId());
    }

    public CreateRequirementModel model(Requirements requirement) {
        return new CreateRequirementModel(requirement.getRequirementName(),
                requirement.getRequirementDescription(),
                requirement.getHoaId());
    }

    @Test
    void hello() throws Exception {
        mockMvc.perform(get("/requirements/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"));
    }

    @Test
    public void testCreateRequirement() throws Exception{
        requirement1.setHoaId(-1);
        mockMvc.perform(post("/requirements/createRequirement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.serialize(model(requirement1))))
                .andExpect(status().isOk());

        Requirements req = requirementsRepository.findAll().get(0);
        assertThat(req.getRequirementName()).isEqualTo("First requirement");
        assertThat(req.getRequirementDescription()).isEqualTo("Some details");
    }

    @Test
    public void testCreateRequirementFail() throws Exception{
        requirement1.setHoaId(-20);
        mockMvc.perform(post("/requirements/createRequirement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.serialize(model(requirement1))))
                        .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateReport() throws Exception {
        requirementsRepository.save(requirement1);

        requirement1.setId(-1);

        mockMvc.perform(post("/requirements/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.serialize(model(report1))))
                .andExpect(status().isOk());
        requirement1.setId(1);
        Report report = reportRepository.findAll().get(0);
        assertThat(report.getReportedUser()).isEqualTo("sem2");
        assertThat(report.getRequirement()).isEqualTo(requirement1);
    }

    @Test
    public void testCreateReportFail() throws Exception {
        requirementsRepository.save(requirement1);
        requirement2.setId(100);
        report1.setRequirement(requirement2);
        mockMvc.perform(post("/requirements/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.serialize(model(report1))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getRequirementsTest() throws Exception {
        requirement1.setHoaId(-1);
        mockMvc.perform(post("/requirements/createRequirement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.serialize(model(requirement1))))
                .andExpect(status().isOk());

        requirement2.setHoaId(100);
        mockMvc.perform(post("/requirements/createRequirement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.serialize(model(requirement2))))
                .andExpect(status().isBadRequest());

        MvcResult result = mockMvc.perform(get("/requirements/getRequirements/-1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        //TODO: use deserializer
        assertThat(result.getResponse().getContentAsString()).contains("First requirement");
        assertThat(result.getResponse().getContentAsString()).doesNotContain("Second requirement");
    }

    @Test
    public void getReportsTest() throws Exception {
        requirement1.setHoaId(-1);
        requirementsRepository.save(requirement1);
        requirement1.setId(-1);
        mockMvc.perform(post("/requirements/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.serialize(model(report1))))
                .andExpect(status().isOk());
        requirement1.setId(1);
        MvcResult result = mockMvc.perform(get("/requirements/getReports/-1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("First requirement");
    }
}
