package nl.tudelft.sem.template.authentication.controllers;


import com.fasterxml.jackson.core.type.TypeReference;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import nl.tudelft.sem.template.authentication.domain.notification.JsonUtil;
import nl.tudelft.sem.template.authentication.domain.notification.Notification;
import nl.tudelft.sem.template.authentication.domain.notification.NotificationService;
import nl.tudelft.sem.template.authentication.domain.user.AppUser;
import nl.tudelft.sem.template.authentication.domain.user.Username;
import nl.tudelft.sem.template.commons.entities.notification.CreateRequirementEvent;
import nl.tudelft.sem.template.commons.entities.notification.Event;
import nl.tudelft.sem.template.commons.models.notification.NewNotification;
import nl.tudelft.sem.template.commons.models.notification.NotificationCreateReq;
import nl.tudelft.sem.template.commons.models.notification.NotificationModel;
import nl.tudelft.sem.template.commons.models.notification.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private static final String AUTHORIZATION_LITERAL = "Authorization";

    private final transient NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey("exampleSecret").parseClaimsJws(token).getBody();
    }

    private AppUser parseUserFromToken() {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader(AUTHORIZATION_LITERAL);
        String username = getClaimFromToken(token.split(" ")[1], Claims::getSubject);
        var authenticationToken = new UsernamePasswordAuthenticationToken(username, null, List.of());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return notificationService.find(new Username(name));
    }

    /**
     * stupid javadoc
     * @return
     */
    @GetMapping("/whoami")
    public ResponseEntity whoami() {
        try {
            AppUser user = parseUserFromToken();
            if (user != null) {
                return ResponseEntity.ok("Hello World " + user.getUsername().toString());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ok");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * stupid javadoc
     * @return
     */
    @GetMapping("/getNotifications")
    public ResponseEntity<NotificationModel> getNotification() {
        try {
            if (parseUserFromToken() != null) {
                //TODO: maybe figure out a more efficient way to retrieve notifications
                List<Notification> notificationList = notificationService.getAll();
                List<Event> ret = new ArrayList<>();
                for (Notification n: notificationList) {

                    Map<String, Boolean> users = n.getUsers();
                    for (String user: users.keySet()) {
                        if (user.equals(parseUserFromToken().getUsername().toString()) && !users.get(user)) {
                            // check our user and markedRead
                            ret.add(n.getEvent());
                            break;
                        }
                    }
                }
                NotificationModel fin = new NotificationModel(ret);
                return ResponseEntity.ok(fin);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * LOL
     * @param body
     * @return
     */
    @PostMapping("/processNotification")
    public ResponseEntity processor(@RequestBody String body) throws Exception {
        System.out.println("I ARRIVED");
        System.out.println(body);
        //this is horrible
        String type = body.substring(body.indexOf("\"notificationType\":\"") + 20);
        type = type.substring(0, type.indexOf("\""));
        System.out.println(type);
        NotificationType notificationType = NotificationType.valueOf(type);

        System.out.println(body);
        if (notificationType == NotificationType.CREATE_REQUIREMENT) {
            TypeReference<NotificationCreateReq> typeReference = new TypeReference<>() {};
            NotificationCreateReq model = JsonUtil.deserialize(body, typeReference);
            CreateRequirementEvent cr = new CreateRequirementEvent();
            cr.setRequirementName(model.getRequirementName());
            cr.setRequirementDescription(model.getRequirementDescription());
            System.out.println("Saved");
            notificationService.createNotification(cr, model.getUsernames());
        }
        return null;
    }
}
