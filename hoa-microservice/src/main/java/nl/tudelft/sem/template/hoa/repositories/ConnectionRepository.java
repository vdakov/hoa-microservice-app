package nl.tudelft.sem.template.hoa.repositories;

import java.util.List;

import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.hoa.entitites.User;
import nl.tudelft.sem.template.hoa.entitites.UserHoa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionRepository extends JpaRepository<UserHoa, Integer> {
    @Override
    List<UserHoa> findAll();

    @Override
    UserHoa save(UserHoa hoa);

    UserHoa findById(int id);

    @Modifying
    @Query("DELETE FROM UserHoa u WHERE u.user = :user AND u.hoa = :hoa")
    void delete(@Param("user") User user, @Param("hoa") Hoa hoa);
}
