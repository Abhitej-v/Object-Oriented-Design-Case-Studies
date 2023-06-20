package OODPracticeExample.MovieTicketBooking;

public class Admin extends Person {
    public void addMovie(Movie movie) {
        Catalog catalog = Catalog.getCatalogUniqueInstance();
        catalog.addMovie(movie);
    }

    public void addShow(Theatre theatre) { // it should also receive all necessary info to create a show along with theatre.
        Show show = new Show();// pass the parameter necessary for a show like movie time, movie hall, movie etc...,
        theatre.addShow(show);
    }

    public boolean cancelShow(Show show) {
        ShowManager showManager=ShowManager.getShowMgrInstance();
        return showManager.cancelShow(show);
    }

    public boolean addTheatre(Theatre theatre) {
        TheatreManager theatreManager = TheatreManager.getTheatreMngrInstance();
        return theatreManager.addTheatre(theatre);
    }
}
