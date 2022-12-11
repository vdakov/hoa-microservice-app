package nl.tudelft.sem.template.hoa.controllers;

import nl.tudelft.sem.template.hoa.entitites.*;
import nl.tudelft.sem.template.hoa.services.UserService;
import nl.tudelft.sem.template.hoa.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final transient UserService userService;
    private final transient VoteService voteService;

    @Autowired
    public UserController(UserService userService, VoteService voteService) {
        this.userService = userService;
        this.voteService = voteService;
    }

    @GetMapping("/{displayName}")
    public ResponseEntity createNewUser(@PathVariable("displayName") String displayName){
        User a = new User(displayName);
        //implement stuff for checking whether user exists in service
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/submitVoteElection")
    public ResponseEntity submitVoteElection(@RequestBody Vote vote, @RequestBody User user, Hoa hoa){
        //VoteService.submit(vote,user)
        //needs functionality for checking whether user has already voted and whetehr user exists
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/changeVoteElection")
    public ResponseEntity changeVoteElection(@RequestBody Vote vote, @RequestBody User user, Hoa hoa){
        //VoteService.changeVote(vote,user)
        //needs functionality for checking whether user has already voted and whether user exists
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/submitVoteRequirement")
    public ResponseEntity submitVoteRequirement(@RequestBody RequirementVote vote,
                                                @RequestBody BoardMember boardMember){
        //VoteService.submit(vote,boardMember)
        //needs functionality for checking whether user has already voted and whether user exists
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/changeVoteElection")
    public ResponseEntity changeVoteRequirement(@RequestBody RequirementVote vote,
                                                @RequestBody BoardMember boardMember){
        //VoteService.changeVote(vote,boardMember)
        //needs functionality for checking whether user has already voted and whether user exists
        return ResponseEntity.status(HttpStatus.OK).build();
    }






}
