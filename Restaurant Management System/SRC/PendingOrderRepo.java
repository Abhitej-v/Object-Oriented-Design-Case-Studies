package OODPracticeExample.ResturantManagementSystem;

import java.util.ArrayList;

public class PendingOrderRepo {
    ArrayList<Order> pendingOrders;
    Chef chef;

    public static PendingOrderRepo getPendingOrderRepoUnqInst() {
        // implement singleton design pattern here similar to TableRepo
        return new PendingOrderRepo();
    }

    public boolean addOrder(Order order) {
        pendingOrders.add(order);
        chef.notifyOrder(order);
        return true;
    }

    public boolean orderFulfilled(Order order) {
        pendingOrders.remove(order);
        return true;
    }
}
