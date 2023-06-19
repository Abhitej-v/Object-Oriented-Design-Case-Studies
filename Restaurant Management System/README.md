# Design Restaurant Management System

## Requirements:

1. The restaurant will have different branches.

2. Each restaurant branch will have its menu.

3. The menu will have various menu sections, containing different menu items.

4. The waiter should be able to create an order for a table and add meals for a table.

5. Each meal can have multiple meal items. Each meal item corresponds to a menu item.

6. The system should be able to retrieve information about tables currently available to seat walk-in customers.

7. The system should support the reservation of tables.

8. The receptionist should be able to search for available tables by date/time and reserve a table.

9. The system should allow customers to cancel their reservation.

10. The system should be able to send notifications about the reservation status.

11. The customers should be able to pay their bills through credit card, check or cash.

12. The system allows the manager to keep track of available tables, menu in the system as well as to do various tasks of receptionist and waiter.

## Use Case Diagram

The main actors of our system are:

1. Receptionist: Responsible for searching available table, reserving a table and cancelling reservation.

2. Waiter: To take the order, generate the bill and process the payment.

3. Manager: Responsible for creating and maintaining the menu, adding/modifying the seating arrangement and printing the menu.

4. Chef: To prepare the order.

5. Notification Manager: to send notifications about the reservation status.

![Use Case Diagram](https://github.com/Abhitej-v/Real-World-Object-Oriented-Projects/assets/111651833/b208fcf1-c2d4-4ffb-9932-c9c4ded8d60d)

## UML class diagram
![UML Restaurant Management System](https://github.com/Abhitej-v/Real-World-Object-Oriented-Projects/assets/111651833/7ba95f1f-01c0-4377-97b2-226a27b9ccc0)


## Command Pattern

The command pattern is used in two instances

1. Paying the bill: We can trigger "execute" method on various objects which implemented IPayment interface.

2. Preparing the food: All the menu items implement "DishExecutable". We trigger execute on a menuItem object which holds the logic of that particular menuItem's recipe and preparation steps to be executed.

3. By coding to an interface instead of concrete classes dependency inversion principle is demonstrated.



## Observer Pattern

The observer pattern is used in two instances

1. Once the order is placed by the waiter, that order is put inside pending list of PendingOrderRepo class. PendingOrderRepo is composed with chef, chef is notified upon a new order being placed into the list.

2. For tracking the reservation status and sending it to the customer. Reserve class is composed with NotificationManager. Any change to the reservation will be notified to the NotificationManager.



## Composite Pattern

1. Using composite pattern to implement menu is a standard solution.

2. Though in our requirements its mentioned that we have menu sections under which menu items are present, it could also be simply implemented using a HashMap with key as menu section and value as a list of menu items but it will not be extensible for future changes.

3. In future we might add the functionality to show the ingredients or we may add sub sections with menu sections to categorize the menu better and provide better customer experience so it's better to use composite pattern due to these foreseen reasons.

4. MenuComponent is the common interface which defines all the default implementations of all the methods. MenuSection and MenuItem implement the MenuComponent and override respective methods and implement their own logic.



## Singleton Pattern

1. Singleton Pattern is a standard solution to maintain consistency and acheive concurrency.

2. As two people should not book the same table slot hence its crucial to design a highly consistent system.

3. We don't want two threads operating on the same TableRepo, PendingOrderRepo and creating inconsistent and unreliable data hence it has been made as a singleton.

4. TableRepo class implements various methods like freezing a table slot, freeing up a table slot and noting the customer occupied table slot.

5. PendingOrderRepo implements functionality to add orders into pending list and remove order from pending list upon fulfilling the order.



## Responsibility of various classes

1. Person class: it's a base class for any individual.

2. Employee class: it inherits from person class. Receptionist, Manager, Waiter, Chef are the employees. They are responsible for different functionalities.

3. Receptionist: Is responsible for searching available tables and reserving tables etc..., but the Manager should also be able to do these operations so these functionalities are moved out to Search, Reserve class and these objects are composed in search class.

4. Waiter: Waiter should be able to create an order, add meals to an order, place an order, generate the bill and process the payment. As manager should also be able to do all these tasks these are moved to OrderHandler class and its object is composed with Waiter class.

5. Manager: shall be able to do all the operations of Search, Reserve and OrderHandler objects. Manager is uniquely responsible for creating a menu, modifying the menu, printing the menu and modifying the seating arrangement.

6. Chef: chef gets notified once an order is placed, the chef prepares the menu Items present in the order.

7. Reserve: Responsible for operating on TableRepo to reserve a table or to cancel a reservation.

8. Search: Responsible for fetching the available tables from TableRepo.

9. Order: should be able to add meals in an order. It has list of meals in it, Table for which this order is made, order number and the bill that gets generated at the end.

10. Meal, MealItem: A meal is a list of meal Items. For example, Happy meal of MacDonald’s contain may contain say two burgers, one soft drink and  one French fries. The Menu Item along with its quantity is a meal Item. Two burgers are a meal Item, One French fries is a meal Item.  

11. MenuItem: is an implementation of menuComponent and DishExecutable. A burger is a menuItem.

12. BillItem: The menuItem with its quantity is a meal Item. price for this this meal Item, menuItem and quantity are the contents of a Bill Item.

13. Bill: It has list of bill items and the final price of the bill.



## Work flow of a customer reserving the table, having meal at that reserved slot and paying the bill.

Customer call the reception and ask for a reservation for a particular date and time. Receptionist triggers a "getAvailTables" method on the search object it is composed with. Search object gets the list of tables available at that slot and returns to the receptionist. Receptionist tells whether a table is available at a given slot. By confirming with customer Receptionist reserves the table for the customer. The customer will get a  notification with reservation details. The customers arrive to the restaurant on time. The receptionist "checks-in" the customer. Waiter will create the order by triggering "createOrder" method. Waiter will add the requested meals to the order by triggering "addMeal". once the order is taken waiter places the order by calling "placeOrder" method. "placeOrder" method will trigger PendingOrderRepo to add this order into its pending list by calling "addOrder". Chef is notified by calling "notifyOrder". once the chef is notified the chef will start preparing menu items one by one by triggering execute method on menu items of the order. After customer finishing the meal, the waiter generates Bill by creating Bill Items objects and then adding them to Bill object using "addBillItems" method. Once the Bill is generated the order is composed with the respective Bill. Waiter will then process the payment. Bill will trigger IPayment type object by calling execute method on it. IPayment object can be of any object credit card and UPI, regardless of the type of IPayment object the transaction will execute.

 

## Further scope

Below mentioned are a few of the future improvement that can be implemented.

1. When the food is prepared and the order is removed from pending list, we can notify the waiter to serve the order. Use of observer pattern.

2. Can use decorator pattern for Bill to include the functionality of discounts, offers etc...,

3. Booking slot of a table can be a metadata like a key value pair instead of LocalDateTime of java.

4. Use of ORM design to have to store information in DB instead of a TableRepo class.


