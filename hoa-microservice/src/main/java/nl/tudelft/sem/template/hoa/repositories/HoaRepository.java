package nl.tudelft.sem.template.hoa.repositories;

import java.util.List;

import nl.tudelft.sem.template.hoa.entitites.Hoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaRepository extends JpaRepository<Hoa, Integer> {
    @Override
    List<Hoa> findAll();

    @Override
    Hoa save(Hoa hoa);

    Hoa findById(int id);

    @Override
    boolean existsById(Integer integer);
}
