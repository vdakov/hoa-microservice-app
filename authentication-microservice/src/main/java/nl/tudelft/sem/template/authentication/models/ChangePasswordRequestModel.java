package nl.tudelft.sem.template.authentication.models;

import lombok.Data;

/**
 * Model representing a registration request.
 */
@Data
public class ChangePasswordRequestModel {
    private String email;
    private String password;
    private String newPassword;
}