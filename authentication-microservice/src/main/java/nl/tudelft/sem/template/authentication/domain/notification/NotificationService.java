package nl.tudelft.sem.template.authentication.domain.notification;

import nl.tudelft.sem.template.authentication.domain.user.AppUser;
import nl.tudelft.sem.template.authentication.domain.user.UserRepository;
import nl.tudelft.sem.template.authentication.domain.user.Username;
import nl.tudelft.sem.template.commons.entities.notification.Event;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    private final transient NotificationRepository notificationRepository;
    private final transient UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public Notification createNotification(Event event, List<String> usernames) throws Exception {
        Notification notification = new Notification(event, usernames);
        notificationRepository.save(notification);
        return notification;
    }

    public void deleteNotification(Notification notification) throws Exception {
        notificationRepository.delete(notification);
    }

    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

    public Notification findById(int id) {
        return notificationRepository.findById(id);
    }

    public AppUser find(Username username) {
        Optional<AppUser> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }
}
