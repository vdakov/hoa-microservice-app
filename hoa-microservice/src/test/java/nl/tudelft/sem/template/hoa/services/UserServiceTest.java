package nl.tudelft.sem.template.hoa.services;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;

import nl.tudelft.sem.template.commons.models.hoa.FullAddressModel;
import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.hoa.entitites.User;
import nl.tudelft.sem.template.hoa.entitites.UserHoa;
import nl.tudelft.sem.template.hoa.exceptions.HoaDoesNotExistException;
import nl.tudelft.sem.template.hoa.exceptions.UserDoesNotExistException;
import nl.tudelft.sem.template.hoa.repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ActiveProfiles({"test"})
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private HoaService hoaService;

    @Mock
    private ConnectionService connectionService;

    @Mock
    private BoardMemberService boardMemberService;

    private UserService userService;

    private String displayName;
    private String hoaName;
    private String country;
    private String city;


    @BeforeEach
    public void setUp() {
        userService = new UserService(userRepository, hoaService, connectionService, boardMemberService);
        //initialization of some commonly used variables
        this.displayName = "user1";
        this.hoaName = "HOA1";
        this.country = "Netherlands";
        this.city = "Delft";
    }

    @Test
    public void testGetAllUsers() {
        List<User> expectedUsers = Arrays.asList(new User("user1"), new User("user2"));
        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> actualUsers = userService.getAllUsers();

        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void testSaveUser_success() {
        when(userRepository.existsByDisplayName(displayName)).thenReturn(false);

        boolean result = userService.saveUser(displayName);

        assertTrue(result);
    }

    @Test
    public void testSaveUser_failure() {
        when(userRepository.existsByDisplayName(displayName)).thenReturn(true);

        boolean result = userService.saveUser(displayName);

        assertFalse(result);
    }

    @Test
    public void testLeaveAssociation_success() throws UserDoesNotExistException, HoaDoesNotExistException {
        User user = new User(displayName);
        Hoa hoa = new Hoa(hoaName, country, city);

        when(userRepository.findByDisplayName(displayName)).thenReturn(user);
        when(userRepository.findUserById(user.getId())).thenReturn(user);
        when(hoaService.getByNaturalId(hoaName, country, city)).thenReturn(hoa);

        User actualUser = userService.leaveAssociation(displayName, hoaName, country, city);

        verify(connectionService, times(1)).removeConnection(user, hoa);
        verify(connectionService, times(1)).removeConnection(any(User.class), any(Hoa.class));

        assertEquals(user, actualUser);
    }

    @Test
    public void testLeaveAssociation_userDoesNotExist() {
        when(userRepository.findByDisplayName(displayName)).thenReturn(null);

        UserDoesNotExistException exception = assertThrows(UserDoesNotExistException.class,
                () -> userService.leaveAssociation(displayName, hoaName, country, city));
        assertEquals("User for given username was not found", exception.getMessage());
    }

    @Test
    public void testLeaveAssociation_hoaDoesNotExist() throws UserDoesNotExistException {
        User user = new User(displayName);
        when(userRepository.findByDisplayName(displayName)).thenReturn(user);
        when(hoaService.getByNaturalId(hoaName, country, city)).thenReturn(null);

        HoaDoesNotExistException exception = assertThrows(HoaDoesNotExistException.class,
                () -> userService.leaveAssociation(displayName, hoaName, country, city));
        assertEquals("Hoa for given name/country/city was not found", exception.getMessage());
    }

    @Test
    public void testJoinAssociation_success() throws HoaDoesNotExistException {
        User user = new User(displayName);
        Hoa hoa = new Hoa(hoaName, country, city);
        FullAddressModel address = new FullAddressModel(country, city, "Street", "12", "2565 AA");
        UserHoa expectedConnection = new UserHoa(user, hoa, address);
        when(userRepository.findByDisplayName(displayName)).thenReturn(user);
        when(hoaService.getByNaturalId(hoaName, country, city)).thenReturn(hoa);
        when(connectionService.createConnection(expectedConnection)).thenReturn(expectedConnection);

        UserHoa actualConnection = userService.joinAssociation(hoaName, displayName, address);

        assertEquals(actualConnection.getUser(), user);
        assertEquals(actualConnection.getHoa(), hoa);

        assertEquals(expectedConnection, actualConnection);
    }

    @Test
    public void testJoinAssociation_hoaDoesNotExist() {
        FullAddressModel address = new FullAddressModel("Netherlands", "Delft", "Street", "12", "2565 AA");
        when(hoaService.getByNaturalId(hoaName, "Netherlands", "Delft")).thenReturn(null);

        HoaDoesNotExistException exception = assertThrows(HoaDoesNotExistException.class,
                () -> userService.joinAssociation(hoaName, displayName, address));
        assertEquals("Hoa with given attributes doesn't exits!", exception.getMessage());
    }

    @Test
    public void testJoinAssociation_userDoesNotExist() throws HoaDoesNotExistException {
        User user = new User(displayName);
        Hoa hoa = new Hoa(hoaName, country, city);
        FullAddressModel address = new FullAddressModel(country, city, "Street", "12", "2565 AA");
        UserHoa expectedConnection = new UserHoa(user, hoa, address);
        when(userRepository.findByDisplayName(displayName)).thenReturn(null);
        when(userRepository.save(new User(displayName))).thenReturn(user);
        when(hoaService.getByNaturalId(hoaName, country, city)).thenReturn(hoa);
        when(connectionService.createConnection(expectedConnection)).thenReturn(expectedConnection);

        UserHoa actualConnection = userService.joinAssociation(hoaName, displayName, address);

        assertEquals(expectedConnection, actualConnection);
    }

    @Test
    public void testGetListOfAssociations_none() {
        User user = new User("user1");
        when(userRepository.findUserById(1)).thenReturn(user);

        assertEquals(userService.getListOfAssociations(1), new ArrayList<UserHoa>());
    }

    @Test
    public void testGetListOfAssociations_some() {
        User user = new User(displayName);
        Hoa hoa = new Hoa(hoaName, country, city);
        FullAddressModel address = new FullAddressModel(country, city, "Street", "12", "2565 AA");
        UserHoa expectedConnection = new UserHoa(user, hoa, address);
        user.joinAssociation(expectedConnection);
        when(userRepository.findUserById(1)).thenReturn(user);

        assertEquals(userService.getListOfAssociations(1), new ArrayList<UserHoa>(user.getAssociations()));
    }

    @Test
    public void testIsInHoa_false() {
        when(userService.isInHoa(displayName, hoaName, country, city)).thenReturn(false);

        assertEquals(userService.isInHoa(displayName, hoaName, country, city), false);
    }

    @Test
    public void testIsInHoa_true() {
        when(userRepository.isInHoa(displayName, hoaName, country, city)).thenReturn(true);

        assertEquals(userService.isInHoa(displayName, hoaName, country, city), true);
    }

    @Test
    public void testIsInHoaById_false() {
        int hoaId = 0;
        Hoa hoa = new Hoa(hoaName, country, city);
        hoa.setId(hoaId);

        when(hoaService.getHoaById(hoaId)).thenReturn(hoa);
        when(userRepository.isInHoa(displayName, hoa.getName(), hoa.getCountry(), hoa.getCity())).thenReturn(false);

        assertFalse(userService.isInHoa(displayName, hoaId));
    }

    @Test
    public void testIsInHoaById_true() {
        int hoaId = 0;
        Hoa hoa = new Hoa(hoaName, country, city);
        hoa.setId(hoaId);

        when(hoaService.getHoaById(hoaId)).thenReturn(hoa);
        when(userRepository.isInHoa(displayName, hoa.getName(), hoa.getCountry(), hoa.getCity())).thenReturn(true);

        assertTrue(userService.isInHoa(displayName, hoaId));
    }

    @Test
    public void testIsInBoardByDisplayName() {
        String falseName = "user2";

        when(boardMemberService.existsBoardMemberByDisplayName(any(String.class))).thenReturn(false);
        when(boardMemberService.existsBoardMemberByDisplayName(displayName)).thenReturn(true);

        assertFalse(userService.isInBoard(falseName));
        assertTrue(userService.isInBoard(displayName));
    }

    @Test
    public void testIsInSpecificBoard_notInHoa() {
        when(userRepository.isInHoa(displayName, hoaName, country, city))
                .thenReturn(false); //rejects at the first conditional

        assertFalse(userService.isInSpecificBoard(displayName, hoaName, country, city));
    }

    @Test
    public void testIsInSpecificBoard_notInAnyBoard() {
        when(userService.isInHoa(displayName, hoaName, country, city))
                .thenReturn(true); //bypasses first conditional
        when(userService.isInBoard(displayName)).thenReturn(false); //rejects at the second conditional


        assertFalse(userService.isInSpecificBoard(displayName, hoaName, country, city));
    }

    @Test
    public void testIsInSpecificBoard_notInThisBoard() {
        Hoa hoa = new Hoa(hoaName, country, city);

        when(userService.isInHoa(displayName, hoaName, country, city))
                .thenReturn(true); //bypasses conditional
        when(userService.isInBoard(displayName)).thenReturn(true); //bypasses condition
        when(hoaService.getByNaturalId(hoaName, country, city))
                .thenReturn(hoa); //fetches hoa to be used in service call mock
        when(boardMemberService.existsBoardMemberByDisplayNameAndBoard(displayName, hoa)).thenReturn(false);

        assertFalse(userService.isInSpecificBoard(displayName, hoaName, country, city));

    }


    @Test
    public void testIsInSpecificBoard_true() {
        Hoa hoa = new Hoa(hoaName, country, city);

        when(userService.isInHoa(displayName, hoaName, country, city))
                .thenReturn(true); //bypasses conditional
        when(userService.isInBoard(displayName)).thenReturn(true); //bypasses condition
        when(hoaService.getByNaturalId(hoaName, country, city))
                .thenReturn(hoa); //fetches hoa to be used in service call mock
        when(boardMemberService.existsBoardMemberByDisplayNameAndBoard(displayName, hoa)).thenReturn(true);

        assertTrue(userService.isInSpecificBoard(displayName, hoaName, country, city));
    }

    @Test
    public void testInSpecificBoardByUserName_true() {
        String userName = "user1";
        int hoaId = 0;
        Hoa hoa = new Hoa(hoaName, country, city);
        hoa.setId(hoaId);

        when(hoaService.getHoaById(hoaId)).thenReturn(hoa);
        when(boardMemberService.existsBoardMemberByDisplayNameAndBoard(userName, hoa)).thenReturn(true);

        assertTrue(userService.isInSpecificBoardByUserName(hoaId, userName));
    }

    @Test
    public void testInSpecificBoardByUserName_false() {
        String userName = "user1";
        int hoaId = 0;
        //fetches hoa so that the end behavior from the services is known
        Hoa hoa = new Hoa(hoaName, country, city);
        hoa.setId(hoaId);

        when(hoaService.getHoaById(hoaId)).thenReturn(hoa);
        when(boardMemberService.existsBoardMemberByDisplayNameAndBoard(userName, hoa)).thenReturn(false);

        assertFalse(userService.isInSpecificBoardByUserName(hoaId, userName));
    }

    @Test
    public void testFindByDisplayName_found() {
        User user = new User(displayName);

        //bypasses initial check for users existing
        when(userRepository.existsByDisplayName(displayName)).thenReturn(true);
        //returns the correct user when called
        when(userRepository.findByDisplayName(displayName)).thenReturn(user);

        assertEquals(userService.findByDisplayName(displayName), user);
    }

    @Test
    public void testFindByDisplayName_notFound() {
        assertNull(userService.findByDisplayName("Ivan"));
    }


}