package nl.tudelft.sem.template.authentication.domain.notification;

import nl.tudelft.sem.template.commons.entities.notification.Event;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private final transient NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
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
}
