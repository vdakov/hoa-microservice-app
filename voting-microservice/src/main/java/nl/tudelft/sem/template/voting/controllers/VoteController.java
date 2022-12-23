package nl.tudelft.sem.template.voting.controllers;

import nl.tudelft.sem.template.commons.models.ElectionResultsModel;
import nl.tudelft.sem.template.commons.models.VotingModel;
import nl.tudelft.sem.template.voting.application.VotingService;
import nl.tudelft.sem.template.voting.domain.VotingException;
import nl.tudelft.sem.template.commons.models.VotingType;
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
import java.time.Instant;
import java.util.List;


@RestController
public class VoteController {

    private final transient VotingService votingService;

    @Autowired
    public VoteController(VotingService votingService) {
        this.votingService = votingService;
    }

    /**
     * An endpoint to initialize a voting procedure
     * @param votingModel DTO containing information used to start a vote
     * @return an HTTP response
     */
    @PostMapping("/initializeVoting")
    public ResponseEntity<String> initializeVoting(@RequestBody VotingModel votingModel) {
        // TODO: check whether the respective user has the permission to initialize a vote
        boolean electionOngoing = votingService.existingHoaVoting(votingModel.getHoaId());
        if (!electionOngoing) {
            if (votingModel.getVotingType().equals(VotingType.REQUIREMENTS_VOTE)) {
                return ResponseEntity
                        .status(HttpStatus.NOT_IMPLEMENTED)
                        .body("");
            }
            votingService.registerVoteStartingNow(votingModel, Duration.ofMinutes(1L));
            return ResponseEntity
                    .created(URI.create(String.format("/vote/%d", votingModel.getHoaId())))
                    .body(String.format("/vote/%d", votingModel.getHoaId()));

        }
        return ResponseEntity
                .status(HttpStatus.SEE_OTHER)
                .body(String.format("/vote/%d", votingModel.getHoaId()));
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
    @PostMapping("/vote/{hoaId}/castVote/{userName}")
    public ResponseEntity<Void> castVote(@PathVariable int hoaId,
                                            @RequestBody int optionIndex,
                                         @PathVariable String userName) {

        if (!votingService.existingHoaVoting(hoaId)) {
            return ResponseEntity.notFound().build();
        }
        try {
            String netId = userName;
            votingService.castVote(hoaId, netId, optionIndex);
            return ResponseEntity.ok().build();
        } catch (VotingException e) {
            if (e.getMessage().equals("Voter is not eligible")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            } else if (e.getMessage().equals("Chosen option index is invalid")
                    || e.getMessage().equals("Vote is still ongoing")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/vote/{hoaId}/getEndTime")
    public ResponseEntity<Instant> getEndTime(@PathVariable int hoaId) {

        if (!votingService.existingHoaVoting(hoaId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(votingService.getEndTime(hoaId));
    }

}
