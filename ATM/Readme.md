# Design Automated Teller Machine

 

## Requirements:

#### The Core functional requirements a customer should be able to perform operations as follows:

1. Check balance: To be able to see the funds in the bank account.

2. Deposit cash: To be able to deposit cash in the ATM.

3. Withdraw cash: To be able to withdraw funds in bank account through the ATM.

4. Transfer funds: To be able to transfer funds to another bank account

5. Deposit check: To be able to deposit check in the ATM.

6. The user can have two types of account 1. Savings account 2. Current account. In both the cases the user should be able to do the operations.

#### Non-functional requirements an ATM is expected to deliver:

1. The ATM will serve one customer at a time and should not shut down while serving.

2. To begin a transaction user shall insert the card inside card reader. Card reader shall read the metaData from the ATM card

3. Then, the user should enter their Personal Identification Number (PIN) for authentication through keypad.

4. The ATM will send user's information to bank for authentication; only after authentication a session shall be established in which a transaction can be made.

5. session can be either terminated by user's request or by ATM if the requested transaction service is delivered. Card shall only be ejected out when the session is ended.

6. The user deposits will not reflect to their account balance immediately because it will be subject to verification by the bank.



## The main components of the ATM that will affect interactions between the ATM and its users are:

1. Card reader: to read the users’ metaData from ATM cards.

2. Keypad: to enter information into the ATM e.g., PIN, card's number, amount etc...,

3. Screen: to display messages to the users.

4. Cash dispenser: for dispensing cash.

5. Deposit slot: For users to deposit cash or checks.

6. Printer: for printing receipts.

7. Communication/Network Infrastructure: it is assumed that the ATM has a communication infrastructure to communicate with the bank upon any transaction or activity. This means there are two JVM's one in the ATM and one in the bank server. 



## State Pattern

Use if state pattern is helpful when there are multiple states in a design and the behaviour of operations in each state is different. Here there are three states

1. InactiveSession: when there is no session currently active. customer can request for a session by inserting card.

2. SessionRequested: when a session is requested and authentication is pending from the bank then it enters this state. No operation can be performed in this state.

3. ActiveSession: In this session customer should be able to perform a transaction or end the session.

Depending upon the state of the stateMangaer the operations behaviour will vary. these are the three operations

1. To request for a session; 

2. To end a session;  

3. To perform a transaction.



## Factory Pattern and command pattern

1. Depending upon the input Enum from customer about which transaction to perform the TransactionFatory creates the respective Transaction object.

2. check balance, deposit cash, deposit check, withdraw cash, transfer funds are the five classes that implement Transaction interface.

3. Transaction interface implements "execute" method all the objects that implement Transaction interface have the behaviour of this execute method defined differently.

4. the ATM machine has to trigger a "execute" method on the Transaction object returned by transaction factory. The actual implementation of it is then delegated to that respective Transaction

 

## Singleton Pattern

There are many classes that require to be a single object. Their unique instance objects are created in different JVM's

1. RMI_ATM_servicesHandlerC: It returns the look up address of the BankServices object of a customer. So, all these objects address shall be maintained in a monolith server and it should be a singleton class.

2. AuthManager: Authentication manager will have list of registered Pin's for all the registered accounts; thus, it should be a singleton. We can make it not to be singleton if we intend to shard the data of customers across the servers but then still the handler which decides which server should get to decide about a customer will have to be singleton.

3. BankingServices: As we don't want to have any inconsistency in the bank balance because two threads can use this object while we perform a transaction simultaneously using UPI and ATM, hence this object is necessary to a singleton

4. StateManager: Only one state manager that hold the states and operates on a state. If we had two StateManager and each having different state then it is not an ideal situation for a customer using the ATM.

5. TransactionFactory: The factory that decides which transaction object should be created. It is singleton because a customer should be able to perform a transaction one at a time.

6. ATM: Only one instance object of the central system which services the request of the user.

7. An ATM has only one component of these: CardReader, Screen, Keypad, CashDispenser, Printer, CheckDepositSlot, CashDepositSlot.

 

## Proxy Pattern

1. As both ATM machine and Bank server have different JVM's and the objects needed for authentication and BankingServices are in different JVM i.e., in the bank server and we need to perform certain operations on an object in another JVM across the network. Remote Proxy pattern is the ideal solution for such scenarios.

2. RMI_ATM_servicesHandlerC also implements remote proxy so that the ATM can fetch the LookUp path of the customer's banking services object. Depending upon the scale of the customers of a bank, the customers might be sharded into various servers, so to get the remote proxy's stub we need to perform the lookup operation using this path/IP address.



## The code flow for withdrawing cash from the ATM

The state is inactive initially. the customer requests for an active session to the state manager. state manager triggers request session on inactive state and makes the current state to session requested state. The card is inserted into card reader it reads the metadata from the card. Card reader will call the remote authentication manager to validate the session. remote authentication manager will call the screen to display enter pin message. remote authentication manager will call the keypad to read and return the pin entered by the user. remote authentication manager will in turn trigger Authentication manager's proxy to validate the pin by passing the metadata and the pin. This action happens across the network. The actual validation takes place at the other end in bank server's JVM and the acknowledgement to establish a session or not is sent back across the network. once the valid session acknowledgement reaches the ATM the remote authentication manager will call the state manager to change the state to active state. remote authentication manager will call the ATM to get the ATMServicesProxy of that particular customer. to get ATMServicesProxy we need the registered path of the actual object, that path is again fetched from another proxy object RMI_ATM_servicesHandlerProxy. RMI_ATM_servicesHandlerProxy's actual object in bank server will maintain the registered paths of all the ATMServices object. Once ATM gets that path it gets the ATMServicesProxy of the customer. ATM will call TransactionFactory to create a transaction object depending upon the transaction enum the customer has selected. Say customer wanted to with draw cash then WithdrawCash object is created. Once ATM call's execute method on this transaction object the object will take care of its implementation. It will call the screen to display a message saying "what is the amount you wish to withdraw" then calls keypad to read the amount and then triggers a withdraw cash method on ATMServicesProxy. ATMServicesProxy will send the request across the network the actual ATMServices object will do all the necessary checks and necessary changes to the bank account. If funds are available, it will send positive acknowledgement. WithdrawCash object will then call cash dispenser to dispense the cash. once the service is delivered to the customer, we clear the ATMServicesProxy object of the current customer, end the current session and make the state back to inactive state.
