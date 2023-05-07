package OODPracticeExample.MovieTicketBooking;

public class Cash extends IPayment {

    @Override
    public boolean execute(double price) {
        return true;
    }
}
