package nl.tudelft.sem.template.hoa.repositories;

import nl.tudelft.sem.template.hoa.entitites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for the users table in the database
 *
 * Contains many useful queries used in the endpoints
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Override
    List<User> findAll();

    @Override
    User save(User user);

    User findUserById(int id);

    boolean existsById(int id);

    boolean existsByDisplayName(String displayName);

    User findByDisplayName(String displayName);

    @Query(
        "SELECT COUNT(uh) > 0 FROM UserHoa uh " 
        + "JOIN uh.user u " 
        + "JOIN uh.hoa h " 
        + "WHERE u.displayName = :displayName AND h.name = :hoaName AND h.country = :country AND h.city = :city"
    )
    boolean isInHoa(
        @Param("displayName") String displayName, @Param("hoaName") String hoaName, 
        @Param("country") String country, @Param("city") String city
    );
}
