package nl.tudelft.sem.template.voting.controllers;

import nl.tudelft.sem.template.voting.application.VotingService;
import nl.tudelft.sem.template.voting.authentication.AuthManager;
import nl.tudelft.sem.template.voting.domain.VotingException;
import nl.tudelft.sem.template.voting.domain.VotingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URI;
import java.time.Duration;
import java.util.List;

@RestController
public class VotingController {

    private final transient VotingService votingService;
    private final transient AuthManager authManager;

    @Autowired
    public VotingController(VotingService votingService, AuthManager authManager) {
        this.votingService = votingService;
        this.authManager = authManager;
    }

    /**
     * An endpoint to initialize a voting procedure
     * @param hoaId the ID of the HOA
     * @param votingType the type of the voting procedure
     * @param body a list of Strings that denotes the available options
     * @return an HTTP response
     */
    @PostMapping("/initializeVoting")
    public ResponseEntity<String> initializeVoting(@RequestParam int hoaId,
                                                   @RequestParam VotingType votingType,
                                                   @RequestBody List<String> body) {

        // TODO: check whether the respective user has the permission to initialize a vote
        boolean electionOngoing = votingService.existingHoaVoting(hoaId);
        if (!electionOngoing) {
            if (votingType.equals(VotingType.REQUIREMENTS_VOTE)) {
                return ResponseEntity
                        .status(HttpStatus.NOT_IMPLEMENTED)
                        .body("");
            }
            votingService.registerVotingStartingNow(hoaId, votingType, body, Duration.ofMinutes(1L));
            return ResponseEntity
                    .created(URI.create(String.format("/vote/%d", hoaId)))
                    .body(String.format("/vote/%d", hoaId));

        }
        return ResponseEntity
                .status(HttpStatus.SEE_OTHER)
                .body(String.format("/vote/%d", hoaId));
    }

    /**
     * An endpoint to get the list of options to vote for
     * @param hoaId the ID of the HOA in which a vote is conducted
     * @return the list of options
     */
    @GetMapping("/vote/{hoaId}/getOptions")
    public ResponseEntity<List<String>> getOptions(@PathVariable int hoaId) {

        if (!votingService.existingHoaVoting(hoaId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(votingService.getOptions(hoaId));
    }

    /**
     * A request to cast a vote
     * @param hoaId the HOA where a vote is currently running
     * @param optionIndex the index of the chosen option
     * @return -
     */
    @PostMapping("/vote/{hoaId}/castVote")
    public ResponseEntity<Void> castVote(@PathVariable int hoaId,
                                            @RequestParam int optionIndex) {

        if (!votingService.existingHoaVoting(hoaId)) {
            return ResponseEntity.notFound().build();
        }
        try {
            String netId = authManager.getNetId();
            votingService.castVote(hoaId, netId, optionIndex);
            return ResponseEntity.ok().build();
        } catch (VotingException e) {
            if (e.getMessage().equals("Voter is not eligible")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            } else if (e.getMessage().equals("Chosen option index is invalid")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
