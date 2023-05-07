package OODPracticeExample.MovieTicketBooking;

public class CreditCard extends IPayment {

    @Override
    public boolean execute(double price) {
        return true;
    }
}
