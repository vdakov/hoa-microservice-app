package nl.tudelft.sem.template.hoa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nl.tudelft.sem.template.hoa.domain.activity.Activity;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, String> {
    @Override
    List<Activity> findAll();

    boolean existsByName(String name);

    List<Activity> findAllByHoaId(int hoaId);
}
