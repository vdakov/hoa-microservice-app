package nl.tudelft.sem.template.hoa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.commons.models.HoaModel;
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

    @Test
    public void testGetAllHoas() {
        List<Hoa> expectedHoas = Collections.singletonList(new Hoa("MyHoa", "USA", "New York"));
        when(hoaRepository.findAll()).thenReturn(expectedHoas);

        List<Hoa> actualHoas = hoaService.getAllHoas();

        assertEquals(expectedHoas, actualHoas);
    }

    @Test
    public void testGetHoaById_Success() {
        Hoa expectedHoa = new Hoa("MyHoa", "USA", "New York");
        when(hoaRepository.findById(1)).thenReturn(expectedHoa);

        Hoa actualHoa = hoaService.getHoaById(1);

        assertEquals(expectedHoa, actualHoa);
    }
    
    @Test
    public void testGetHoaById_NoSuchElementException() {
        when(hoaRepository.findById(1)).thenReturn(null);
    
        assertThrows(NoSuchElementException.class, () -> hoaService.getHoaById(1));
    }
    
    @Test
    public void testExistsById_True() {
        when(hoaRepository.existsById(1)).thenReturn(true);
    
        boolean result = hoaService.existsById(1);
    
        assertTrue(result);
    }
    
    @Test
    public void testExistsById_False() {
        when(hoaRepository.existsById(1)).thenReturn(false);
    
        boolean result = hoaService.existsById(1);
    
        assertFalse(result);
    }
    
}