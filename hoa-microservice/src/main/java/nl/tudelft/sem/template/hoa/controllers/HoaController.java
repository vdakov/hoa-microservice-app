package nl.tudelft.sem.template.hoa.controllers;

import java.util.List;

import nl.tudelft.sem.template.commons.models.ElectionResultsModel;
import nl.tudelft.sem.template.commons.models.RequirementResultsModel;
import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.commons.models.HoaModel;
import nl.tudelft.sem.template.hoa.entitites.User;
import nl.tudelft.sem.template.hoa.services.HoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@RestController
public class HoaController {

    private final transient HoaService hoaService;

    @Autowired
    public HoaController(HoaService hoaService) {
        this.hoaService = hoaService;
    }


    public HttpEntity buildEntity(String token, Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity<>(body, headers);
        return entity;
    }

    /**
     * Test for sample routing to voting microservice
     */
    @GetMapping("/testRemote")
    public void testRemote() {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization");
        System.out.println(token);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(null, token);
        String url = "http://localhost:8082/hello";
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        System.out.println(response);
    }


    @GetMapping("/world")
    public ResponseEntity<String> helloWorld() {
        System.out.println("Hello");
        return ResponseEntity.ok().build();

    }

    @PostMapping("{hoaId}/receiveElectionResults")
    public ResponseEntity<String> getElectionResults(@RequestBody ElectionResultsModel results,
                                                     @PathVariable("hoaId") int hoaId) {
        hoaService.storeElectionResults(hoaId, results);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{hoaId}/receiveRequirementResults")
    public ResponseEntity<String> getRequirementResults(@RequestBody RequirementResultsModel results,
                                                        @PathVariable("hoaId") int hoaId) {
        hoaService.storeRequirementResults(hoaId, results);
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
