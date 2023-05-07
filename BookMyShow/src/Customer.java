package OODPracticeExample.MovieTicketBooking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Customer extends Person {
    City city;
    HashMap<Show, Ticket> activeBookings = new HashMap<Show, Ticket>();
    List<Coupon> coupons;

    public boolean setCity(City city) {
        this.city = city;
        return true;
    }

    public City getCity() {
        return city;
    }

    public ArrayList<Movie> searchMovie(Object searchParm, SearchType searchType) {
        Search search = SearchFactory.createSearchStrategy(searchType);
        return search.serchMovie(searchParm);
    }

    public ArrayList<Theatre> showTheatreOfMovie(Movie movie) {
        TheatreManager theatreManager = TheatreManager.getTheatreMngrInstance();
        return theatreManager.getTheatres(movie);
    }

    public ArrayList<Show> getAvailableShowsInTheatre(Theatre theatre, Movie movie) {
        return theatre.getAvailableShowsInTheatre(movie);
    }

    public boolean[][] viewSeatingArrangement(Show show) {
        return show.viewSeatingArrangement();
    }

    public boolean bookTicket(Show show, ArrayList<ShowSeat> showSeats, Coupon coupon) {
        Scanner sc = new Scanner(System.in);
        if (checkReqSeatAvailable(showSeats)) {
            changeSeatStatus(ShowSeatStatus.PendingPayment, showSeats);
        }
        Ticket ticket = new Ticket(show, showSeats, coupon);
        double price = ticket.getPrice();
        System.out.println(price + " is the price of booking, do you wish proceed to pay");
        // timer shall wait for response for 13 mins after which booking shall be cancelled and seats should be
        // again made available
        if (sc.nextBoolean() == true) {
            IPayment iPayment = new CreditCard(); // this should be provided by the customer
            ticket.payBill(iPayment);
        } else {
            ticket.setBookingStatus(BookingStatus.Cancelled);
            changeSeatStatus(ShowSeatStatus.Available, showSeats);
        }
        if (ticket.bookingStatus == BookingStatus.Confirmed) {
            // add the show and Ticket to the active bookings hashmap
            changeSeatStatus(ShowSeatStatus.Reserved, showSeats);
            return true;
        }
        return false;
    }

    private boolean checkReqSeatAvailable(ArrayList<ShowSeat> showSeats) {
        boolean flag = true;
        for (ShowSeat showSeat : showSeats) {
            if (showSeat.getShowSeatStatus() == ShowSeatStatus.Reserved ||
                    showSeat.getShowSeatStatus() == ShowSeatStatus.PendingPayment) {
                flag = false;
            }
        }
        return flag;
    }

    public void changeSeatStatus(ShowSeatStatus showSeatStatus, List<ShowSeat> showSeats) {
        for (ShowSeat showSeat : showSeats) {
            showSeat.setShowSeatStatus(showSeatStatus);
        }
    }

    public boolean cancelTicket(Ticket ticket) {
        // remove from activeBookings HashMap
        return ticket.cancel();
    }
}
