package nl.tudelft.sem.template.hoa.services;


import nl.tudelft.sem.template.commons.models.VotingModel;
import nl.tudelft.sem.template.hoa.entitites.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class ClientService {

    private final RestTemplate restTemplate;

    @Autowired
    public ClientService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public void notifyUsers(List<User> users) {

    }

    public void startVote(VotingModel vote) {
        restTemplate.postForEntity("http://localhost:8082/initializeVoting", vote, VotingModel.class);
    }

    public void test() {
        restTemplate.getForEntity("http://localhost:8090/world", String.class);
    }


    public void sendReport() {

    }


}
