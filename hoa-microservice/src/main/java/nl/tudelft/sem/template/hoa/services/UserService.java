package nl.tudelft.sem.template.hoa.services;

import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.hoa.entitites.User;
import nl.tudelft.sem.template.hoa.entitites.UserHoa;
import nl.tudelft.sem.template.hoa.exceptions.HoaDoesNotExistException;
import nl.tudelft.sem.template.hoa.exceptions.UserDoesNotExistException;
import nl.tudelft.sem.template.hoa.models.FullAddressModel;
import nl.tudelft.sem.template.hoa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @param hoaId the id of the association to be left
     * @param id    the id of the user to be left
     * @return status on whetehr the leaving was succesful
     */
    public boolean leaveAssociation(int hoaId, int id) throws UserDoesNotExistException {

        // TODO in later MR (this one is already way too big)

        return false;
    }

    /**
     * Request to join an association by a user
     * <p>
     * Note: Likely not the final implementation since the user currently can just join an association no questions asked
     *
     * @param hoaId the id of the association to be joined
     * @param id    the id of the user that wants to join
     * @return status on whether the joining was successful
     * @throws HoaDoesNotExistException
     * @throws UserDoesNotExistException
     */
    public UserHoa joinAssociation(String hoaName, String displayName, FullAddressModel address)
            throws HoaDoesNotExistException, UserDoesNotExistException {
        
        if (!userRepository.existsByDisplayName(displayName))
            throw new UserDoesNotExistException("User not in system!");
        
        Hoa hoa = hoaService.getByNaturalId(hoaName, address.getCountry(), address.getCity());

        if (hoa == null) 
            throw new HoaDoesNotExistException("Hoa with given attributes doesn't exits!");

        User user = userRepository.findByDisplayName(displayName);

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


}
