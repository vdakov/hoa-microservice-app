package nl.tudelft.sem.template.authentication.domain.user;

import org.springframework.stereotype.Service;

/**
 * A DDD service for registering a new user.
 */
@Service
public class RegistrationService {
    private final transient UserRepository userRepository;
    private final transient PasswordHashingService passwordHashingService;

    /**
     * Instantiates a new UserService.
     *
     * @param userRepository  the user repository
     * @param passwordHashingService the password encoder
     */
    public RegistrationService(UserRepository userRepository, PasswordHashingService passwordHashingService) {
        this.userRepository = userRepository;
        this.passwordHashingService = passwordHashingService;
    }

    /**
     * Register a new user.
     *
     * @param username    The Username of the user
     * @param password The password of the user
     * @throws Exception if the user already exists
     */
    public AppUser registerUser(Username username, Password password) throws Exception {

        if (checkUsernameIsUnique(username)) {
            // Hash password
            HashedPassword hashedPassword = passwordHashingService.hash(password);

            // Create new account
            AppUser user = new AppUser(username, hashedPassword);
            userRepository.save(user);

            return user;
        }

        throw new UsernameAlreadyInUseException(username);
    }

    public boolean checkUsernameIsUnique(Username username) {
        return !userRepository.existsByUsername(username);
    }

    /**
     * Changes a users password.
     *
     * @param username the username of the user to edit
     * @param newPass the new password
     * @return the user
     * @throws Exception if anything goes wrong
     */
    public AppUser changePassword(Username username, Password newPass) throws Exception {
        AppUser user = userRepository.findByUsername(username).orElseThrow();
        HashedPassword pwd = passwordHashingService.hash(newPass);
        user.changePassword(pwd);
        userRepository.delete(userRepository.findByUsername(username).orElseThrow());
        userRepository.saveAndFlush(user);
        return user;
    }
}
