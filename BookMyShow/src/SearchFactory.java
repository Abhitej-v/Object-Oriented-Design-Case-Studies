package OODPracticeExample.MovieTicketBooking;

public class SearchFactory {
    public static Search createSearchStrategy(SearchType searchType) {
        if (searchType == SearchType.Title) {
            return new searchByTitle();
        } else if (searchType == SearchType.Language) {
            return new searchByLanguage();
        } else {
            // so on for the rest of the strategies
            return new searchByGenre();
        }
    }
}
