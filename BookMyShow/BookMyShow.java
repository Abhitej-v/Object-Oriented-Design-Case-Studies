package OODPracticeExample.MovieTicketBooking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.*;

// Necessary Enums
public enum PaymentStatus {
    Paid,
    Pending,
    Abandoned,
    Failed,
    Cancelled,
    RefundRequested,
    Refunded,
    RefundRejected
}

public enum BookingStatus{
    Confirmed,
    Pending,
    Cancelled,
    Requested,
    Showed_up,
    Failed
}

public enum SeatType{
    Recliner,
    Premium,
    Slider
}

public enum SearchType{
    Language,
    Genre,
    City,
    Date,
    Title
}

public enum CouponStatus{
    Expired,
    Redeemed,
    Active,
    Disabled
}

public enum ShowStatus{
    YetToStart,
    Started,
    ShowEnded,
    Cancelled
}

public enum ShowSeatStatus{
    Reserved,
    Available,
    PendingPayment
}

// classes start here
public class Movie{
    String title;
    String language;
    String genre;
    Date realeaseDate;
    double movieDuration;
    String movieCertificate;
}
public class Coupon{
    int discountPercentage;
    int maxLimit;
    Customer customer;
    CouponStatus couponStatus;
}

public class City{
    String state;
    String country;
    String zipCode;
}

public class Address{
    String streetName;
    String locality;
    City city;
    String propertyNum;
}

public class MovieHall{
    boolean[][] seatingArrangement;
    String hallNum;
    int totalSeats;
    ArrayList<MovieHallSeat> movieHallSeats;

    public boolean[][] getSeatingArrangement() {
        return seatingArrangement;
    }

    public String getHallNum() {
        return hallNum;
    }

    public int getTotalSeats() {
        return totalSeats;
    }
}
public class MovieHallSeat{
    int rowNum;
    int colNum;
    SeatType seatType;
}

public abstract class Person {
    String username;
    String password;
    public boolean resetPassword(){
        return true;
    }
}


public class Admin extends Person{
    public void addMovie(Movie movie){
        Catalog catalog=Catalog.getCatalogUniqueInstance();
        catalog.addMovie(movie);
    }
    public void addShow(Theatre theatre){ // it should also receive all necessary info to create a show along with theatre.
        Show show=new Show();// pass the parameter necessary for a show like movie time, movie hall, movie etc...,
        theatre.addShow(show);
    }
    public boolean cancelShow(Show show){
        return show.cancelShow();
    }
    public boolean addTheatre(Theatre theatre){
        TheatreManager theatreManager = TheatreManager.getTheatreMngrInstance();
        return theatreManager.addTheatre(theatre);
    }
}

public class Customer extends Person{
    City city;
    HashMap<Show,Ticket> activeBookings=new HashMap<Show, Ticket>();
    List<Coupon> coupons;

    public boolean setCity(City city) {
        this.city = city;
        return true;
    }
    public City getCity() {
        return city;
    }
    public ArrayList<Movie> searchMovie(Object searchParm, SearchType searchType){
        Search search= SearchFactory.createSearchStrategy(searchType);
        return search.serchMovie(searchParm);
    }
    public ArrayList<Theatre> showTheatreOfMovie(Movie movie){
        TheatreManager theatreManager=TheatreManager.getTheatreMngrInstance();
        return theatreManager.getTheatres(movie);
    }

    public ArrayList<Show> getAvailableShowsInTheatre(Theatre theatre, Movie movie){
        return theatre.getAvailableShowsInTheatre(movie);
    }
    public boolean[][] viewSeatingArrangement(Show show){
        return show.viewSeatingArrangement();
    }
    public boolean bookTicket(Show show, ArrayList<ShowSeat> showSeats, Coupon coupon){
        Scanner sc=new Scanner(System.in);
        if(checkReqSeatAvailable(showSeats)){
            changeSeatStatus(ShowSeatStatus.PendingPayment, showSeats);
        }
        Ticket ticket = new Ticket(show, showSeats, coupon);
        double price=ticket.getPrice();
        System.out.println(price+" is the price of booking, do you wish proceed to pay");
        // timer shall wait for response for 13 mins after which booking shall be cancelled and seats should be
        // again made available
        if(sc.nextBoolean()==true){
            IPayment iPayment = new CreditCard(); // this should be provided by the customer
            ticket.payBill(iPayment);
        }
        else {
            ticket.setBookingStatus(BookingStatus.Cancelled);
            changeSeatStatus(ShowSeatStatus.Available, showSeats);
        }
        if(ticket.bookingStatus==BookingStatus.Confirmed){
            // add the show and Ticket to the active bookings hashmap
            changeSeatStatus(ShowSeatStatus.Reserved, showSeats);
            return true;
        }
        return false;
    }

