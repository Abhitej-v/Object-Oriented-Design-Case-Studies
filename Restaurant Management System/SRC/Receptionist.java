package OODPracticeExample.ResturantManagementSystem;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Receptionist extends Employee {
    Search searchObj;
    Reserve reserveObj;

    public ArrayList<Table> getAvailTables(LocalDateTime bookingInfo) {
        return searchObj.getAvailTables(bookingInfo);
    }

    public boolean reserveTable(Customer customer, LocalDateTime bookingTime, Table table) {
        return reserveObj.reserveTable(customer, bookingTime, table);
    }

    public boolean cancelReservation(Customer customer, LocalDateTime bookingTime, Table table) {
        return reserveObj.cancelReservation(customer, bookingTime, table);
    }

    public boolean checkIn(Table table, LocalDateTime bookingTime, Customer customer) {
        TableRepo tableRepo = TableRepo.getUniqueTableRepoInst();
        return tableRepo.customerOccupied(bookingTime, table);
    }
}
