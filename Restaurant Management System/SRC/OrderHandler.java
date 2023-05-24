package OODPracticeExample.ResturantManagementSystem;

import java.util.ArrayList;

public class OrderHandler {

    public void createOrder() {
        Order order = new Order();
    }

    public void addMeal(Order order, Meal meal) {
        order.addMeal(meal);
    }

    public boolean placeOrder(Order order) {
        PendingOrderRepo pendingOrderRepo = PendingOrderRepo.getPendingOrderRepoUnqInst();
        return pendingOrderRepo.addOrder(order);
    }

    public Bill generateBill(Order order) {
        double finalPrice = 0.00;
        Bill bill = new Bill();
        ArrayList<Meal> meals = order.getMeals();
        for (Meal meal : meals) {
            ArrayList<MealItem> mealItems = meal.getMealItems();
            for (MealItem mealItem : mealItems) {
                MenuItem menuItem = mealItem.getMenuItem();
                int quantity = mealItem.getQuantity();
                double unitPrice = menuItem.getPrice();
                double price = unitPrice * quantity;
                BillItem billItem = new BillItem(menuItem, quantity, price);
                bill.addBillItems(billItem);
                finalPrice += price;
            }
        }
        order.setBill(bill);
        bill.setFinalPrice(finalPrice);
        return order.getBill();
    }

    public boolean processPayment(Bill bill) {
        System.out.println("payable amount is " + bill.getFinalPrice());
        // ask for the mode of payment and get that payment object in run time from the customer
        IPayment creditCard = new CreditCard(); // for simplicity assume the user chose credit card to pay the bill
        return creditCard.execute(bill.getFinalPrice());
    }
}
