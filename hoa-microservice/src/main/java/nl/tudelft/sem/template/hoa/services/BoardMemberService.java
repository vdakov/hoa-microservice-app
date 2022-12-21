package nl.tudelft.sem.template.hoa.services;

import nl.tudelft.sem.template.hoa.entitites.BoardMember;
import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.hoa.repositories.BoardMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardMemberService {

    @Autowired
    private transient BoardMemberRepository boardMemberRepository;
    @Autowired
    private transient HoaService hoaService;

    /**
     * Queries table for all the board members of a current hoa
     * @param hoaId the id sent to the endpoint
     * @return a list of the board members
     */
    public List<BoardMember> getAllBoardMembersOfAnHoa(int hoaId) {
        Hoa hoa = hoaService.getHoaById(hoaId);
        return boardMemberRepository.findBoardMemberByBoard(hoa);
    }

    /**
     * Queries the board members table for all users that are a board member
     * @return the list of all board members
     */
    public List<BoardMember> getAllBoardMembers() {
        return boardMemberRepository.findAll();
    }

    /**
     * TO-DO: Need to figure out whether this is how the leaving would work;
     * Complicated reason is that a user outside of a board just behaves like a regular user
     * so maybe consider turning inherited parts into fields of user (work in progress)
     *
     * @param id id of the board member to leave
     * @return the status of whether the removal was successful
     */
    public boolean leaveBoard(int id) {
        return boardMemberRepository.removeBoardMemberById(id);
    }

    /**
     * Gets for how many years a user has been on the board
     * @param id the id of the user the query is performed on
     * @return the number of years that a user has been on the board
     */
    public int getYearsOnBoard(int id) {
        return boardMemberRepository.findBoardMemberById(id).getYearsOnBoard();
    }

}
