package nl.tudelft.sem.template.voting.controllers;

import nl.tudelft.sem.template.voting.application.VotingService;
import nl.tudelft.sem.template.voting.authentication.AuthManager;
import nl.tudelft.sem.template.voting.domain.VotingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.time.Duration;
import java.util.List;

@RestController
public class VotingController {

    private VotingService votingService;
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

        boolean electionOngoing = votingService.existingHoaVoting(hoaId);
        if (!electionOngoing) {
            votingService.registerVotingStartingNow(hoaId, votingType, body, Duration.ofMinutes(1L));
            return ResponseEntity
                    .created(URI.create(String.format("/vote/%d", hoaId)))
                    .body(String.format("/vote/%d", hoaId));
        }
        return ResponseEntity
                .status(HttpStatus.SEE_OTHER)
                .body(String.format("/vote/%d", hoaId));
    }
    
}
