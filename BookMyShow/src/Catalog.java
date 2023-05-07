package OODPracticeExample.MovieTicketBooking;

import java.util.ArrayList;
import java.util.Set;

public class Catalog {
    Set<City> cities;
    ArrayList<Movie> activeMovies;
    private volatile static Catalog catalogUniqueInstance;

    private Catalog() {
    }

    public static Catalog getCatalogUniqueInstance() {
        if (catalogUniqueInstance == null) {
            synchronized (Catalog.class) {
                if (catalogUniqueInstance == null) {
                    catalogUniqueInstance = new Catalog();
                }
            }
        }
        return catalogUniqueInstance;
    }

    public void addMovie(Movie movie) {
        activeMovies.add(movie);
        notifySystem(movie);
    }

    public void notifySystem(Movie movie) {
        SystemC systemC = SystemC.getSysUniqueInstance();
        systemC.notifyNewMovie(movie);
    }

    public ArrayList<Movie> getActiveMovies() {
        return this.activeMovies;
    }
}
