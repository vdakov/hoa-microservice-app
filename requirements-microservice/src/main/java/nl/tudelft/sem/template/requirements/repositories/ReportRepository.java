package nl.tudelft.sem.template.requirements.repositories;

import nl.tudelft.sem.template.requirements.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    @Override
    List<Report> findAll();

}
