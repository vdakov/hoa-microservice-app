package nl.tudelft.sem.template.hoa.services;


import com.fasterxml.jackson.core.type.TypeReference;
import nl.tudelft.sem.template.hoa.entitites.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;


import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Service
public class ClientService {

    private final RestTemplate restTemplate;
    @Autowired
    public ClientService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public void notifyUsers(List<User> users){

    }

    public void sendReport(){

    }





}
