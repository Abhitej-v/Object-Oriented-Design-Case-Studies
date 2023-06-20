package OODPracticeExample.MovieTicketBooking;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

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

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
        if (bookingStatus == BookingStatus.Confirmed) {
            notifySystem();
            notifyShowMgrAddTicket();
            this.show.decLeftOutSeats(bookedShowSeats.size());
        } else if (bookingStatus == BookingStatus.Cancelled) {
            notifySystem();
            notifyShowMgrRemoveTicket();
            this.show.incLeftOutSeats(bookedShowSeats.size());
        }
    }



    private void notifySystem() {
        SystemC systemC = SystemC.getSysUniqueInstance();
        systemC.notifyBooking(customer, bookingStatus);
    }

    private void notifyShowMgrAddTicket() {
        ShowManager showManager=ShowManager.getShowMgrInstance();
        showManager.addTicketForShow(this.show,this);
    }
    public void notifyShowMgrRemoveTicket() {
        ShowManager showManager=ShowManager.getShowMgrInstance();
        showManager.removeTicketForShow(this.show,this);
    }
    public double getPrice() {
        // iterate through bookedShowSeats list and sum up the price of all the seats, by calling seat.getPrice()
        // return the final price. include the logic of coupon discount as well.
        totalPrice = 100.00;
        return totalPrice;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public boolean cancel() {
        // check if show it is cancelled before the show started if yes then trigger the refund process
        Date currTime = new Date();
        if (show.movieTime.getTime() > currTime.getTime()) {
            boolean ack;
            ack = paymentInfo.refund();
            if (ack) {
                setBookingStatus(BookingStatus.Cancelled);
                ShowManager showManager=ShowManager.getShowMgrInstance();
                showManager.changeSeatStatus(ShowSeatStatus.Available,bookedShowSeats);
            }
            return ack;
        } else {
            return false;
        }
    }
}
