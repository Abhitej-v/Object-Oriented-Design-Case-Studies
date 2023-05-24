package OODPracticeExample.ResturantManagementSystem;

import java.util.List;

public class Branch {
    Address address;
    Receptionist receptionist;
    Manager manager;
    List<Waiter> waiters;
    List<Chef> chefs;
    SeatingArrangement seatingArrangement;
    MenuComponent menu;

    public MenuComponent getMenu() {
        return menu;
    }

    public void setMenu(MenuComponent menu) {
        this.menu = menu;
    }

}
