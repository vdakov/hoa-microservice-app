package nl.tudelft.sem.template.authentication.models;

import lombok.Data;

/**
 * Model representing a registration request.
 */
@Data
public class RegistrationRequestModel {
    private String username;
    private String password;
}