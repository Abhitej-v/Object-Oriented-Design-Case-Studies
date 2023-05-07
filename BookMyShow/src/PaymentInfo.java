package OODPracticeExample.MovieTicketBooking;

import java.util.Date;

public class PaymentInfo {
    long transactionId;
    Date creationDate;
    PaymentStatus paymentStatus;

    public PaymentInfo(IPayment iPayment) {
        setPaymentStatus(PaymentStatus.Pending);
        creationDate = new Date();
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
        if (iPayment.execute(price)) {
            setTransactionId();
            setPaymentStatus(PaymentStatus.Paid);
            return true;
        } else {
            setPaymentStatus(PaymentStatus.Cancelled);
            return false;
        }
    }

    public boolean refund() {
        System.out.println("you will get your money back in 3 business days");
        setPaymentStatus(PaymentStatus.RefundRequested);
        // depending upon the payment mode of the customer trigger the respective api calls for refund after successful
        // refund update the payment status as refunded
        setPaymentStatus(PaymentStatus.Refunded);
        return true;
    }
}
