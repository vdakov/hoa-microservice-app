package nl.tudelft.sem.template.authentication.domain.requests;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.Objects;

@Service
public class SendRequestService {

    private static final RestTemplate restTemplate = new RestTemplate();

    /**
     * Builds a standardized HTTP entity to pass along with HTTP requests
     *
     * @param body The body of the request, can be null.
     * @return an HttpEntity with the provided body
     */
    public ResponseEntity buildAndSend(String url, Object body, HttpMethod method) {
        String token = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest().getHeader("Authorization");

        //Remove the "bearer" from the beginning of the token.
        token = token.split(" ")[1];
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity(body, headers);

        return restTemplate.exchange(url, method, entity, Object.class);
    }
}
