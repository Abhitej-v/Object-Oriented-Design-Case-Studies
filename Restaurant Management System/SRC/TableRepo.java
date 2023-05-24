package OODPracticeExample.ResturantManagementSystem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TableRepo {
    HashMap<LocalDateTime, HashMap<Table, BookingStatus>> tableBookingMap = new HashMap<LocalDateTime, HashMap<Table, BookingStatus>>();
    ArrayList<Table> tablesInRestaurant = new ArrayList<Table>();
    private volatile static TableRepo tableRepoUnqInst;

    public boolean freezeTable(LocalDateTime bookingTime, Table table) {
        if (tableBookingMap.get(bookingTime).get(table) == BookingStatus.Available) {
            tableBookingMap.get(bookingTime).put(table, BookingStatus.Reserved);
            // It can have additional column in DB which stores customer object indicating who booked the table slot
            return true;
        } else {
            return false;
        }
    }

    public boolean freeTable(LocalDateTime bookingTime, Table table) {
        tableBookingMap.get(bookingTime).put(table, BookingStatus.Available);
        return true;
    }

    public boolean customerOccupied(LocalDateTime bookingTime, Table table) {
        // can add an additional check, if reserved table is reserved by the check in customer or not.
        if (tableBookingMap.get(bookingTime).get(table) == BookingStatus.Available || tableBookingMap.get(bookingTime).get(table) == BookingStatus.Reserved) {
            tableBookingMap.get(bookingTime).put(table, BookingStatus.Occupied);
            return true;
        } else {
            return false;
        }
    }

    private TableRepo() {
    }

    public static TableRepo getUniqueTableRepoInst() {
        if (tableRepoUnqInst == null) {
            synchronized (TableRepo.class) {
                if (tableRepoUnqInst == null) {
                    tableRepoUnqInst = new TableRepo();
                }
            }
        }
        return tableRepoUnqInst;
    }

    public ArrayList<Table> getAvailTables(LocalDateTime bookingInfo) {
        ArrayList<Table> availTables = new ArrayList<Table>();
        HashMap<Table, BookingStatus> tableBookingDateMap = tableBookingMap.get(bookingInfo);
        //ideally this search happens in DB with Query using filter as booking status
        for (Map.Entry<Table, BookingStatus> entry : tableBookingDateMap.entrySet()) {
            Table table = entry.getKey();
            BookingStatus bookingStatus = entry.getValue();
            if (bookingStatus == BookingStatus.Available) {
                availTables.add(table);
            }
        }
        return availTables;
    }

    public void addTable(Table table) {
        tablesInRestaurant.add(table);
    }
}
