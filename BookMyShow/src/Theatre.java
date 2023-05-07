package OODPracticeExample.MovieTicketBooking;

import java.util.ArrayList;

public class Theatre {
    ArrayList<Show> activeShows;
    ArrayList<Movie> runningMovies;
    String TheatreName;
    Address address;

    public void addShow(Show show) {
        if (runningMovies.contains(show.getMovie())) {
            runningMovies.add(show.getMovie());
        }
        activeShows.add(show);
    }

    public void removeShow(Show show) {
        activeShows.remove(show);
        // check if this the only show for the movie which is running in the theatre then remove it from runningMovies as well
    }

    public ArrayList<Show> getAvailableShowsInTheatre(Movie movie) {
        // go through the activeShows list and return all those movies that match the input parameter
        // or can maintain a movieShowsMap as well
        return new ArrayList<Show>();
    }

    public boolean hasMovie(Movie movie) {
        for (Movie rMovie : runningMovies) {
            if (rMovie == movie) {
                return true;
            }
        }
        return false;
    }
}
