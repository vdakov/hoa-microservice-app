package nl.tudelft.sem.template.authentication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/gateway")
public class GatewayController {
    private final transient AuthenticationManager authManager;

    /**
     * Instantiates a new UsersController.
     */
    @Autowired
    public GatewayController(AuthenticationManager authManager){
        this.authManager = authManager;
    }

    public HttpEntity buildEntity(String token, Object body){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity<>(body, headers);
        return entity;
    }

    /**
     * Routing method used to retrieve activities for a certain HOA, if authorized.
     *
     */
    @GetMapping("/pnb/allActivities")
    public ResponseEntity viewActivities(){
        //Get bearer token
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization");
        System.out.println(token);

        //TODO: Define return type, define parameters (HOA).
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(null, token);
        String url = "http://localhost:8090/pnb/allActivities";
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        System.out.println(response);
        return ResponseEntity.ok().build();
    }

    /**
     * test.
     * @return ok.
     */
    @GetMapping("/users/castVoteElection/{UserID}/{HoaID}")
    public ResponseEntity test(@PathVariable("UserID") int user_id,
                               @PathVariable("HoaID") int hoa_id){
        //Get bearer token
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = buildEntity(null, token);
        String url = "http://localhost:8090/api/users/submitVoteElection/" + user_id + "/" + hoa_id;

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        System.out.println(response);
        return response;
    }
}
