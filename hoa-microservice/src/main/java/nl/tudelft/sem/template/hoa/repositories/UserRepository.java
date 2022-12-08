package nl.tudelft.sem.template.hoa.repositories;


import nl.tudelft.sem.template.hoa.entitites.BoardMember;
import nl.tudelft.sem.template.hoa.entitites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Override
    List<User> findAll();

    @Override
    User save(User user);



}
