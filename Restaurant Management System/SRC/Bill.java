package OODPracticeExample.ResturantManagementSystem;

import java.util.ArrayList;

public class Bill {
    ArrayList<BillItem> billItems = new ArrayList<BillItem>();
    double finalPrice;

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public void addBillItems(BillItem billItem) {
        billItems.add(billItem);
    }

    public double getFinalPrice() {
        return finalPrice;
    }
}
