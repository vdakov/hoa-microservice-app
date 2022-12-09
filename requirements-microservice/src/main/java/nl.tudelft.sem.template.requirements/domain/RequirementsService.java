package nl.tudelft.sem.template.requirements.domain;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RequirementsService {

    private final transient RequirementsRepository requirementsRepository;

    public RequirementsService(RequirementsRepository requirementsRepository) {
        this.requirementsRepository = requirementsRepository;
    }

    public Requirements createRequirement(int id, String name, String description) throws Exception {
        Requirements req = new Requirements(id, name, description);
        return requirementsRepository.save(req);
    }

    public List<Requirements> getAll() {
        return requirementsRepository.findAll();
    }

}
