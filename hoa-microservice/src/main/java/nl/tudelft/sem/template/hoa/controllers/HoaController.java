package nl.tudelft.sem.template.hoa.controllers;

import java.util.List;
import java.util.stream.Collectors;

import nl.tudelft.sem.template.commons.models.hoa.HoaRequestModel;
import nl.tudelft.sem.template.commons.models.hoa.FullHoaResponseModel;
import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.hoa.services.HoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hoa")
public class HoaController {

    private final transient HoaService hoaService;

    @Autowired
    public HoaController(HoaService hoaService) {
        this.hoaService = hoaService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<FullHoaResponseModel>> getAllHoas() {
        List<Hoa> hoas = hoaService.getAllHoas();
        return ResponseEntity.ok(hoas.stream().map(Hoa::toFullModel).collect(Collectors.toList()));
    }

    @PostMapping("/createHoa")
    public ResponseEntity<FullHoaResponseModel> createHoa(@RequestBody HoaRequestModel hoaModel) throws Exception {
        if (hoaModel.anyNull())
            return ResponseEntity.badRequest().build();

        Hoa hoa = hoaService.createHoa(hoaModel.getName(), hoaModel.getCountry(), hoaModel.getCity());
        return ResponseEntity.ok(hoa.toFullModel());
    }

    @GetMapping("/find/{hoaId}")
    public ResponseEntity<Boolean> findHoa(@PathVariable("hoaId") int hoaId) {
        Hoa hoa = hoaService.getHoaById(hoaId);
        if (hoa != null) return ResponseEntity.ok(true);
        return ResponseEntity.ok(false);
    }

    @GetMapping("/getHoaModel/{hoaId}")
    public ResponseEntity<FullHoaResponseModel> getHoaModel(@PathVariable("hoaId") int hoaId) {
        Hoa hoa = hoaService.getHoaById(hoaId);
        if (hoa != null) {
            return ResponseEntity.ok(hoa.toFullModel());
        }
        return ResponseEntity.badRequest().build();
    }
}
