package OODPracticeExample.MovieTicketBooking;

import java.util.ArrayList;
import java.util.Date;

public class Ticket {
    Customer customer;
    Show show;
    ArrayList<ShowSeat> bookedShowSeats;
    BookingStatus bookingStatus;
    double totalPrice;
    Coupon coupon = null;
    PaymentInfo paymentInfo;

    // a coupon may or may not be added so using method overloading for handling both
    public Ticket(Show show, ArrayList<ShowSeat> showSeats, Coupon coupon) {
        this.show = show;
        this.bookedShowSeats = showSeats;
        this.coupon = coupon;
    }

    public Ticket(Show show, ArrayList<ShowSeat> showSeats) {
        this.show = show;
        this.bookedShowSeats = showSeats;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
        if (bookingStatus == BookingStatus.Confirmed) {
            notifySystem();
            notifyShow();
        } else if (bookingStatus == BookingStatus.Cancelled) {
            notifySystem();
            notifyDecShow();
        }
    }

    private void notifyDecShow() {
        this.show.removeTicketOfShow(this, bookedShowSeats.size());
    }

    public void notifySystem() {
        SystemC systemC = SystemC.getSysUniqueInstance();
        systemC.notifyBooking(customer, bookingStatus);
    }

    public void notifyShow() {
        this.show.addTicketsOfShow(this, bookedShowSeats.size());
    }

    public double getPrice() {
        // iterate through bookedShowSeats list and sum up the price of all the seats, by calling seat.getPrice()
        // return the final price. include the logic of coupon discount as well.
        totalPrice = 100.00;
        return totalPrice;
    }

    public boolean payBill(IPayment iPayment) {
        paymentInfo = new PaymentInfo(iPayment);
        // use of command pattern following dependency inversion principle
        if (paymentInfo.payBill(iPayment, this.totalPrice)) {
            setBookingStatus(BookingStatus.Confirmed);
            return true;
        } else {
            setBookingStatus(BookingStatus.Failed);
            return false;
        }
    }

    public boolean cancel() {
        // check if show it is cancelled before the show started if yes then trigger the refund process
        Date currTime = new Date();
        if (show.movieTime.getTime() > currTime.getTime()) {
            boolean ack;
            ack = paymentInfo.refund();
            if (ack) {
                setBookingStatus(BookingStatus.Cancelled);
                freeUpBookedSeats();
            }
            return ack;
        } else {
            return false;
        }
    }

    private void freeUpBookedSeats() {
        for (ShowSeat showSeat : bookedShowSeats) {
            showSeat.setShowSeatStatus(ShowSeatStatus.Available);
        }
    }

}
