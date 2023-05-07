package OODPracticeExample.MovieTicketBooking;

import java.util.ArrayList;

public class searchByTitle implements Search {
    // usually this search happens on DB using a query on a central Movies List repository.
    @Override
    public ArrayList<Movie> serchMovie(Object title) {
        ArrayList<Movie> moviesList = new ArrayList<Movie>();
        title = (String) title;
        Catalog catalog = Catalog.getCatalogUniqueInstance();
        ArrayList<Movie> listOfMovies = catalog.getActiveMovies();
        for (Movie movie : listOfMovies) {
            if (movie.title == title) {
                moviesList.add(movie);
            }
        }
        return moviesList;
    }
}
