package nl.tudelft.sem.template.authentication.domain.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A DDD repository for quering and persisting notifications.
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    @Override
    List<Notification> findAll();

    Notification findById(int id);
}

