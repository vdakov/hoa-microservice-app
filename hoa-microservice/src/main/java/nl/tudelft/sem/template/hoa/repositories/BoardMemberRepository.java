package nl.tudelft.sem.template.hoa.repositories;

import nl.tudelft.sem.template.hoa.entitites.BoardMember;
import nl.tudelft.sem.template.hoa.entitites.Hoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Board member repository to model the current inheritance model of the User and BoardMember classes
 *
 * Allows for some useful queries in the endpoints, which is the reason for its existence
 */
@Repository
public interface BoardMemberRepository extends JpaRepository<BoardMember, Integer> {

    List<BoardMember> findBoardMemberByBoard(Hoa board);

    BoardMember save(BoardMember boardMember);

    BoardMember findBoardMemberById(int id);

    boolean removeBoardMemberById(int id);
}
