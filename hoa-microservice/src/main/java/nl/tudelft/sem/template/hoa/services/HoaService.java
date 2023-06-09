package nl.tudelft.sem.template.hoa.services;

import java.util.List;
import java.util.NoSuchElementException;

import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.hoa.repositories.HoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HoaService {

    private transient HoaRepository hoaRepository;

    @Autowired
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
     * @return a list of all activities in wthe repository
     */
    public List<Hoa> getAllHoas() {
        return hoaRepository.findAll();
    }

    public Hoa getHoaById(int id) {
        Hoa out = hoaRepository.findById(id);
        if (out == null) throw new NoSuchElementException();
        return out;
    }


    public boolean existsById(int hoaId) {
        return hoaRepository.existsById(hoaId);
    }

    public Hoa getByNaturalId(String name, String country, String city) {
        return this.hoaRepository.findByNaturalId(name, country, city);
    }



}