    private boolean checkReqSeatAvailable(ArrayList<ShowSeat> showSeats) {
        boolean flag=true;
        for (ShowSeat showSeat: showSeats) {
            if(showSeat.getShowSeatStatus()==ShowSeatStatus.Reserved ||
                    showSeat.getShowSeatStatus()==ShowSeatStatus.PendingPayment){
                flag=false;
            }
        }
        return flag;
    }

    public void changeSeatStatus(ShowSeatStatus showSeatStatus, List<ShowSeat> showSeats){
        for (ShowSeat showSeat: showSeats) {
            showSeat.setShowSeatStatus(showSeatStatus);
        }
    }
    public boolean cancelTicket(Ticket ticket){
        // remove from activeBookings HashMap
        return ticket.cancel();
    }
}

public interface Search{
    ArrayList<Movie> serchMovie(Object searchParam);
}

public class searchByTitle implements Search{
    // usually this search happens on DB using a query on a central Movies List repository.
    @Override
    public ArrayList<Movie> serchMovie(Object title) {
        ArrayList<Movie> moviesList=new ArrayList<Movie>();
        title=(String) title;
        Catalog catalog=Catalog.getCatalogUniqueInstance();
        ArrayList<Movie> listOfMovies=catalog.getActiveMovies();
        for(Movie movie: listOfMovies){
            if(movie.title==title){
                moviesList.add(movie);
            }
        }
        return moviesList;
    }
}
public class searchByGenre implements Search{
    // usually this search happens on DB using a query on a central Movies List repository.
    @Override
    public ArrayList<Movie> serchMovie(Object genre) {
        ArrayList<Movie> moviesList=new ArrayList<Movie>();
        genre=(String) genre;
        Catalog catalog=Catalog.getCatalogUniqueInstance();
        ArrayList<Movie> listOfMovies=catalog.getActiveMovies();
        for(Movie movie: listOfMovies){
            if(movie.genre==genre){
                moviesList.add(movie);
            }
        }
        return moviesList;
    }
}
public class searchByLanguage implements Search{
    // usually this search happens on DB using a query on a central Movies List repository.
    @Override
    public ArrayList<Movie> serchMovie(Object Language) {
        ArrayList<Movie> moviesList=new ArrayList<Movie>();
        Language=(String) Language;
        Catalog catalog=Catalog.getCatalogUniqueInstance();
        ArrayList<Movie> listOfMovies=catalog.getActiveMovies();
        for(Movie movie: listOfMovies){
            if(movie.language==Language){
                moviesList.add(movie);
            }
        }
        return moviesList;
    }
}
public class searchByCity implements Search{
    // usually this search happens on DB using a query on a central Movies List repository.
    @Override
    public ArrayList<Movie> serchMovie(Object city) {
        ArrayList<Movie> moviesList=new ArrayList<Movie>();
        return moviesList;
    }
}
public class searchByReleaseDate implements Search{
    // usually this search happens on DB using a query on a central Movies List repository.
    @Override
    public ArrayList<Movie> serchMovie(Object date) {
        ArrayList<Movie> moviesList=new ArrayList<Movie>();
        date=(Date) date;
        Catalog catalog=Catalog.getCatalogUniqueInstance();
        ArrayList<Movie> listOfMovies=catalog.getActiveMovies();
        for(Movie movie: listOfMovies){
            if(movie.realeaseDate==date){
                moviesList.add(movie);
            }
        }
        return moviesList;
    }
}

public class SearchFactory{
    public static Search createSearchStrategy(SearchType searchType){
        if (searchType==SearchType.Title){
            return new searchByTitle();
        } else if (searchType==SearchType.Language) {
            return new searchByLanguage();
        }
        else {
            // so on for the rest of the strategies
            return new searchByGenre();
        }
    }
}

public class Catalog{
    Set<City> cities;
    ArrayList<Movie> activeMovies;
    private volatile static Catalog catalogUniqueInstance;
    private Catalog(){}

    public static Catalog getCatalogUniqueInstance(){
        if(catalogUniqueInstance==null){
            synchronized (Catalog.class){
                if(catalogUniqueInstance==null){
                    catalogUniqueInstance=new Catalog();
                }
            }
        }
        return catalogUniqueInstance;
    }

