package OODPracticeExample.ResturantManagementSystem;

public class MenuItem extends MenuComponent implements DishExecutable {

    boolean isVegetarian;
    double price;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public void print() {
        System.out.println("Name: " + name + " Description: " + description);
    }

    public boolean execute() {
        // sequence of steps to follow to prepare this dish.
        return true;
    }
}
