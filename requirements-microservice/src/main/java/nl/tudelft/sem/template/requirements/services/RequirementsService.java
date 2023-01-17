package nl.tudelft.sem.template.requirements.services;

import java.util.List;
import java.util.stream.Collectors;

import nl.tudelft.sem.template.requirements.domain.Requirements;
import nl.tudelft.sem.template.requirements.repositories.RequirementsRepository;
import org.springframework.stereotype.Service;

@Service
public class RequirementsService {

    private final transient RequirementsRepository requirementsRepository;

    public RequirementsService(RequirementsRepository requirementsRepository) {
        this.requirementsRepository = requirementsRepository;
    }

    public Requirements createRequirement(int hoaId, String name, String description) throws Exception {
        Requirements req = new Requirements(hoaId, name, description);
        requirementsRepository.save(req);
        return req;
    }

    public void updateRequirement(Requirements requirement, String newName, String newDescription) throws Exception {
        requirement.setRequirementName(newName);
        requirement.setRequirementDescription(newDescription);
        requirementsRepository.save(requirement);
    }

    public void deleteRequirement(Requirements requirement) throws Exception {
        requirementsRepository.delete(requirement);
    }

    public List<Requirements> getAll() {
        return requirementsRepository.findAll();
    }

    public List<Requirements> getRequirementsByHoa(int hoaId){
        return getAll()
                .stream().filter(o -> o.getHoaId() == hoaId)
                .collect(Collectors.toList());
    }

    public Requirements findById(int id) {
        return requirementsRepository.findById(id);
    }

}
