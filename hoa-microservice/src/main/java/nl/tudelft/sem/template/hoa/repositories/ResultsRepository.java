package nl.tudelft.sem.template.hoa.repositories;

import nl.tudelft.sem.template.hoa.entitites.Results;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository to store the results that have later be retrieved at any time from the database
 */
@Repository
public interface ResultsRepository extends JpaRepository<Results, Integer> {
    @Override
    List<Results> findAll();

    @Override
    Results save(Results results);


}
