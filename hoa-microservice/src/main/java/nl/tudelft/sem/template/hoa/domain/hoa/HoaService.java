package nl.tudelft.sem.template.hoa.domain.hoa;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

@Service
public class HoaService {
    private final transient HoaRepository hoaRepository;

    /**
     * Creates a new hoa service with the given repository.
     *
     * @param hoaRepository the repository
     */
    public HoaService(HoaRepository hoaRepository) {
        this.hoaRepository = hoaRepository;
    }

    public Hoa createHoa(String name, String country, String city) throws Exception {
        Hoa hoa = new Hoa(name, country, city);
        return hoaRepository.save(hoa);
    }

    /**
     * Returns all activities in the repository.
     *
     * @return a list of all activities in the repository
     */
    public List<Hoa> getAllHoas() {
        return hoaRepository.findAll();
    }

    public Hoa getHoaById(int id) {
        System.out.println("hoaId: " + id);
        Hoa out = hoaRepository.findById(id);
        if (out == null) throw new NoSuchElementException();
        return out;
    }
}
