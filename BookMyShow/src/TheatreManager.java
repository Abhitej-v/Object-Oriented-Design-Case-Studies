package OODPracticeExample.MovieTicketBooking;

import java.util.ArrayList;
import java.util.List;

public class TheatreManager {
    List<Theatre> affiliatedTheatres;
    public volatile static TheatreManager uniqueTheatreMngrInstance;

    private TheatreManager() {
    }

    public static TheatreManager getTheatreMngrInstance() {
        if (uniqueTheatreMngrInstance == null) {
            synchronized (TheatreManager.class) {
                if (uniqueTheatreMngrInstance == null) {
                    uniqueTheatreMngrInstance = new TheatreManager();
                }
            }
        }
        return uniqueTheatreMngrInstance;
    }

    public boolean addTheatre(Theatre theatre) {
        affiliatedTheatres.add(theatre);
        return true;
    }

    public ArrayList<Theatre> getTheatres(Movie movie) {
        ArrayList<Theatre> theatresOfMovie = new ArrayList<Theatre>();
        for (Theatre theatre : affiliatedTheatres) {
            if (theatre.hasMovie(movie)) {
                theatresOfMovie.add(theatre);
            }
        }
        return theatresOfMovie;
    }
}
