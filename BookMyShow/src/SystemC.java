package OODPracticeExample.MovieTicketBooking;

import java.util.ArrayList;

public class SystemC {
    ArrayList<Customer> subscribedCustomers;
    private volatile static SystemC uniqueSysInstance;

    private SystemC() {
    }

    public static SystemC getSysUniqueInstance() {
        if (uniqueSysInstance == null) {
            synchronized (SystemC.class) {
                if (uniqueSysInstance == null) {
                    uniqueSysInstance = new SystemC();
                }
            }
        }
        return uniqueSysInstance;
    }

    public void notifyNewMovie(Movie movie) {
        for (Customer customer : subscribedCustomers) {
            // notify it to all the customers who are present in subscribers list by fetching their contact info from DB
        }
    }

    public void notifyBooking(Customer customer, BookingStatus bookingStatus) {
        // fetch the Customer device type and its push notification ID from DB if it is android trigger Firebase APIs
        // if IOS the trigger APN APIs.
    }
}
