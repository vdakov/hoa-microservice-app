package nl.tudelft.sem.template.hoa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.hoa.models.HoaModel;
import nl.tudelft.sem.template.hoa.repositories.HoaRepository;


@SpringBootTest
@ActiveProfiles({"test"})
@ExtendWith(MockitoExtension.class)
public class HoaServiceTest {
    @Mock
    private HoaRepository hoaRepository;

    @InjectMocks
    private HoaService hoaService;

    @BeforeEach
    public void setup() {
        hoaService = new HoaService(hoaRepository);
    }

    @Test
    public void testCreateHoa() throws Exception {
        Hoa expectedHoa = new Hoa("MyHoa", "USA", "New York");
        when(hoaRepository.save(expectedHoa)).thenReturn(expectedHoa);

        HoaModel hoaModel = new HoaModel("MyHoa", "USA", "New York");
        Hoa actualHoa = hoaService.createHoa(hoaModel.getName(), hoaModel.getCountry(), hoaModel.getCity());

        assertEquals(expectedHoa, actualHoa);
    }
}
