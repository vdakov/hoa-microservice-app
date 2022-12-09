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
     * @param email    The Email of the user
     * @param password The password of the user
     * @throws Exception if the user already exists
     */
    public AppUser registerUser(Email email, Password password) throws Exception {

        if (checkEmailIsUnique(email)) {
            // Hash password
            HashedPassword hashedPassword = passwordHashingService.hash(password);

            // Create new account
            AppUser user = new AppUser(email, hashedPassword);
            userRepository.save(user);

            return user;
        }

        throw new EmailAlreadyInUseException(email);
    }

    public boolean checkEmailIsUnique(Email email) {
        return !userRepository.existsByEmail(email);
    }

    /**
     * Changes a users password.
     *
     * @param email the email of the user to edit
     * @param password the new password
     * @return the user
     * @throws Exception if anything goes wrong
     */
    public AppUser changePassword(Email email, Password password, Password newPass) throws Exception {
        AppUser user = userRepository.findByEmail(email).orElseThrow();
        HashedPassword pwd = passwordHashingService.hash(password);
        user.changePassword(pwd);
        userRepository.delete(userRepository.findByEmail(email).orElseThrow());
        userRepository.saveAndFlush(user);
        return user;
    }
}