    public void addMovie(Movie movie){
        activeMovies.add(movie);
        notifySystem(movie);
    }
    public void notifySystem(Movie movie){
        SystemC systemC = SystemC.getSysUniqueInstance();
        systemC.notifyNewMovie(movie);
    }

    public ArrayList<Movie> getActiveMovies() {
        return this.activeMovies;
    }
}

public class TheatreManager{
    List<Theatre> affiliatedTheatres;
    public volatile static TheatreManager uniqueTheatreMngrInstance;
    private TheatreManager(){}
    public static TheatreManager getTheatreMngrInstance(){
        if (uniqueTheatreMngrInstance==null){
            synchronized (TheatreManager.class){
                if (uniqueTheatreMngrInstance==null){
                    uniqueTheatreMngrInstance=new TheatreManager();
                }
            }
        }
        return uniqueTheatreMngrInstance;
    }
    public boolean addTheatre(Theatre theatre){
        affiliatedTheatres.add(theatre);
        return true;
    }
    public ArrayList<Theatre> getTheatres(Movie movie){
        ArrayList<Theatre> theatresOfMovie = new ArrayList<Theatre>();
        for (Theatre theatre: affiliatedTheatres){
            if(theatre.hasMovie(movie)){
                theatresOfMovie.add(theatre);
            }
        }
        return theatresOfMovie;
    }
}
public class Theatre{
    ArrayList<Show> activeShows;
    ArrayList<Movie> runningMovies;
    String TheatreName;
    Address address;
    public void addShow(Show show){
        if(runningMovies.contains(show.getMovie())){
            runningMovies.add(show.getMovie());
        }
        activeShows.add(show);
    }
    public void removeShow(Show show){
        activeShows.remove(show);
        // check if this the only show for the movie which is running in the theatre then remove it from runningMovies as well
    }

    public ArrayList<Show> getAvailableShowsInTheatre(Movie movie) {
        // go through the activeShows list and return all those movies that match the input parameter
        // or can maintain a movieShowsMap as well
        return new ArrayList<Show>();
    }

    public boolean hasMovie(Movie movie) {
        for (Movie rMovie: runningMovies){
            if(rMovie==movie){
                return true;
            }
        }
        return false;
    }
}

public class Show{
    Date movieTime;
    MovieHall movieHall;
    ArrayList<ShowSeat> showSeats = new ArrayList<ShowSeat>();
    ArrayList<Ticket> ticketsOfShow=new ArrayList<Ticket>();
    Movie movie;
    Theatre theatre;
    boolean isHouseFull;
    int leftOutSeats;
    ShowStatus showStatus;
    public boolean cancelShow() {
        // check if show hasn't yet started and iterate through all the tickets in ticketsOfShow and trigger
        // cancelTicket() on them.
        // cancel method notifies system, system notifies customer
        for (Ticket ticket:ticketsOfShow) {
            ticket.cancel();
        }
        showStatus=ShowStatus.Cancelled;
        theatre.removeShow(this);
        return true;
    }

    public void addTicketsOfShow(Ticket ticket, int seatCount){
        ticketsOfShow.add(ticket);
        // get the num of seats booked decrement leftOutSeats, once it reaches 0 make isHousefull to true.
    }

    public boolean[][] viewSeatingArrangement() {
        return movieHall.getSeatingArrangement();
    }

    public void removeTicketOfShow(Ticket ticket, int size) {
        ticketsOfShow.remove(ticket);
        // get the num of seats cancelled and increment leftOutSeats.
    }

    public Movie getMovie() {
        return this.movie;
    }

}

public class ShowSeat extends MovieHallSeat{
    String seatId;
    double price;
    ShowSeatStatus showSeatStatus;
    static ShowSeat uniqueInstance;

    private ShowSeat(){
        setShowSeatStatus(ShowSeatStatus.Available);
    }
    static public ShowSeat getUniqueInstance(){
        if(uniqueInstance==null){
            synchronized (Show.class){
                if(uniqueInstance==null){
                    uniqueInstance=new ShowSeat();
                }
            }
        }
        return uniqueInstance;
    }

    public ShowSeatStatus getShowSeatStatus() {
        return showSeatStatus;
    }

    public void setShowSeatStatus(ShowSeatStatus showSeatStatus){
        this.showSeatStatus=showSeatStatus;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}


public class Ticket{
    Customer customer;
    Show show;
    ArrayList<ShowSeat>bookedShowSeats;
    BookingStatus bookingStatus;
    double totalPrice;
    Coupon coupon=null;
    PaymentInfo paymentInfo;

