package nl.tudelft.sem.template.authentication.domain.user;

import lombok.EqualsAndHashCode;

/**
 * A DDD value object representing a password in our domain.
 */
@EqualsAndHashCode
public class Password {
    private final transient String passwordValue;

    /**
     * Constructor.
     *
     * @param password the password.
     * @throws IllegalArgumentException if the password is null or too long.
     */
    public Password(String password) {
        // Validate input
        if (password == null || password.length() > 100) {
            throw new IllegalArgumentException("Your password is too long. I know, hard to believe.");
        }
        this.passwordValue = password;
    }

    @Override
    public String toString() {
        return passwordValue;
    }
}
