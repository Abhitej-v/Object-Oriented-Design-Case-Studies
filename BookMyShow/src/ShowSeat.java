package OODPracticeExample.MovieTicketBooking;

public class ShowSeat extends MovieHallSeat {
    String seatId;
    double price;
    ShowSeatStatus showSeatStatus;
    static ShowSeat uniqueInstance;

    private ShowSeat() {
        setShowSeatStatus(ShowSeatStatus.Available);
    }

    static public ShowSeat getUniqueInstance() {
        if (uniqueInstance == null) {
            synchronized (Show.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new ShowSeat();
                }
            }
        }
        return uniqueInstance;
    }

    public ShowSeatStatus getShowSeatStatus() {
        return showSeatStatus;
    }

    public void setShowSeatStatus(ShowSeatStatus showSeatStatus) {
        this.showSeatStatus = showSeatStatus;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
