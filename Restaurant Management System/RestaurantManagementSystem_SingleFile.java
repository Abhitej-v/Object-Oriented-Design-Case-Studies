package OODPracticeExample.ResturantManagementSystem;
import java.util.*;
import java.time.LocalDateTime;

public class Table{
    int seatingCapacity;
    int tableNum;
}

public class Address{
    String city;
    String street;
    String propertyNum;
}

public class SeatingArrangement{
    int tableCount;
    ArrayList<Table> tables;
    Byte[] tableSeatingImage;
}

public class Restaurant {
    ArrayList<Branch> branches;
}

public class Branch{
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

public class Receptionist extends Employee{
    Search searchObj;
    Reserve reserveObj;
    public ArrayList<Table> getAvailTables(LocalDateTime bookingInfo) {
        return searchObj.getAvailTables(bookingInfo);
    }
    public boolean reserveTable(Customer customer, LocalDateTime bookingTime, Table table){
        return reserveObj.reserveTable(customer,bookingTime,table);
    }
    public boolean cancelReservation(Customer customer, LocalDateTime bookingTime, Table table){
        return reserveObj.cancelReservation(customer, bookingTime,table);
    }
    public boolean checkIn(Table table, LocalDateTime bookingTime, Customer customer){
        TableRepo tableRepo = TableRepo.getUniqueTableRepoInst();
        return tableRepo.customerOccupied(bookingTime, table);
    }
}

public class Search {
    public ArrayList<Table> getAvailTables(LocalDateTime bookingInfo) {
        TableRepo tableRepo = TableRepo.getUniqueTableRepoInst();
        //ideally this search happens in DB with Query using filter as booking status and Date Time
        return tableRepo.getAvailTables(bookingInfo);
    }
}
public class Reserve{
    NotificationManager notificationManager;

    public boolean reserveTable(Customer customer, LocalDateTime bookingTime, Table table){
        TableRepo tableRepo=TableRepo.getUniqueTableRepoInst();
        boolean ack = tableRepo.freezeTable(bookingTime,table);
        if (ack==true){
            notificationManager.sendNotification(customer, "Reserved");
        }
        return ack;
    }
    public boolean cancelReservation(Customer customer, LocalDateTime bookingTime, Table table){
        TableRepo tableRepo=TableRepo.getUniqueTableRepoInst();
        boolean ack = tableRepo.freeTable(bookingTime,table);
        if (ack==true){
            notificationManager.sendNotification(customer, "Canceled");
        }
        return ack;
    }
}

public class TableRepo{
    HashMap<LocalDateTime, HashMap<Table, BookingStatus>> tableBookingMap= new HashMap<LocalDateTime, HashMap<Table, BookingStatus>>();
    ArrayList<Table> tablesInRestaurant = new ArrayList<Table>();
    private volatile static TableRepo tableRepoUnqInst;
    public boolean freezeTable(LocalDateTime bookingTime, Table table){
        if(tableBookingMap.get(bookingTime).get(table)==BookingStatus.Available){
            tableBookingMap.get(bookingTime).put(table, BookingStatus.Reserved);
            // It can have additional column in DB which stores customer object indicating who booked the table slot
            return true;
        }
        else {
            return false;
        }
    }
    public boolean freeTable(LocalDateTime bookingTime, Table table) {
        tableBookingMap.get(bookingTime).put(table, BookingStatus.Available);
        return true;
    }

    public boolean customerOccupied(LocalDateTime bookingTime, Table table){
        // can add an additional check, if reserved table is reserved by the check in customer or not.
        if(tableBookingMap.get(bookingTime).get(table)==BookingStatus.Available || tableBookingMap.get(bookingTime).get(table)==BookingStatus.Reserved){
            tableBookingMap.get(bookingTime).put(table, BookingStatus.Occupied);
            return true;
        }
        else {
            return false;
        }
    }
    private TableRepo(){
    }
    public static TableRepo getUniqueTableRepoInst(){
        if (tableRepoUnqInst==null){
            synchronized (TableRepo.class){
                if (tableRepoUnqInst==null){
                    tableRepoUnqInst = new TableRepo();
                }
            }
        }
        return tableRepoUnqInst;
    }
    public ArrayList<Table> getAvailTables(LocalDateTime bookingInfo) {
        ArrayList<Table> availTables=new ArrayList<Table>();
        HashMap<Table, BookingStatus> tableBookingDateMap=tableBookingMap.get(bookingInfo);
        //ideally this search happens in DB with Query using filter as booking status
        for (Map.Entry<Table,BookingStatus> entry: tableBookingDateMap.entrySet()) {
            Table table=entry.getKey();
            BookingStatus bookingStatus=entry.getValue();
            if(bookingStatus==BookingStatus.Available){
                availTables.add(table);
            }
        }
        return availTables;
    }
    public void addTable(Table table){
        tablesInRestaurant.add(table);
    }
}

public enum BookingStatus{
    Reserved, Available, Occupied
}

public class NotificationManager{
    public void sendNotification(Customer customer, String message){
        // this fetching can also happen in DB tables by implementing an ORM design
        String emailID=customer.getEmailId();
        // use this Email ID trigger Gmail API to send the notification
    }
}

public abstract class Person{
    String name;
    String phoneNum;
}
public abstract class Employee extends Person{
    long employeeID;
    Date joiningDate;
    String password;
}

public class Customer extends Person{
    private String accountPassword;
    private String EmailId;

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }
}

