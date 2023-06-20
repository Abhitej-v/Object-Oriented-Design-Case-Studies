# Design Book My Show

## Requirements:

1. It should be able to list the cities where affiliate Theatres are located.

2. Each Theatre can have multiple halls and each hall can run one movie show at a time.

3. Each Movie will have multiple shows.

4. Customers should be able to search movies by their title, language, genre, release date, and city name.

5. Once the customer selects a movie, the service should display the theatres running that movie and its available shows.

6. The customer should be able to select a show at a particular theatre and book their tickets.

7. The service should show the customer the seating arrangement of the movie hall. The customer should be able to select multiple seats according to their preference.

8. The customer should be able to distinguish between available seats and booked ones.

9. The system should send notifications whenever there is a new movie, as well as when a booking is made or cancelled.

10. Customers of our system should be able to pay with credit cards or cash.

11. The system should ensure that no two customers can reserve the same seat.
    Customers should be able to add a discount coupon to their payment.

## Use case diagram:

The three main actors of the system are 

1. Admin: block/ unblock user, cancel show

2. Customer: search movie, select theatre, select show, select seats, and be able to book tickets

3. System: send notifications

## UML class diagram:

## Responsibilities of various classes:

1. Customer: search movie, select theatre, select show, select seats, and be able to book tickets.

2. SearchFactory: Based on the input of searchType it will create various search objects.

3. Catalog: Maintains the list of active movies. Upon adding new movies, it notifies system. singleton pattern is used for catalog so that catalog is the single source of truth for active movies.

4. Admin: Mainly responsible for adding a movie to the catalog, adding a new theatre in TheatreManager, creating or cancelling a show.

5. TheatreManager: Mainly responsible for adding theatre, removing theatre, storing the list of theatres. Given a movie it will return a list of theatres in which that movie is being played.

6. Movie: It has information about title, language, genre, release date, duration of the movie, movie certificate.

7. Theatre: Can add a show or remove a show from a theatre. Given a particular movie it will return the list of show Id's which are playing that movie in that theatre.

8. MovieHall: It mainly has seating arrangement, list of movie hall seats and total number of seats in that movie hall.

9. MovieHallseat: It has a seat num. It has a seatType (Recliner, Premium, Slider).

10. Show: Show "has a" movieTime, movieHall, movie and theatre. These four are what defines a show. Based on show seats booking the left-out seats and the show status is updated. It has the showStatus enum.

11. ShowSeat: ShowSeat has information about seatId, price, status of the seat and movieHallSeat. A movie Hall seat can be booked for various shows hence we require another object showSeat which is a seat specific to a show.

12. MetaData: Contains showId, list of seat numbers and coupon. It is passed while customer triggers API to book a ticket.

13. TicketManager: Mainly responsible for calling showManger to validate if the requested seats are available for booking. If yes, it will temporarily block the seats. It will make a thread wait for 13 mins to get an input from customer to go ahead with payment. Upon receiving the acknowledgement from customer, it will trigger payBill API of PaymentInfo class. Depending upon the payment status, customer input or customer inaction for more than 13 mins it will release the showSeats. Ticket's status is also updated accordingly.

