package nl.tudelft.sem.template.pnb.domain.activity;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    @Override
    List<Activity> findAll();

    @Override
    boolean existsById(Integer i);
}
