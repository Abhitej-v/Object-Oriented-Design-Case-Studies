package OODPracticeExample.ResturantManagementSystem;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Manager extends Employee {
    Search searchObj;
    Reserve reserveObj;
    Branch branch;
    OrderHandler orderHandler;

    public void createMenu() {
        MenuComponent menu = new MenuSection();
        branch.setMenu(menu);
    }

    public void addMenuSection(MenuComponent parentMenuComponent, MenuComponent subSection) {
        parentMenuComponent.addMenuComponent(subSection);
    }

    public void addMenuItem(MenuComponent parentMenuComponent, MenuComponent menuItem) {
        parentMenuComponent.addMenuComponent(menuItem);
    }

    public void printMenu() {
        MenuComponent menu = branch.getMenu();
        menu.print();
    }

    public void addTable() {
        Table table = new Table(); //have to pass seating capacity as well by taking the input.
        TableRepo tableRepo = TableRepo.getUniqueTableRepoInst();
        tableRepo.addTable(table);
    }

    public ArrayList<Table> getAvailTables(LocalDateTime bookingInfo) {
        return searchObj.getAvailTables(bookingInfo);
    }

    public boolean reserveTable(Customer customer, LocalDateTime bookingTime, Table table) {
        return reserveObj.reserveTable(customer, bookingTime, table);
    }

    public boolean cancelReservation(Customer customer, LocalDateTime bookingTime, Table table) {
        return reserveObj.cancelReservation(customer, bookingTime, table);
    }

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
