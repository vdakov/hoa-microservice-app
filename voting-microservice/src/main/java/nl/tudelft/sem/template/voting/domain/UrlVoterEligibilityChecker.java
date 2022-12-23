package nl.tudelft.sem.template.voting.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import nl.tudelft.sem.template.commons.models.hoa.IsInHoaByIdRequestModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UrlVoterEligibilityChecker implements VoterEligibilityChecker {

    private final transient String url;
    private final transient int hoaId;


    /**
     * Makes a request to the HOA microservice to check if the given user is in that HOA
     * @param netId the username of the user
     * @return true if the user is in the HOA
     */
    public boolean isVoterEligible(String netId) {
        RestTemplate restTemplate = new RestTemplate();
        IsInHoaByIdRequestModel req = new IsInHoaByIdRequestModel(netId);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity<>(req, headers);
        ResponseEntity<Boolean> response =  restTemplate.exchange(url + Integer.toString(hoaId),
                HttpMethod.POST, entity, Boolean.class);
        return response.getBody();
    }
}
