package nl.tudelft.sem.template.hoa.controllers;

import nl.tudelft.sem.template.hoa.entitites.*;
import nl.tudelft.sem.template.hoa.services.UserService;
import nl.tudelft.sem.template.hoa.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private VoteService voteService;


    @GetMapping("/{displayName}")
    public ResponseEntity<Boolean> createNewUser(@PathVariable("displayName") String displayName) {
        try {
            return ResponseEntity.ok(userService.saveUser(displayName));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }


    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }

    @PostMapping("/submitVoteElection/{userId}/{hoaId}")
    public ResponseEntity submitVoteElection(@RequestBody ElectionVote vote,
                                             @PathVariable("userId") int userId,
                                             @PathVariable("hoaId") int hoaId) {
        try {
            voteService.submitVoteElection(userId, vote, hoaId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }

    @PutMapping("/changeVoteElection")
    public ResponseEntity changeVoteElection(@RequestBody ElectionVote vote,
                                             @PathVariable("userId") int userId,
                                             @PathVariable("hoaId") int hoaId) {
        try {
            voteService.changeVoteElection(userId, vote, hoaId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }

    @PostMapping("/submitVoteRequirement/{userId}")
    public ResponseEntity submitVoteRequirement(@RequestBody RequirementVote vote,
                                                @PathVariable("userId") int boardMemberId) {
        try {
            voteService.submitVoteRequirement(boardMemberId, vote);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }

    @PutMapping("/changeVoteElection/{userId}")
    public ResponseEntity changeVoteRequirement(@RequestBody RequirementVote vote,
                                                @PathVariable("userId") int boardMemberId) {
        try {
            voteService.changeVoteRequirement(boardMemberId, vote);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }


}
