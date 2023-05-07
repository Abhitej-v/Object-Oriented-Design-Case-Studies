package OODPracticeExample.MovieTicketBooking;

import java.util.ArrayList;
import java.util.Date;

public class Show {
    Date movieTime;
    MovieHall movieHall;
    ArrayList<ShowSeat> showSeats = new ArrayList<ShowSeat>();
    ArrayList<Ticket> ticketsOfShow = new ArrayList<Ticket>();
    Movie movie;
    Theatre theatre;
    boolean isHouseFull;
    int leftOutSeats;
    ShowStatus showStatus;

    public boolean cancelShow() {
        // check if show hasn't yet started and iterate through all the tickets in ticketsOfShow and trigger
        // cancelTicket() on them.
        // cancel method notifies system, system notifies customer
        for (Ticket ticket : ticketsOfShow) {
            ticket.cancel();
        }
        showStatus = ShowStatus.Cancelled;
        theatre.removeShow(this);
        return true;
    }

    public void addTicketsOfShow(Ticket ticket, int seatCount) {
        ticketsOfShow.add(ticket);
        // get the num of seats booked decrement leftOutSeats, once it reaches 0 make isHousefull to true.
    }

    public boolean[][] viewSeatingArrangement() {
        return movieHall.getSeatingArrangement();
    }

    public void removeTicketOfShow(Ticket ticket, int size) {
        ticketsOfShow.remove(ticket);
        // get the num of seats cancelled and increment leftOutSeats.
    }

    public Movie getMovie() {
        return this.movie;
    }

}
