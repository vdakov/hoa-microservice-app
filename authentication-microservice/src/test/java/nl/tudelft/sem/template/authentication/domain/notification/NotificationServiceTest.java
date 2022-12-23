package nl.tudelft.sem.template.authentication.domain.notification;

import nl.tudelft.sem.template.authentication.domain.user.AppUser;
import nl.tudelft.sem.template.authentication.domain.user.HashedPassword;
import nl.tudelft.sem.template.authentication.domain.user.RegistrationService;
import nl.tudelft.sem.template.authentication.domain.user.Username;
import nl.tudelft.sem.template.commons.entities.notification.CreateRequirementEvent;
import nl.tudelft.sem.template.commons.entities.notification.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class NotificationServiceTest {

    private NotificationRepository notificationRepository;

    private NotificationService notificationService;

    Event event;
    Notification notification;
    List<String> usernames;

    @BeforeEach
    public void setup() {
        notificationRepository = mock(NotificationRepository.class);
        RegistrationService registrationService = mock(RegistrationService.class);
        notificationService = new NotificationService(notificationRepository, registrationService);
        event = new CreateRequirementEvent();
        ((CreateRequirementEvent) event).setRequirementName("Parking rules");
        ((CreateRequirementEvent) event).setRequirementDescription("Park in front of your house only");
        usernames = new ArrayList<>();
        usernames.add("Frank");
        usernames.add("Ben");
    }

    @Test
    void createNotification() throws Exception {
        notification = notificationService.createNotification(event, usernames);
        verify(notificationRepository, times(1)).save(notification);
    }

    @Test
    void deleteNotification() throws Exception {
        notification = notificationService.createNotification(event, usernames);
        notification = notificationService.deleteNotification(notification);
        verify(notificationRepository, times(1)).delete(notification);
    }

    @Test
    void updateNotification() throws Exception {
        notification = notificationService.createNotification(event, usernames);
        verify(notificationRepository, times(1)).save(notification);
        notification.setId(100);
        notification = notificationService.updateNotification(notification);
        verify(notificationRepository, times(2)).save(notification);
    }

    @Test
    void getAll() throws Exception {
        List<String> usernames2 = new ArrayList<>();
        usernames2.add("Martin");
        usernames2.add("Luther");
        notification = notificationService.createNotification(event, usernames);
        verify(notificationRepository, times(1)).save(notification);
        Notification notification2 = notificationService.createNotification(event, usernames2);
        verify(notificationRepository, times(1)).save(notification2);
        List<String> usernames3 = new ArrayList<>();
        usernames2.add("Liam");
        usernames2.add("Joe");
        Notification notification3 = notificationService.createNotification(event, usernames3);
        verify(notificationRepository, times(1)).save(notification3);
        List<Notification> notificationList = List.of(notification, notification2, notification3);
        when(notificationService.getAll()).thenReturn(notificationList);
        assertEquals(notificationList, notificationService.getAll());
    }

    @Test
    void findById() {
        notification = new Notification(event, usernames);
        when(notificationService.findById(2)).thenReturn(notification);
        assertEquals(notification, notificationService.findById(2));
    }

    @Test
    void find() {
        Username username = new Username("Ben");
        AppUser user = new AppUser(username, new HashedPassword("hash"));

        when(notificationService.find(username)).thenReturn(user);
        assertEquals(user, notificationService.find(username));
    }
}