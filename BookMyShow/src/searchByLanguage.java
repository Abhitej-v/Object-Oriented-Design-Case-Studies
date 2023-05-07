package OODPracticeExample.MovieTicketBooking;

import java.util.ArrayList;

public class searchByLanguage implements Search {
    // usually this search happens on DB using a query on a central Movies List repository.
    @Override
    public ArrayList<Movie> serchMovie(Object Language) {
        ArrayList<Movie> moviesList = new ArrayList<Movie>();
        Language = (String) Language;
        Catalog catalog = Catalog.getCatalogUniqueInstance();
        ArrayList<Movie> listOfMovies = catalog.getActiveMovies();
        for (Movie movie : listOfMovies) {
            if (movie.language == Language) {
                moviesList.add(movie);
            }
        }
        return moviesList;
    }
}
