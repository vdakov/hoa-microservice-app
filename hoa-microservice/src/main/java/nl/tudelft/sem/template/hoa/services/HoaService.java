package nl.tudelft.sem.template.hoa.services;

import java.util.List;
import java.util.NoSuchElementException;


import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.hoa.exceptions.HoaDoesNotExistException;
import nl.tudelft.sem.template.hoa.models.HoaModel;
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
        return this.hoaRepository.existsById(hoaId);
    }


    /**
     * Updates a Homeowners Association (HOA) with the given ID.
     *
     * @param id       the ID of the HOA to update
     * @param hoa      the new HOA data
     * @return         the updated HOA
     * @throws HoaDoesNotExistException if the HOA with the given ID does not exist
     */
    public Hoa updateHoa(int id, HoaModel hoa) throws HoaDoesNotExistException {
        if (!this.existsById(id)) 
            throw new HoaDoesNotExistException("Hoa with that id does not exist");

        return this.hoaRepository.save(
            new Hoa(id, hoa.getName(), hoa.getCountry(), hoa.getCity(), hoa.getBoardMembers(), hoa.getMembers())
        );
        
    }

    public Hoa getByNaturalId(String name, String country, String city) {

        

        return this.hoaRepository.findByNaturalId(name, country, city);
    }

}
