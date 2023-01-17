package nl.tudelft.sem.template.requirements.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

/**
 * The Json util for tests.
 */
public class Util {
    /**
     * Serialize object into a string.
     *
     * @param object The object to be serialized.
     * @return A serialized string.
     * @throws JsonProcessingException if an error occurs during serialization.
     */
    public static String serialize(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    /**
     * This method will query the hoa microservice in order to check if the hoa exists
     * @param hoaId the id of the hoa
     * @return true/false
     */
    public static boolean hoaExists(int hoaId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = new HttpEntity(null, null);
        String url = "http://localhost:8090/hoa/find/" + hoaId;

        return Boolean.TRUE.equals(restTemplate.exchange(url, HttpMethod.GET, entity, Boolean.class).getBody());
    }

}