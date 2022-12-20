package nl.tudelft.sem.template.hoa.controllers;

import java.util.List;

import nl.tudelft.sem.template.commons.models.ElectionResultsModel;
import nl.tudelft.sem.template.commons.models.RequirementResultsModel;
import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.commons.models.HoaModel;
import nl.tudelft.sem.template.hoa.entitites.User;
import nl.tudelft.sem.template.hoa.services.ClientService;
import nl.tudelft.sem.template.hoa.services.HoaService;
import nl.tudelft.sem.template.hoa.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HoaController {

    private final transient HoaService hoaService;
    private final transient ClientService client;
    private final transient VoteService votes;


    @Autowired
    public HoaController(HoaService hoaService, ClientService client, VoteService votes) {
        this.hoaService = hoaService;
        this.client = client;
        this.votes = votes;
    }


    @GetMapping("/world")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok().build();

    }

    //will have to be extended with inheritance
    @PostMapping("{hoaId}/receiveElectionResults")
    public ResponseEntity<String> getElectionResults(@RequestBody ElectionResultsModel results,
                                                     @PathVariable("hoaId") int hoaId) {
        this.votes.storeElectionResults(hoaId, results);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{hoaId}/receiveRequirementResults")
    public ResponseEntity<String> getRequirementResults(@RequestBody RequirementResultsModel results,
                                                        @PathVariable("hoaId") int hoaId) {
        this.votes.storeRequirementResults(hoaId, results);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{hoadId}/getListEligibleMembers")
    public ResponseEntity<List<User>> getListEligibleMembers() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Hoa>> getAllHoas() {
        return ResponseEntity.ok(hoaService.getAllHoas());
    }

    @PostMapping("/createHoa")
    public ResponseEntity<Hoa> createHoa(@RequestBody HoaModel hoaModel) throws Exception {
        Hoa hoa = hoaService.createHoa(hoaModel.getName(), hoaModel.getCountry(), hoaModel.getCity());
        return ResponseEntity.ok(hoa);
    }


}
