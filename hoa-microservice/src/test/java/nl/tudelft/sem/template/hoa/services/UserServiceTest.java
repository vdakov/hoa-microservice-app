package nl.tudelft.sem.template.hoa.services;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import nl.tudelft.sem.template.commons.models.hoa.FullAddressModel;
import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.hoa.entitites.User;
import nl.tudelft.sem.template.hoa.entitites.UserHoa;
import nl.tudelft.sem.template.hoa.exceptions.HoaDoesNotExistException;
import nl.tudelft.sem.template.hoa.exceptions.UserDoesNotExistException;
import nl.tudelft.sem.template.hoa.repositories.BoardMemberRepository;
import nl.tudelft.sem.template.hoa.repositories.UserRepository;
import nl.tudelft.sem.template.hoa.services.HoaService;
import nl.tudelft.sem.template.hoa.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
    private BoardMemberRepository boardMemberRepository;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService(userRepository, hoaService, connectionService, boardMemberRepository);
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
        String displayName = "user1";
        when(userRepository.existsByDisplayName(displayName)).thenReturn(false);

        boolean result = userService.saveUser(displayName);

        assertTrue(result);
    }

    @Test
    public void testSaveUser_failure() {
        String displayName = "user1";
        when(userRepository.existsByDisplayName(displayName)).thenReturn(true);

        boolean result = userService.saveUser(displayName);

        assertFalse(result);
    }

    @Test
    public void testLeaveAssociation_success() throws UserDoesNotExistException, HoaDoesNotExistException {
        String displayName = "user1";
        String hoaName = "HOA1";
        String country = "Netherlands";
        String city = "Delft";
        User user = new User(displayName);
        Hoa hoa = new Hoa(hoaName, country, city);
        when(userRepository.findByDisplayName(displayName)).thenReturn(user);
        when(userRepository.findUserById(user.getId())).thenReturn(user);
        when(hoaService.getByNaturalId(hoaName, country, city)).thenReturn(hoa);

        User actualUser = userService.leaveAssociation(displayName, hoaName, country, city);

        assertEquals(user, actualUser);
    }

    @Test
    public void testLeaveAssociation_userDoesNotExist() {
        String displayName = "user1";
        String hoaName = "HOA1";
        String country = "Netherlands";
        String city = "Delft";
        when(userRepository.findByDisplayName(displayName)).thenReturn(null);

        UserDoesNotExistException exception = assertThrows(UserDoesNotExistException.class,
            () -> userService.leaveAssociation(displayName, hoaName, country, city));
        assertEquals("User for given username was not found", exception.getMessage());
    }

    @Test
    public void testLeaveAssociation_hoaDoesNotExist() throws UserDoesNotExistException {
        String displayName = "user1";
        String hoaName = "HOA1";
        String country = "Netherlands";
        String city = "Delft";
        User user = new User(displayName);
        when(userRepository.findByDisplayName(displayName)).thenReturn(user);
        when(hoaService.getByNaturalId(hoaName, country, city)).thenReturn(null);

        HoaDoesNotExistException exception = assertThrows(HoaDoesNotExistException.class,
            () -> userService.leaveAssociation(displayName, hoaName, country, city));
        assertEquals("Hoa for given name/country/city was not found", exception.getMessage());
    }

    @Test
    public void testJoinAssociation_success() throws HoaDoesNotExistException {
        String displayName = "user1";
        String hoaName = "HOA1";
        String country = "Netherlands";
        String city = "Delft";
        User user = new User(displayName);
        Hoa hoa = new Hoa(hoaName, country, city);
        FullAddressModel address = new FullAddressModel(country, city, "Street", "12", "2565 AA");
        UserHoa expectedConnection = new UserHoa(user, hoa, address);
        when(userRepository.findByDisplayName(displayName)).thenReturn(user);
        when(hoaService.getByNaturalId(hoaName, country, city)).thenReturn(hoa);
        when(connectionService.createConnection(expectedConnection)).thenReturn(expectedConnection);

        UserHoa actualConnection = userService.joinAssociation(hoaName, displayName, address);

        assertEquals(expectedConnection, actualConnection);
    }

    @Test
    public void testJoinAssociation_hoaDoesNotExist() {
        String displayName = "user1";
        String hoaName = "HOA1";
        FullAddressModel address = new FullAddressModel("Netherlands", "Delft", "Street", "12", "2565 AA");
        when(hoaService.getByNaturalId(hoaName, "Netherlands", "Delft")).thenReturn(null);

        HoaDoesNotExistException exception = assertThrows(HoaDoesNotExistException.class,
            () -> userService.joinAssociation(hoaName, displayName, address));
        assertEquals("Hoa with given attributes doesn't exits!", exception.getMessage());
    }

    @Test
    public void testJoinAssociation_userDoesNotExist() throws HoaDoesNotExistException {
        String displayName = "user1";
        String hoaName = "HOA1";
        String country = "Netherlands";
        String city = "Delft";
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
        String displayName = "user1";
        String hoaName = "HOA1";
        String country = "Netherlands";
        String city = "Delft";
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
        String displayName = "user1";
        String hoaName = "HOA1";
        String country = "Netherlands";
        String city = "Delft";
        when(userService.isInHoa(displayName, hoaName, country, city)).thenReturn(false);

        assertEquals(userService.isInHoa(displayName, hoaName, country, city), false);
    }

    @Test
    public void testIsInHoa_true() {
        String displayName = "user1";
        String hoaName = "HOA1";
        String country = "Netherlands";
        String city = "Delft";
        when(userService.isInHoa(displayName, hoaName, country, city)).thenReturn(true);

        assertEquals(userService.isInHoa(displayName, hoaName, country, city), true);
    }

}