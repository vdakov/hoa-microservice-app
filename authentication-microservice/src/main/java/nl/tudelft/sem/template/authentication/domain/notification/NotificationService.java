package nl.tudelft.sem.template.authentication.domain.notification;

import nl.tudelft.sem.template.authentication.domain.user.AppUser;
import nl.tudelft.sem.template.authentication.domain.user.RegistrationService;
import nl.tudelft.sem.template.authentication.domain.user.UserRepository;
import nl.tudelft.sem.template.authentication.domain.user.Username;
import nl.tudelft.sem.template.commons.entities.notification.Event;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    private final transient NotificationRepository notificationRepository;
    private final transient RegistrationService registrationService;

    public NotificationService(NotificationRepository notificationRepository, RegistrationService registrationService) {
        this.notificationRepository = notificationRepository;
        this.registrationService = registrationService;
    }

    public Notification createNotification(Event event, List<String> usernames) throws Exception {
        Notification notification = new Notification(event, usernames);
        notificationRepository.save(notification);
        return notification;
    }

    public Notification deleteNotification(Notification notification) throws Exception {
        notificationRepository.delete(notification);
        return notification;
    }

    public Notification updateNotification(Notification notification) throws Exception {
        notificationRepository.save(notification);
        return notification;
    }

    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

    public Notification findById(int id) {
        return notificationRepository.findById(id);
    }

    public AppUser find(Username username) {
        return registrationService.find(username);
    }
}
