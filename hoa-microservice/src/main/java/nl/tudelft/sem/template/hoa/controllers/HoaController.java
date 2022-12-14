package nl.tudelft.sem.template.hoa.controllers;

import java.util.List;
import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.hoa.models.HoaModel;
import nl.tudelft.sem.template.hoa.services.HoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello World example controller.
 * <p>
 * This controller shows how you can extract information from the JWT token.
 * </p>
 */
@RestController
public class HoaController {

    private final transient HoaService hoaService;

    @Autowired
    public HoaController(HoaService hoaService) {
        this.hoaService = hoaService;
    }

    @GetMapping("/world")
    public ResponseEntity<String> helloWorld() {
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
