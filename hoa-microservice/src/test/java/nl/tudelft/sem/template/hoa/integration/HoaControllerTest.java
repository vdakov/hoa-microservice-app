package nl.tudelft.sem.template.hoa.integration;

import nl.tudelft.sem.template.commons.models.hoa.HoaRequestModel;
import nl.tudelft.sem.template.hoa.controllers.HoaController;
import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.hoa.services.HoaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@WebMvcTest(HoaController.class)
public class HoaControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private HoaService hoaService;

    private String name;
    private String country;
    private String city;
    private Hoa hoa;

    private ObjectMapper mapper;


    /**
     * Set up the test by creating a new Hoa instance and an ObjectMapper instance.
     * The Hoa instance is initialized with the name "hoa1", country "USA", and city "Cincinnati".
    */
    @BeforeEach
    public void setup() {
        name = "hoa1";
        country = "USA";
        city = "Cincinnati";
        hoa = new Hoa(name, country, city);

        mapper = new ObjectMapper();
    }

    @Test
    public void createHoaTest() throws Exception {
        given(hoaService.createHoa(any(), any(), any())).willReturn(hoa);

        String expectedJson = mapper.writeValueAsString(hoa);

        mvc.perform(MockMvcRequestBuilders.post("/hoa/createHoa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expectedJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(hoaService, Mockito.times(1)).createHoa(any(), any(), any());
    }

    @Test
    public void getAllHoasTest() throws Exception {
        String name2 = "hoa2";
        String country2 = "Canada";
        String city2 = "Toronto";
        Hoa hoa2 = new Hoa(name2, country2, city2);
        List<Hoa> hoas = Arrays.asList(hoa, hoa2);
        given(hoaService.getAllHoas()).willReturn(hoas);

        String expectedJson = mapper.writeValueAsString(hoas.stream().map(hoa -> {
            return hoa.toFullModel();
        }).collect(Collectors.toList()));

        mvc.perform(MockMvcRequestBuilders.get("/hoa/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedJson));

        Mockito.verify(hoaService, Mockito.times(1)).getAllHoas();
    }

    @Test
    public void testFindHoa_HoaExists_ReturnsTrue() throws Exception {
        given(hoaService.getHoaById(1)).willReturn(new Hoa(1, "HOA 1", "Country 1", "City 1", null, null));

        mvc.perform(MockMvcRequestBuilders.get("/hoa/find/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    public void testFindHoa_HoaDoesNotExist_ReturnsFalse() throws Exception {
        given(hoaService.getHoaById(1)).willReturn(null);

        mvc.perform(MockMvcRequestBuilders.get("/hoa/find/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"));
    }

    @Test
    public void testGetAllHoas_NoHoas_ReturnsEmptyList() throws Exception {
        given(hoaService.getAllHoas()).willReturn(Arrays.asList());

        mvc.perform(MockMvcRequestBuilders.get("/hoa/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    public void testGetAllHoas_OneHoa_ReturnsHoaList() throws Exception {
        List<Hoa> hoas = Arrays.asList(new Hoa(1, "HOA 1", "Country 1", "City 1", new HashSet<>(), new HashSet<>()));
        given(hoaService.getAllHoas()).willReturn(hoas);

        mvc.perform(MockMvcRequestBuilders.get("/hoa/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                .json(mapper.writeValueAsString(hoas.stream().map(Hoa::toFullModel).collect(Collectors.toList()))));
    }

    @Test
    public void testCreateHoa_InvalidRequest_ReturnsBadRequest() throws Exception {
        HoaRequestModel hoaModel = new HoaRequestModel(null, "Country 1", "City 1");

        mvc.perform(MockMvcRequestBuilders.post("/hoa/createHoa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(hoaModel)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testCreateHoa_ValidRequest_ReturnsCreatedHoa() throws Exception {
        HoaRequestModel hoaModel = new HoaRequestModel("HOA 1", "Country 1", "City 1");
        Hoa hoa = new Hoa(
            1, hoaModel.getName(), hoaModel.getCountry(), hoaModel.getCity(), new HashSet<>(), new HashSet<>()
        );
        given(hoaService.createHoa(hoaModel.getName(), hoaModel.getCountry(), hoaModel.getCity())).willReturn(hoa);

        mvc.perform(MockMvcRequestBuilders.post("/hoa/createHoa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(hoaModel)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(hoa.toFullModel())));
    }

}
