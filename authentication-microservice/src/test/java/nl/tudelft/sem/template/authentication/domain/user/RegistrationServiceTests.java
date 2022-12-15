package nl.tudelft.sem.template.authentication.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
// activate profiles to have spring use mocks during auto-injection of certain beans.
@ActiveProfiles({"test", "mockPasswordEncoder"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RegistrationServiceTests {

    @Autowired
    private transient RegistrationService registrationService;

    @Autowired
    private transient PasswordHashingService mockPasswordEncoder;

    @Autowired
    private transient UserRepository userRepository;

    @Test
    public void createUser_withValidData_worksCorrectly() throws Exception {
        // Arrange
        final Username testUser = new Username("SomeUser");
        final Password testPassword = new Password("password123");
        final HashedPassword testHashedPassword = new HashedPassword("hashedTestPassword");
        when(mockPasswordEncoder.hash(testPassword)).thenReturn(testHashedPassword);

        // Act
        registrationService.registerUser(testUser, testPassword);

        // Assert
        AppUser savedUser = userRepository.findByUsername(testUser).orElseThrow();

        assertThat(savedUser.getUsername()).isEqualTo(testUser);
        assertThat(savedUser.getPassword()).isEqualTo(testHashedPassword);
    }

    @Test
    public void createUser_withExistingUser_throwsException() {
        // Arrange
        final Username testUser = new Username("SomeUser");
        final HashedPassword existingTestPassword = new HashedPassword("password123");
        final Password newTestPassword = new Password("password456");

        AppUser existingAppUser = new AppUser(testUser, existingTestPassword);
        userRepository.save(existingAppUser);

        // Act
        ThrowingCallable action = () -> registrationService.registerUser(testUser, newTestPassword);

        // Assert
        assertThatExceptionOfType(Exception.class)
                .isThrownBy(action);

        AppUser savedUser = userRepository.findByUsername(testUser).orElseThrow();

        assertThat(savedUser.getUsername()).isEqualTo(testUser);
        assertThat(savedUser.getPassword()).isEqualTo(existingTestPassword);
    }

    @Test
    public void changePassword_success() {
        registrationService = new RegistrationService(userRepository, mockPasswordEncoder);

        Username username = new Username("SomeUser");
        HashedPassword oldPass = new HashedPassword("oldPassword");
        AppUser testUser = new AppUser(username, oldPass);
        userRepository.save(testUser);

        Password newPass = new Password("newPassword");
        HashedPassword newPassHashed = new HashedPassword("newPassword");
        when(mockPasswordEncoder.hash(newPass)).thenReturn(newPassHashed);
        AppUser updatedUser = registrationService.changePassword(username, newPass);

        assertThat(updatedUser.getPassword()).isEqualTo(newPassHashed);
    }

    @Test
    void changePassword_userDoesntExist_throwsException() {
        Username username = new Username("SomeUser");
        Password newPass = new Password("newPassword");
        HashedPassword newPassHashed = new HashedPassword("newPassword");
        when(mockPasswordEncoder.hash(newPass)).thenReturn(newPassHashed);

        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> {
            registrationService.changePassword(username, newPass);
        });
    }
}
