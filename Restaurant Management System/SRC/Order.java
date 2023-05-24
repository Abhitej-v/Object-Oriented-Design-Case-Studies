package OODPracticeExample.ResturantManagementSystem;

import java.util.ArrayList;

public class Order {
    ArrayList<Meal> meals = new ArrayList<Meal>();
    Table table;
    int orderNum;
    Bill bill;

    public void addMeal(Meal meal) {
        meals.add(meal);
    }

    public ArrayList<Meal> getMeals() {
        return meals;
    }

    public Bill getBill() {
        if (bill == null) {
            System.out.println("First generate the bill and then try accessing it");
            return null;
        }
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
