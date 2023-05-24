package OODPracticeExample.ResturantManagementSystem;

import java.util.ArrayList;

public class Chef extends Employee {
    private void prepareFood(Order order) {
        ArrayList<Meal> meals = order.getMeals();
        for (Meal meal : meals) {
            ArrayList<MealItem> mealItems = meal.getMealItems();
            for (MealItem mealItem : mealItems) {
                MenuItem menuItem = mealItem.getMenuItem();
                int quantity = mealItem.getQuantity();
                for (int i = 0; i < quantity; i++) {
                    menuItem.execute();
                }
            }
        }
        PendingOrderRepo pendingOrderRepo = PendingOrderRepo.getPendingOrderRepoUnqInst();
        pendingOrderRepo.orderFulfilled(order);
    }

    public void notifyOrder(Order order) {
        prepareFood(order);
    }
}
