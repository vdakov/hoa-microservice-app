package nl.tudelft.sem.template.hoa.repositories;

import java.util.List;

import nl.tudelft.sem.template.hoa.entitites.Hoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaRepository extends JpaRepository<Hoa, Integer> {
    @Override
    List<Hoa> findAll();

    @Override
    Hoa save(Hoa hoa);

    Hoa findById(int id);

    @Query(value = "SELECT h FROM Hoa h WHERE h.name = :name AND h.country = :country AND h.city = :city")
    Hoa findByNaturalId(@Param("name") String name, @Param("country") String country, @Param("city") String city);
}
