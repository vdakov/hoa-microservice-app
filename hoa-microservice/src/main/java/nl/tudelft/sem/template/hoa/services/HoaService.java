package nl.tudelft.sem.template.hoa.services;

import java.util.List;

import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.hoa.repositories.HoaRepository;
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


    public Hoa createActivity(String name, String country, String city) throws Exception {
        Hoa hoa = new Hoa(name, country, city);
        return hoaRepository.save(hoa);
    }

    /**
     * Returns all activities in the repository.
     *
     * @return a list of all activities in wthe repository
     */
    public List<Hoa> getAllHoas() {
        return hoaRepository.findAll();
    }

}