14. Ticket: It has information about customer, show for which it is booked, list of booked show seats, booking status of the ticket, total price, coupon used for booking and paymentInfo. It is responsible for cancelling a ticket (if the movie hasn't started yet), triggers PaymentInfo's refund API. It will be able to set booking status, if the status is set to confirmed or cancelled both the show manager and the system are notified. Show manger is notified to either remove or add this ticket against a show depending upon if the booking is cancelled or if it confirmed respectively.

15. System: Upon getting notified about new movie all its subscribers will be notified, upon booking confirmation or booking cancellation that particular customer is notified.

16. ShowManager: As we don't want two users to book the same show seat showManager is made as singleton. It has showTicketsMap which is used upon a show cancellation to refund the amount for all the customers that booked those tickets. It has showToShowSeatsMap, it is used to get the show seats with the show and the seat numbers of the seats. It validates if the requested show seats are available for booking if yes it will block the requested show seats.

17. PaymentInfo: Stores transaction ID , creation date, responsible for triggering pay bill and refund API calls on the IPayment interface

18. Coupon: has info about max limit, percentage, status etc...,

19. IPayment: It is an interface implemented by various payment methods. Dependency inversion principle by use of command pattern.



## Design Patterns used:

### Factory pattern:

There are various search strategies the system should support. They are SearchByTitle, SearchByLanguage, SearchByName, SearchByGenre, SearchByReleaseDate. Depending upon the search type passed to the search factory it will create respective search object.

### Singleton pattern:

1. Catalog is expected to be consistent with the list of all active movie

2. TheatreManager is expected to be consistent with the list of affiliated theatre

3. ShowManager should be consistent. It is very critical to achieve concurrency as we don't want two customers should to be able to book the same seat. 

4. Singleton Pattern provides solution to maintain consistency and achieve concurrency.

### Command pattern:

Paying the bill: We can trigger "execute" method on various objects which implemented IPayment interface. By coding to an interface instead of concrete classes dependency inversion principle is maintained.

### Observer pattern:

1. System observes Ticket class for a booking confirmation or booking cancellation. system is the subscriber of booking status. System then notifies the booking status to the customer, so the customer is also the ultimate subscriber of the booking status.

2. Customers observes new movie. Admin Upon adding a new movie to the catalog it is notified to all the customers that subscribed to get notified upon new movie release.

## Code flow of a customer booking a ticket for a show and admin cancelling the show.

Customer will call the searchFactory to create a search object. Based on the search type passed to it searchFactory will return a search object. customer will trigger a searchMovie API call on search object with respective parameter. Assume customer chose to search by title. searchByTitle implements the searchMovie method. It will fetch list of all active movies from the catalog and filter out the movies that match that title and send the list of movies back to the customer. Customer will select a movie from list of movies and requests to get theatres in which this movie is played. It will call "getTheatres" API call on TheatreManager object. TheatreManager has list of affiliated movies. It filters out the theatre in which a movie is played and it will send back the list of theatres. Customer will request  for available show by passing a theatre and movie. Theatre maintains a list of shows. customer will call "getAvailableShowsInTheatre" API on theatre object which will return list of desired shows. Customer will then request to view the seating Arrangement. Customer will call "viewSeatingArrangement"  API call on show object. Show object is composed of MovieHall object which has a matrix that represent its seating arrangement. Customer will select the seats and will request to book those seats by passing metadata as input. metadata contains showId, a list of seat numbers and a coupon. customer will then trigger "validateAndBook"  API call on TicketManager. TicketManager will ask the ShowManager to get the list of seats from showId and list of seat numbers. Then TicketManager will call "validateToBlockSeats" API call on showManager. showManager will check if the requested seats are available. If yes it will block the seats temporarily for the customer to do the payment. Ticket manager Upon receiving the acknowledgement from customer, it will trigger payBill API of PaymentInfo class. Assume payment is successful then the ticket status is confirmed. Once the ticket is confirmed it notifies the system and them system will notify the user. Once the ticket is confirmed Ticket will notify ShowManager to store the reference of the ticket as it is booked for a particular show in a HashMap. After successful booking we return true to the customer. Due to heavy rainfall in the city the "Admin" chose to cancel the show. The admin will call "cancelShow" API call on ShowManager. ShowManager will get list of all the tickets booked for this show and trigger "cancel" API call on the ticket objects. In the cancel method calls "refund" API call of the PaymentInfo object composed with the ticket. It will make the ticket booking status as cancelled and request the ShowManager to change the status of the booked show seats. Upon changing the ticket booking status to cancelled the system is notified and the system in turn notifies the customer.