public class Manager extends Employee{
    Search searchObj;
    Reserve reserveObj;
    Branch branch;
    OrderHandler orderHandler;
    public void createMenu(){
        MenuComponent menu=new MenuSection();
        branch.setMenu(menu);
    }
    public void addMenuSection(MenuComponent parentMenuComponent, MenuComponent subSection){
        parentMenuComponent.addMenuComponent(subSection);
    }
    public void addMenuItem(MenuComponent parentMenuComponent, MenuComponent menuItem){
        parentMenuComponent.addMenuComponent(menuItem);
    }
    public void printMenu(){
        MenuComponent menu=branch.getMenu();
        menu.print();
    }

    public void addTable(){
        Table table=new Table(); //have to pass seating capacity as well by taking the input.
        TableRepo tableRepo= TableRepo.getUniqueTableRepoInst();
        tableRepo.addTable(table);
    }

    public ArrayList<Table> getAvailTables(LocalDateTime bookingInfo) {
        return searchObj.getAvailTables(bookingInfo);
    }
    public boolean reserveTable(Customer customer, LocalDateTime bookingTime, Table table){
        return reserveObj.reserveTable(customer,bookingTime,table);
    }
    public boolean cancelReservation(Customer customer, LocalDateTime bookingTime, Table table){
        return reserveObj.cancelReservation(customer, bookingTime,table);
    }
    public void createOrder(){
        orderHandler.createOrder();
    }
    public void addMeal(Order order,Meal meal){
        orderHandler.addMeal(order,meal);
    }
    public boolean placeOrder(Order order){
        return orderHandler.placeOrder(order);
    }
    public Bill generateBill(Order order){
        return orderHandler.generateBill(order);
    }

    public boolean processPayment(Bill bill){
        return orderHandler.processPayment(bill);
    }
}

public abstract class MenuComponent{
    String name;
    String description;

    public void addMenuComponent(MenuComponent  menuComponent){
        throw new UnsupportedOperationException();
    }
    public void removeMenuComponent(MenuComponent menuComponent){
        throw new UnsupportedOperationException();
    }
    public MenuComponent getChild(int i){
        throw new UnsupportedOperationException();
    }
    public String getDescription(){
        throw new UnsupportedOperationException();
    }
    public String getName(){
        throw new UnsupportedOperationException();
    }
    public double getPrice(){
        throw new UnsupportedOperationException();
    }
    public boolean isVegetarian(){
        throw new UnsupportedOperationException();
    }
    public void print(){
        throw new UnsupportedOperationException();
    }
}

public class MenuSection extends MenuComponent{
    ArrayList<MenuComponent> subSections=new ArrayList<MenuComponent>();
    @Override
    public void addMenuComponent(MenuComponent menuComponent){
        subSections.add(menuComponent);
    }
    @Override
    public void removeMenuComponent(MenuComponent menuComponent){
        subSections.remove(menuComponent);
    }
    public MenuComponent getChild(int i){
        return subSections.get(i);
    }
    public void print(){
        System.out.println("Section Name "+name+" Section description "+description);
        for (MenuComponent menuComponent: subSections){
            menuComponent.print();
        }
    }
}
public interface DishExecutable{
    boolean execute();
}
public class MenuItem extends MenuComponent implements DishExecutable{

    boolean isVegetarian;
    double price;

    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public double getPrice(){
        return price;
    }
    public boolean isVegetarian(){
        return isVegetarian;
    }
    public void print(){
        System.out.println("Name: "+name+" Description: "+description);
    }
    public boolean execute(){
        // sequence of steps to follow to prepare this dish.
        return true;
    }
}

