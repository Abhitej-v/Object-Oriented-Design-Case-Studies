package OODPracticeExample.ResturantManagementSystem;

import java.time.LocalDateTime;

public class Reserve {
    NotificationManager notificationManager;

    public boolean reserveTable(Customer customer, LocalDateTime bookingTime, Table table) {
        TableRepo tableRepo = TableRepo.getUniqueTableRepoInst();
        boolean ack = tableRepo.freezeTable(bookingTime, table);
        if (ack == true) {
            notificationManager.sendNotification(customer, "Reserved");
        }
        return ack;
    }

    public boolean cancelReservation(Customer customer, LocalDateTime bookingTime, Table table) {
        TableRepo tableRepo = TableRepo.getUniqueTableRepoInst();
        boolean ack = tableRepo.freeTable(bookingTime, table);
        if (ack == true) {
            notificationManager.sendNotification(customer, "Canceled");
        }
        return ack;
    }
}
