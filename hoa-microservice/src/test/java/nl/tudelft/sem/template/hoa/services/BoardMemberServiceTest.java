package nl.tudelft.sem.template.hoa.services;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import io.jsonwebtoken.impl.compression.DeflateCompressionCodec;

import org.junit.jupiter.api.Test;

import nl.tudelft.sem.template.hoa.entitites.BoardMember;
import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.hoa.repositories.BoardMemberRepository;

@SpringBootTest
@ActiveProfiles({"test"})
@ExtendWith(MockitoExtension.class)
public class BoardMemberServiceTest {
    
    @Mock
    public BoardMemberRepository boardMemberRepository;

    @Mock
    public HoaService hoaService;

    public BoardMemberService boardMemberService;

    @BeforeEach
    public void setUp() {
        boardMemberService = new BoardMemberService(boardMemberRepository, hoaService);
    }


    @Test
    public void allBoardMembersEmptyTest() {

        when(boardMemberRepository.findAll()).thenReturn(new ArrayList<>());

        List<BoardMember> board = boardMemberService.getAllBoardMembers();
        
        assertEquals(0, board.size());

        List<BoardMember> newBoard = List.of(new BoardMember());

        when(boardMemberRepository.findAll()).thenReturn(newBoard);

        board = boardMemberService.getAllBoardMembers();

        assertEquals(1, board.size());

    }

    @Test
    public void allBoardMembersOfAnHoaEmptyTest() {
        Hoa hoa = new Hoa(1, "name", "Netherlands", "Deft", new HashSet<>(), new HashSet<>());
        when(hoaService.getHoaById(1)).thenReturn(hoa);

        when(boardMemberRepository.findBoardMemberByBoard(hoa)).thenReturn(new ArrayList<>());

        List<BoardMember> board = boardMemberService.getAllBoardMembersOfAnHoa(1);
        
        assertEquals(0, board.size());

        List<BoardMember> newBoard = List.of(new BoardMember());

        when(boardMemberRepository.findBoardMemberByBoard(hoa)).thenReturn(newBoard);

        board = boardMemberService.getAllBoardMembersOfAnHoa(1);

        assertEquals(1, board.size());

    }

    @Test
    public void leaveBoardTest() {

        when(boardMemberRepository.removeBoardMemberById(0)).thenReturn(true);

        assertTrue(boardMemberService.leaveBoard(0));

        when(boardMemberRepository.removeBoardMemberById(0)).thenReturn(false);

        assertFalse(boardMemberService.leaveBoard(0));

    }

    @Test
    public void yearsOnBoardTest() {

        BoardMember memeber = new BoardMember();
        
        memeber.setYearsOnBoard(0);

        when(boardMemberRepository.findBoardMemberById(memeber.getId())).thenReturn(memeber);

        assertEquals(boardMemberService.getYearsOnBoard(memeber.getId()), 0);

        memeber.setYearsOnBoard(1);

        when(boardMemberRepository.findBoardMemberById(memeber.getId())).thenReturn(memeber);

        assertEquals(boardMemberService.getYearsOnBoard(memeber.getId()), 1);
    }

    @Test
    public void boardMemberByBoardEmptyTest() {
        List<BoardMember> memebers = List.of();

        Hoa hoa = new Hoa(1, "", "", "", new HashSet<>(), new HashSet<>());

        when(boardMemberRepository.findBoardMemberByBoard(hoa)).thenReturn(memebers);

        List<BoardMember> board = boardMemberService.findBoardMemberByBoard(hoa);

        assertEquals(0, board.size());

        memebers = List.of(new BoardMember());

        when(boardMemberRepository.findBoardMemberByBoard(hoa)).thenReturn(memebers);

        board = boardMemberService.findBoardMemberByBoard(hoa);

        assertEquals(1, board.size());

    }

    @Test
    public void existsBoardMemberByDisplayNameAndBoardTest() {

        Hoa hoa = new Hoa();

        when(boardMemberRepository.existsBoardMemberByDisplayNameAndBoard("name", hoa)).thenReturn(true);

        assertTrue(boardMemberService.existsBoardMemberByDisplayNameAndBoard("name", hoa));

        when(boardMemberRepository.existsBoardMemberByDisplayNameAndBoard("name", hoa)).thenReturn(false);

        assertFalse(boardMemberService.existsBoardMemberByDisplayNameAndBoard("name", hoa));

    }

    @Test
    public void existsBoardMemberByDisplayNameTest() {
        
        when(boardMemberRepository.existsBoardMemberByDisplayName("name")).thenReturn(true);

        assertTrue(boardMemberService.existsBoardMemberByDisplayName("name"));

        when(boardMemberRepository.existsBoardMemberByDisplayName("name")).thenReturn(false);

        assertFalse(boardMemberService.existsBoardMemberByDisplayName("name"));

    }

}
