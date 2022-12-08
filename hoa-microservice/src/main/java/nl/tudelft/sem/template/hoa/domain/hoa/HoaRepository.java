package nl.tudelft.sem.template.hoa.domain.hoa;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaRepository extends JpaRepository<Hoa, Integer> {
    @Override
    List<Hoa> findAll();

    Hoa save();
}
