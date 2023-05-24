package OODPracticeExample.ResturantManagementSystem;

public class NotificationManager {
    public void sendNotification(Customer customer, String message) {
        // this fetching can also happen in DB tables by implementing an ORM design
        String emailID = customer.getEmailId();
        // use this Email ID trigger Gmail API to send the notification
    }
}
