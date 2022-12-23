package nl.tudelft.sem.template.hoa.services;

import nl.tudelft.sem.template.commons.models.hoa.FullAddressModel;
import nl.tudelft.sem.template.hoa.entitites.BoardMember;
import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.hoa.entitites.User;
import nl.tudelft.sem.template.hoa.entitites.UserHoa;
import nl.tudelft.sem.template.hoa.exceptions.HoaDoesNotExistException;
import nl.tudelft.sem.template.hoa.exceptions.UserDoesNotExistException;
import nl.tudelft.sem.template.hoa.repositories.BoardMemberRepository;
import nl.tudelft.sem.template.hoa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {
    @Autowired
    private transient UserRepository userRepository;
    @Autowired
    private transient HoaService hoaService;
    @Autowired
    private transient ConnectionService connectionService;
    @Autowired
    private transient BoardMemberRepository boardMemberRepository;

    public UserService(UserRepository userRepository, HoaService hoaService,
                       ConnectionService connectionService, BoardMemberRepository boardMemberRepository) {
        this.userRepository = userRepository;
        this.hoaService = hoaService;
        this.connectionService = connectionService;
        this.boardMemberRepository = boardMemberRepository;
    }

    /**
     * Query for getting all users currently in the table
     *
     * @return the list of all the users currently in the table
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Creates a new User with the provided name
     * <p>
     * Note: Likely not the final implementation due to the linking with the authentication microservice
     *
     * @param displayName the name the user will be saved as
     * @return status on whether the saving succeeded
     */
    public boolean saveUser(String displayName) {
        if (userRepository.existsByDisplayName(displayName)) return false;
        User user = new User(displayName);
        userRepository.save(user);
        return true;
    }

    /**
     * Query to remove the user from an association
     * <p>
     * Does not need any additional checks since a user should be able to leave
     * at any time
     *
     * @return the user that left or an exception
     * @throws HoaDoesNotExistException
     * @throws UserDoesNotExistException
     */
    public User leaveAssociation(
            String displayName, String hoaName, String country, String city
    ) throws UserDoesNotExistException, HoaDoesNotExistException {

        User user = this.userRepository.findByDisplayName(displayName);

        if (user == null)
            throw new UserDoesNotExistException("User for given username was not found");

        Hoa hoa = this.hoaService.getByNaturalId(hoaName, country, city);

        if (hoa == null)
            throw new HoaDoesNotExistException("Hoa for given name/country/city was not found");

        this.connectionService.removeConnection(user, hoa);

        return this.getUser(user.getId());

    }

    /**
     * Request to join an association by a user
     * <p>
     * Note: Likely not the final implementation since the user currently can just join an association no questions asked
     *
     * @param hoaName     the name of the association to be joined
     * @param displayName the display name of the user that wants to join
     * @param address     the address that the membership is based on
     * @return status on whether the joining was successful
     * @throws HoaDoesNotExistException
     * @throws UserDoesNotExistException
     */
    public UserHoa joinAssociation(String hoaName, String displayName, FullAddressModel address)
            throws HoaDoesNotExistException {

        Hoa hoa = hoaService.getByNaturalId(hoaName, address.getCountry(), address.getCity());

        if (hoa == null)
            throw new HoaDoesNotExistException("Hoa with given attributes doesn't exits!");

        User user = userRepository.findByDisplayName(displayName);

        if (user == null)
            user = userRepository.save(new User(displayName));

        UserHoa connection = new UserHoa(user, hoa, address);

        user.joinAssociation(connection);
        hoa.addMember(connection);

        connection.setUser(user);
        connection.setHoa(hoa);

        return connectionService.createConnection(connection);
    }

    /**
     * Queries the database for all the associations of a give user
     *
     * @param id the id of the user whose associations we want to check
     * @return the list of the associations
     */
    public List<UserHoa> getListOfAssociations(int id) {
        return new ArrayList<>(userRepository.findUserById(id).getAssociations());
    }

    /**
     * Query to retrieve a given user based on their id - will likely be only used for testing
     *
     * @param id the id of the user we want to retrieve
     * @return User object corresponding to the provided id
     */
    public User getUser(int id) {
        return userRepository.findUserById(id);
    }

    public boolean isInHoa(String displayName, String hoaName, String country, String city) {
        return this.userRepository.isInHoa(displayName, hoaName, country, city);
    }

    public boolean isInBoard(String displayName) {
        return this.boardMemberRepository.existsBoardMemberByDisplayName(displayName);
    }

    public boolean isInSpecificBoard(String displayName, String hoaName, String country, String city) {
        if (!isInHoa(displayName, hoaName, country, city)) return false;
        if (!isInBoard(displayName)) return false;
        Hoa hoa = hoaService.getByNaturalId(hoaName, country, city);

        return this.boardMemberRepository.existsBoardMemberByDisplayNameAndBoard(displayName, hoa);
    }

    public boolean isInSpecificBoardByUserName(int hoaId, String userName) {
        Hoa hoa = hoaService.getHoaById(hoaId);
        return this.boardMemberRepository.existsBoardMemberByDisplayNameAndBoard(userName, hoa);
    }


}