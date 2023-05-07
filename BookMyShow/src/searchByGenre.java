package OODPracticeExample.MovieTicketBooking;

import java.util.ArrayList;

public class searchByGenre implements Search {
    // usually this search happens on DB using a query on a central Movies List repository.
    @Override
    public ArrayList<Movie> serchMovie(Object genre) {
        ArrayList<Movie> moviesList = new ArrayList<Movie>();
        genre = (String) genre;
        Catalog catalog = Catalog.getCatalogUniqueInstance();
        ArrayList<Movie> listOfMovies = catalog.getActiveMovies();
        for (Movie movie : listOfMovies) {
            if (movie.genre == genre) {
                moviesList.add(movie);
            }
        }
        return moviesList;
    }
}
