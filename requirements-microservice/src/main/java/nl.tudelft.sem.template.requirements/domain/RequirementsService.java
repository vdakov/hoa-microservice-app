package nl.tudelft.sem.template.requirements.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class RequirementsService {

    private final transient RequirementsRepository requirementsRepository;

    public RequirementsService(RequirementsRepository requirementsRepository) {
        this.requirementsRepository = requirementsRepository;
    }

    public Requirements createRequirement(String name, String description) throws Exception {
        Requirements req = new Requirements(name, description);
        return requirementsRepository.save(req);
    }

    public List<Requirements> getAll() {
        return requirementsRepository.findAll();
    }

    public Optional<Requirements> get(int id) {
        return requirementsRepository.findById(id);
    }

}
