package nl.tudelft.sem.template.hoa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import nl.tudelft.sem.template.hoa.entitites.Activity;

import java.util.GregorianCalendar;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, String> {
    @Override
    List<Activity> findAll();

    boolean existsByNameAndTime(String name, GregorianCalendar time);

    Activity findByName(String name);

    List<Activity> findAllByHoaId(int hoaId);

    @Query("SELECT a FROM Activity a JOIN a.hoa h JOIN h.members m JOIN m.user u WHERE u.displayName = :displayName")
    List<Activity> findActivitiesForUser(@Param("displayName") String displayName);
}
