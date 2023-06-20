package OODPracticeExample.MovieTicketBooking;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicketManager {

    public static boolean validateAndBook(MetaData metaData) {
        ShowManager showManager=ShowManager.getShowMgrInstance();
        ArrayList<ShowSeat> showSeats=showManager.getShowSeatsFromSeatIdList(metaData.showId,metaData.seatNumbers);
        if (showManager.validateToBlockSeats(showSeats)){
            Ticket ticket = new Ticket(showManager.getShow(metaData.showId),showSeats,metaData.coupon);
            double price = ticket.getPrice();
            System.out.println(price + " is the price of booking, do you wish proceed to pay");
            // timer shall wait for response for 13 mins after which booking shall be cancelled and seats should be
            // again made available
            Scanner sc = new Scanner(System.in);
            if (sc.nextBoolean() == true) {
                IPayment iPayment = new CreditCard(); // this should be provided by the customer
                payBill(iPayment,ticket);
            } else {
                ticket.setBookingStatus(BookingStatus.Cancelled);
                showManager.changeSeatStatus(ShowSeatStatus.Available, showSeats);
            }
            if (ticket.bookingStatus == BookingStatus.Confirmed) {
                // add the show and Ticket to the active bookings hashmap
                showManager.changeSeatStatus(ShowSeatStatus.Reserved, showSeats);
                return true;
            }
        }
        return false;
    }
    private static boolean payBill(IPayment iPayment, Ticket ticket) {
        PaymentInfo paymentInfo=new PaymentInfo(iPayment);
        ticket.setPaymentInfo(paymentInfo);
        // use of command pattern following dependency inversion principle
        if (paymentInfo.payBill(iPayment, ticket.getPrice())) {
            ticket.setBookingStatus(BookingStatus.Confirmed);
            return true;
        } else {
            ticket.setBookingStatus(BookingStatus.Failed);
            return false;
        }
    }
}
