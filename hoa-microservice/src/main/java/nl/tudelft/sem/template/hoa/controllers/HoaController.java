package nl.tudelft.sem.template.hoa.controllers;

import java.util.List;
import java.util.stream.Collectors;

import nl.tudelft.sem.template.commons.models.hoa.CreateHoaModel;
import nl.tudelft.sem.template.commons.models.hoa.FullHoaResponseModel;
import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.hoa.services.HoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HoaController {

    private final transient HoaService hoaService;

    @Autowired
    public HoaController(HoaService hoaService) {
        this.hoaService = hoaService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<FullHoaResponseModel>> getAllHoas() {
        List<Hoa> hoas = hoaService.getAllHoas();
        return ResponseEntity.ok(hoas.stream().map(hoa -> {
            return hoa.toFullModel();
        }).collect(Collectors.toList()));
    }

    @PostMapping("/createHoa")
    public ResponseEntity<FullHoaResponseModel> createHoa(@RequestBody CreateHoaModel hoaModel) throws Exception {
        Hoa hoa = hoaService.createHoa(hoaModel.getName(), hoaModel.getCountry(), hoaModel.getCity());
        return ResponseEntity.ok(hoa.toFullModel());
    }

}
