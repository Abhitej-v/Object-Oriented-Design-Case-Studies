package OODPracticeExample.MovieTicketBooking;

import java.util.ArrayList;
import java.util.Date;

public class searchByReleaseDate implements Search {
    // usually this search happens on DB using a query on a central Movies List repository.
    @Override
    public ArrayList<Movie> serchMovie(Object date) {
        ArrayList<Movie> moviesList = new ArrayList<Movie>();
        date = (Date) date;
        Catalog catalog = Catalog.getCatalogUniqueInstance();
        ArrayList<Movie> listOfMovies = catalog.getActiveMovies();
        for (Movie movie : listOfMovies) {
            if (movie.realeaseDate == date) {
                moviesList.add(movie);
            }
        }
        return moviesList;
    }
}
