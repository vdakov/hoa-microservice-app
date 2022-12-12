package nl.tudelft.sem.template.authentication.domain.user;

import lombok.EqualsAndHashCode;

/**
 * A DDD value object representing a Username in our domain.
 */
@EqualsAndHashCode
public class Username {
    private final transient String usernameValue;

    /**
     * Constructor.
     *
     * @param username the username.
     * @throws IllegalArgumentException if the username is null or too long.
     */
    public Username(String username) {
        // validate Username
        if (username == null || username.length() > 50) {
            throw new IllegalArgumentException("Invalid username. Try using a shorter username.");
        }
        this.usernameValue = username;
    }

    @Override
    public String toString() {
        return usernameValue;
    }
}
