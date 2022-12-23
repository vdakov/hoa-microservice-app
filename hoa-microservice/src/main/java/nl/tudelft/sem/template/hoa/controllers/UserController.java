package nl.tudelft.sem.template.hoa.controllers;

import nl.tudelft.sem.template.commons.models.hoa.ConnectionRequestModel;
import nl.tudelft.sem.template.commons.models.hoa.FullAddressModel;
import nl.tudelft.sem.template.commons.models.hoa.FullUserHoaModel;
import nl.tudelft.sem.template.commons.models.hoa.FullUserResponseModel;
import nl.tudelft.sem.template.commons.models.hoa.JoinRequestModel;
import nl.tudelft.sem.template.hoa.entitites.User;
import nl.tudelft.sem.template.hoa.entitites.UserHoa;
import nl.tudelft.sem.template.hoa.exceptions.HoaDoesNotExistException;
import nl.tudelft.sem.template.hoa.exceptions.UserDoesNotExistException;
import nl.tudelft.sem.template.hoa.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private transient UserService userService;


    private static final String USER_ID_LITERAL = "userId";

    /**
     * GET endpoint for creating a new user
     *
     * @param displayName the name the user is initiated with
     * @return true if the creation is succesful and false if such a user already exists
     */
    @PostMapping("/createNewUser")
    public ResponseEntity<Boolean> createNewUser(@RequestBody String displayName) {
        try {
            userService.saveUser(displayName);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }


    }

    /**
     * GET mapping for all users in the system
     *
     * @return a list of all the users currently registered in the application
     */
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<FullUserResponseModel>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();

            return ResponseEntity.ok(
                users.stream().map(user -> {
                    return user.toFullModel();
                }).collect(Collectors.toList())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }

    /**
    * Allows a user to join an HOA (Homeowners Association).
    * @param joinRequest a request model containing the user and HOA IDs and the user's address
    * @return a ResponseEntity containing the newly created UserHoa connection object
    * @throws HoaDoesNotExistException if the specified HOA does not exist
    * @throws UserDoesNotExistException if the specified user does not exist
    */
    @PostMapping("joinHoa")
    public ResponseEntity<FullUserHoaModel> joinHoa(@RequestBody JoinRequestModel joinRequest) 
        throws HoaDoesNotExistException, UserDoesNotExistException {
        
        if (joinRequest.getUserDisplayName() == null || joinRequest.getHoaName() == null 
            || joinRequest.getCountry() == null || joinRequest.getCity() == null || joinRequest.getStreet() == null 
            || joinRequest.getHoaName() == null || joinRequest.getPostalCode() == null) {
            return ResponseEntity.badRequest().build();
        }

        FullAddressModel address = new FullAddressModel(
            joinRequest.getCountry(), joinRequest.getCity(), 
            joinRequest.getStreet(), joinRequest.getHouseNumber(), 
            joinRequest.getPostalCode()
        );
        
        UserHoa connection = this.userService.joinAssociation(
            joinRequest.getHoaName(), joinRequest.getUserDisplayName(), address
        );

        return ResponseEntity.ok().body(connection.toFullModel());
    }

    @PostMapping("isInHoa")
    public ResponseEntity<Boolean> isInHoa(@RequestBody ConnectionRequestModel req) {
        if (req.anyNull())
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(
            this.userService.isInHoa(req.getDisplayName(), req.getName(), req.getCountry(), req.getCity())
        );
    }


    @DeleteMapping("leaveHoa")
    public ResponseEntity<FullUserResponseModel> leaveHoa(
        @RequestBody ConnectionRequestModel request
    ) throws UserDoesNotExistException, HoaDoesNotExistException {
        if (request.anyNull()) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(this.userService.leaveAssociation(
            request.getDisplayName(), request.getName(), request.getCountry(), request.getCity()
        ).toFullModel());
    }

}
