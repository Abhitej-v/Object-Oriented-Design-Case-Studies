package OODPracticeExample.MovieTicketBooking;

import java.util.ArrayList;

public class MovieHall {
    boolean[][] seatingArrangement;
    String hallNum;
    int totalSeats;
    ArrayList<MovieHallSeat> movieHallSeats;

    public boolean[][] getSeatingArrangement() {
        return seatingArrangement;
    }

    public String getHallNum() {
        return hallNum;
    }

    public int getTotalSeats() {
        return totalSeats;
    }
}