    // a coupon may or may not be added so using method overloading for handling both
    public Ticket(Show show, ArrayList<ShowSeat> showSeats, Coupon coupon) {
        this.show=show;
        this.bookedShowSeats=showSeats;
        this.coupon=coupon;
    }
    public Ticket(Show show, ArrayList<ShowSeat> showSeats) {
        this.show=show;
        this.bookedShowSeats=showSeats;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
        if(bookingStatus==BookingStatus.Confirmed){
            notifySystem();
            notifyShow();
        } else if (bookingStatus==BookingStatus.Cancelled) {
            notifySystem();
            notifyDecShow();
        }
    }

    private void notifyDecShow() {
        this.show.removeTicketOfShow(this,bookedShowSeats.size());
    }

    public void notifySystem(){
        SystemC systemC = SystemC.getSysUniqueInstance();
        systemC.notifyBooking(customer, bookingStatus);
    }

    public void notifyShow(){
        this.show.addTicketsOfShow(this, bookedShowSeats.size());
    }

    public double getPrice() {
        // iterate through bookedShowSeats list and sum up the price of all the seats, by calling seat.getPrice()
        // return the final price. include the logic of coupon discount as well.
        totalPrice=100.00;
        return totalPrice;
    }

    public boolean payBill(IPayment iPayment) {
        paymentInfo = new PaymentInfo(iPayment);
        // use of command pattern following dependency inversion principle
        if(paymentInfo.payBill(iPayment,this.totalPrice)){
            setBookingStatus(BookingStatus.Confirmed);
            return true;
        }
        else {
            setBookingStatus(BookingStatus.Failed);
            return false;
        }
    }

    public boolean cancel() {
        // check if show it is cancelled before the show started if yes then trigger the refund process
        Date currTime=new Date();
        if(show.movieTime.getTime()>currTime.getTime()){
            boolean ack;
            ack=paymentInfo.refund();
            if(ack){
                setBookingStatus(BookingStatus.Cancelled);
                freeUpBookedSeats();
            }
            return ack;
        }
        else{
            return false;
        }
    }

    private void freeUpBookedSeats() {
        for(ShowSeat showSeat: bookedShowSeats){
            showSeat.setShowSeatStatus(ShowSeatStatus.Available);
        }
    }

}

public class PaymentInfo{
    long transactionId;
    Date creationDate;
    PaymentStatus paymentStatus;
    public PaymentInfo(IPayment iPayment) {
        setPaymentStatus(PaymentStatus.Pending);
        creationDate=new Date();
    }

    private void setTransactionId() {
        // trigger some unique number generator logic and assign that value to it.
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    public boolean payBill(IPayment iPayment, double price) {
        // use of command pattern following dependency inversion principle
        if(iPayment.execute(price)){
            setTransactionId();
            setPaymentStatus(PaymentStatus.Paid);
            return true;
        }
        else {
            setPaymentStatus(PaymentStatus.Cancelled);
            return false;
        }
    }
    public boolean refund(){
        System.out.println("you will get your money back in 3 business days");
        setPaymentStatus(PaymentStatus.RefundRequested);
        // depending upon the payment mode of the customer trigger the respective api calls for refund after successful
        // refund update the payment status as refunded
        setPaymentStatus(PaymentStatus.Refunded);
        return true;
    }
}

public abstract class IPayment{
    abstract public boolean execute(double price);
}

public class CreditCard extends IPayment{

    @Override
    public boolean execute(double price) {
        return true;
    }
}

public class Cash extends IPayment{

    @Override
    public boolean execute(double price) {
        return true;
    }
}

public class SystemC{
    ArrayList<Customer> subscribedCustomers;
    private volatile static SystemC uniqueSysInstance;
    private SystemC(){}

    public static SystemC getSysUniqueInstance(){
        if (uniqueSysInstance==null){
            synchronized (SystemC.class){
                if (uniqueSysInstance==null){
                    uniqueSysInstance=new SystemC();
                }
            }
        }
        return uniqueSysInstance;
    }

    public void notifyNewMovie(Movie movie) {
        for (Customer customer: subscribedCustomers){
            // notify it to all the customers who are present in subscribers list by fetching their contact info from DB
        }
    }
    public void notifyBooking(Customer customer, BookingStatus bookingStatus){
        // fetch the Customer device type and its push notification ID from DB if it is android trigger Firebase APIs
        // if IOS the trigger APN APIs.
    }
}