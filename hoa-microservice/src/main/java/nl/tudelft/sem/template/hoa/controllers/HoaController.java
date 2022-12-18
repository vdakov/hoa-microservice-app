package nl.tudelft.sem.template.hoa.controllers;

import java.util.List;

import nl.tudelft.sem.template.commons.models.ResultsModel;
import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.commons.models.HoaModel;
import nl.tudelft.sem.template.hoa.entitites.User;
import nl.tudelft.sem.template.hoa.services.ClientService;
import nl.tudelft.sem.template.hoa.services.HoaService;
import nl.tudelft.sem.template.hoa.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Hello World example controller.
 * <p>
 * This controller shows how you can extract information from the JWT token.
 * </p>
 */
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
    @PostMapping("{hoaId}/receiveResults")
    public ResponseEntity<String> getResults(@RequestBody ResultsModel results, @PathVariable("hoaId") int hoaId){
        this.votes.storeResults(hoaId,results);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{hoadId}/getListEligibleMembers")
    public ResponseEntity<List<User>> getListEligibleMembers(){
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
