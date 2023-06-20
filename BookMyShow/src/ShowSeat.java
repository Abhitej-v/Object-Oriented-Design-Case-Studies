package OODPracticeExample.MovieTicketBooking;

public class ShowSeat extends MovieHallSeat {
    String seatId;
    double price;
    ShowSeatStatus showSeatStatus;
    MovieHallSeat movieHallSeat;

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
