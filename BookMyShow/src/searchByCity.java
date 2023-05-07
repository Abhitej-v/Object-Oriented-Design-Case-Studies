package OODPracticeExample.MovieTicketBooking;

import java.util.ArrayList;

public class searchByCity implements Search {
    // usually this search happens on DB using a query on a central Movies List repository.
    @Override
    public ArrayList<Movie> serchMovie(Object city) {
        ArrayList<Movie> moviesList = new ArrayList<Movie>();
        return moviesList;
    }
}
