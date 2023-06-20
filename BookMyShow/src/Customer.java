package OODPracticeExample.MovieTicketBooking;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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

    public ArrayList<String> getAvailableShowsInTheatre(Theatre theatre, Movie movie) {
        return theatre.getAvailableShowsInTheatre(movie);
    }

    public boolean[][] viewSeatingArrangement(Show show) {
        return show.viewSeatingArrangement();
    }

    public boolean bookTicket(MetaData metaData) {
        return TicketManager.validateAndBook(metaData);
    }

    public boolean cancelTicket(Ticket ticket) {
        // remove from activeBookings HashMap
        return ticket.cancel();
    }
}
