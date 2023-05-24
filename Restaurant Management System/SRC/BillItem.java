package OODPracticeExample.ResturantManagementSystem;

public class BillItem {
    MenuItem menuItem;
    int quantity;
    double price;

    public BillItem(MenuItem menuItem, int quantity, double price) {
        this.menuItem = menuItem;
        this.price = price;
        this.quantity = quantity;
    }
}
