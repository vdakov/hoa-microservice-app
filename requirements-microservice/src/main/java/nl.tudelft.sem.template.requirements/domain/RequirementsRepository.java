package nl.tudelft.sem.template.requirements.domain;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequirementsRepository extends JpaRepository<Requirements, Integer> {
    @Override
    List<Requirements> findAll();

}
