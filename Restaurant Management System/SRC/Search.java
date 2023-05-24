package OODPracticeExample.ResturantManagementSystem;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Search {
    public ArrayList<Table> getAvailTables(LocalDateTime bookingInfo) {
        TableRepo tableRepo = TableRepo.getUniqueTableRepoInst();
        //ideally this search happens in DB with Query using filter as booking status and Date Time
        return tableRepo.getAvailTables(bookingInfo);
    }
}
