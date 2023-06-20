package OODPracticeExample.MovieTicketBooking;

import java.util.Date;

public class Show {
    String showId;
    Date movieTime;
    MovieHall movieHall;
    Movie movie;
    Theatre theatre;
    int leftOutSeats;
    ShowStatus showStatus;

    public void setShowStatus(ShowStatus showStatus) {
        this.showStatus = showStatus;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public boolean[][] viewSeatingArrangement() {
        return movieHall.getSeatingArrangement();
    }


    public Movie getMovie() {
        return this.movie;
    }

    public void decLeftOutSeats(int size) {
        leftOutSeats-=size;
    }

    public void incLeftOutSeats(int size) {
        leftOutSeats+=size;
    }
}