public class Waiter extends Employee{
    OrderHandler orderHandler;
    public void createOrder(){
        orderHandler.createOrder();
    }
    public void addMeal(Order order,Meal meal){
        orderHandler.addMeal(order,meal);
    }
    public boolean placeOrder(Order order){
        return orderHandler.placeOrder(order);
    }
    public Bill generateBill(Order order){
        return orderHandler.generateBill(order);
    }

    public boolean processPayment(Bill bill){
        return orderHandler.processPayment(bill);
    }
}

public class OrderHandler{

    public void createOrder(){
        Order order=new Order();
    }
    public void addMeal(Order order,Meal meal){
        order.addMeal(meal);
    }
    public boolean placeOrder(Order order){
        PendingOrderRepo pendingOrderRepo=PendingOrderRepo.getPendingOrderRepoUnqInst();
        return pendingOrderRepo.addOrder(order);
    }
    public Bill generateBill(Order order){
        double finalPrice = 0.00;
        Bill bill=new Bill();
        ArrayList<Meal> meals = order.getMeals();
        for (Meal meal: meals){
            ArrayList<MealItem> mealItems=meal.getMealItems();
            for (MealItem mealItem: mealItems){
                MenuItem menuItem=mealItem.getMenuItem();
                int quantity=mealItem.getQuantity();
                double unitPrice=menuItem.getPrice();
                double price=unitPrice*quantity;
                BillItem billItem=new BillItem(menuItem,quantity,price);
                bill.addBillItems(billItem);
                finalPrice+=price;
            }
        }
        order.setBill(bill);
        bill.setFinalPrice(finalPrice);
        return order.getBill();
    }

    public boolean processPayment(Bill bill){
        System.out.println("payable amount is "+bill.getFinalPrice());
        // ask for the mode of payment and get that payment object in run time from the customer
        IPayment creditCard=new CreditCard(); // for simplicity assume the user chose credit card to pay the bill
        return creditCard.execute(bill.getFinalPrice());
    }
}
public class PendingOrderRepo{
    ArrayList<Order> pendingOrders;
    Chef chef;

    public static PendingOrderRepo getPendingOrderRepoUnqInst() {
        // implement singleton design pattern here similar to TableRepo
        return new PendingOrderRepo();
    }

    public boolean addOrder(Order order){
        pendingOrders.add(order);
        chef.notifyOrder(order);
        return true;
    }
    public boolean orderFulfilled(Order order){
        pendingOrders.remove(order);
        return true;
    }
}
public class Order{
    ArrayList<Meal> meals=new ArrayList<Meal>();
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
        if (bill==null){
            System.out.println("First generate the bill and then try accessing it");
            return null;
        }
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}

public class Meal{
    ArrayList<MealItem> mealItems=new ArrayList<MealItem>();

    public ArrayList<MealItem> getMealItems() {
        return mealItems;
    }
}
public class MealItem{
    MenuItem menuItem;
    int quantity;

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity;
    }
}

public class Bill{
    ArrayList<BillItem> billItems=new ArrayList<BillItem>();
    double finalPrice;

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }
    public void addBillItems(BillItem billItem){
        billItems.add(billItem);
    }

    public double getFinalPrice() {
        return finalPrice;
    }
}
public class BillItem{
    MenuItem menuItem;
    int quantity;
    double price;

    public BillItem(MenuItem menuItem, int quantity, double price) {
        this.menuItem=menuItem;
        this.price=price;
        this.quantity=quantity;
    }
}
public interface IPayment {
    boolean execute(double price);
}
public class Cash implements IPayment {
    public boolean execute(double price) {
        return true;
    }
}
public class CreditCard implements IPayment {
    public boolean execute(double price) {
        return true;
    }
}
public class UPI implements IPayment {
    public boolean execute(double price) {
        return true;
    }
}
public class Chef extends Employee{
    private void prepareFood(Order order){
        ArrayList<Meal> meals = order.getMeals();
        for (Meal meal: meals){
            ArrayList<MealItem> mealItems=meal.getMealItems();
            for (MealItem mealItem: mealItems){
                MenuItem menuItem=mealItem.getMenuItem();
                int quantity=mealItem.getQuantity();
                for(int i=0;i<quantity;i++){
                    menuItem.execute();
                }
            }
        }
        PendingOrderRepo pendingOrderRepo=PendingOrderRepo.getPendingOrderRepoUnqInst();
        pendingOrderRepo.orderFulfilled(order);
    }

    public void notifyOrder(Order order) {
        prepareFood(order);
    }
}