package nl.tudelft.sem.template.hoa.controllers;

import nl.tudelft.sem.template.hoa.entitites.ElectionVote;
import nl.tudelft.sem.template.hoa.entitites.RequirementVote;
import nl.tudelft.sem.template.hoa.entitites.User;
import nl.tudelft.sem.template.hoa.services.UserService;
import nl.tudelft.sem.template.hoa.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private transient UserService userService;
    @Autowired
    private transient VoteService voteService;

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
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }

    /**
     * Mapping to submit a vote in an election
     * <p>
     * The mapping gets the Hoa and User as id's in the path (whether it should be
     * in the path is up to debate) to avoid transfering that much heavy data like objects
     *
     * @param vote   the vote submitted
     * @param userId id of the user submitting the vote
     * @param hoaId  id of the association the user is submitting their vote for (since they canbe a member of multiple
     *               associations
     * @return status of whether the submission succeeded
     */
    @PostMapping("/submitVoteElection/{userId}/{hoaId}")
    public ResponseEntity submitVoteElection(@RequestBody ElectionVote vote,
                                             @PathVariable(USER_ID_LITERAL) int userId,
                                             @PathVariable("hoaId") int hoaId) {
        try {
            voteService.submitVoteElection(userId, vote, hoaId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }

    /**
     * PUT mappping to change the user's vote in an election (PUT since it is just an update)
     *
     * @param vote   the vote submitted
     * @param userId id of the user submitting the vote
     * @param hoaId  id of the association the user is submitting their vote for (since they canbe a member of multiple
     *               associations
     * @return status of whether the submission succeeded
     */
    @PutMapping("/changeVoteElection/{userId}/{hoaId}")
    public ResponseEntity changeVoteElection(@RequestBody ElectionVote vote,
                                             @PathVariable(USER_ID_LITERAL) int userId,
                                             @PathVariable("hoaId") int hoaId) {
        try {
            voteService.changeVoteElection(userId, vote, hoaId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }

    /**
     * Analogous POST mapping for requirement voting
     */
    @PostMapping("/submitVoteRequirement/{userId}")
    public ResponseEntity submitVoteRequirement(@RequestBody RequirementVote vote,
                                                @PathVariable(USER_ID_LITERAL) int boardMemberId) {
        try {
            voteService.submitVoteRequirement(boardMemberId, vote);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }

    /**
     * Analogous PUT mapping for requirement voting
     */
    @PutMapping("/changeVoteElection/{userId}")
    public ResponseEntity changeVoteRequirement(@RequestBody RequirementVote vote,
                                                @PathVariable(USER_ID_LITERAL) int boardMemberId) {
        try {
            voteService.changeVoteRequirement(boardMemberId, vote);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }


}
