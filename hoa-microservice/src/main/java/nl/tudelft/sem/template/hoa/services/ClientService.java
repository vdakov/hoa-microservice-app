package nl.tudelft.sem.template.hoa.services;


import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

@Service
public class ClientService {

    private final RestTemplate restTemplate;
    @Autowired
    public ClientService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public ResponseEntity<String> getVoteDistribution(int hoaId) {
        return restTemplate.getForEntity("http://localhost:8082/vote/hello", String.class);
    }

//    public Map<String,Integer> startElection(int hoaId) {
//        Map<String, Integer> response = restTemplate.getForObject("http://localhost:8082/vote/" + hoaId + "/getResults",
//                new ParameterizedTypeReference<Map<String, Integer>>() {});
//        return response;
//    }


}
