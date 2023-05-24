package OODPracticeExample.ResturantManagementSystem;

public class Waiter extends Employee {
    OrderHandler orderHandler;

    public void createOrder() {
        orderHandler.createOrder();
    }

    public void addMeal(Order order, Meal meal) {
        orderHandler.addMeal(order, meal);
    }

    public boolean placeOrder(Order order) {
        return orderHandler.placeOrder(order);
    }

    public Bill generateBill(Order order) {
        return orderHandler.generateBill(order);
    }

    public boolean processPayment(Bill bill) {
        return orderHandler.processPayment(bill);
    }
}
