package OODPracticeExample.ATM;

// Design patterns used in this design: singleTon, factory, command, remoteProxy, State

import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.rmi.*;

//Enums
public enum TransactionType {
    CheckBalance,
    WithdrawCash,
    DepositCheck,
    DepositCash,
    TransferFunds
}
public enum TransactionStatus{
    Processing,
    Successful,
    Failed,
    Disputed,
    None
}

public class Card{
    MetaData metaData;
    String serviceOperator; // Rupay, MasterCard, Visa etc...,
}

public class MetaData{
    String cardHolderName;
    Date expiry;
    long cardNum;
}

public class Printer{

    public static Printer getPrinterUnqInst() {
        // implement singleton pattern
        return new Printer();
    }

    public void printReceipt(Transaction transaction) {
        // get all the info from the transaction object and run the routines to print the information
    }
}

public class BankAccount{
    String customerName;
    String verificationIdNum;
    long accountNumber;
    String branchAddress;
    long balance;
    public boolean decrementBalance(long amount){
        long currBal=getBalance();
        return updateBalance(currBal-amount);
    }
    public boolean incrementBalance(long amount){
        long currBal=getBalance();
        return updateBalance(currBal+amount);
    }

    //@Transactional;
    public boolean updateBalance(long amount){
        this.balance=amount;
        return true;
    }

    public long getBalance() {
        return balance;
    }
}

public class SavingsBankAccount extends BankAccount{}
public class CurrentBankAccount extends BankAccount{}

public class Customer {
    BankAccount bankAccount;
    Card card;
    public boolean insertCard(){
        StateManager stateManager = StateManager.getStateMngrUnqInst();
        return stateManager.requestSession();
    }
    public boolean performTransaction(TransactionType transactionType){
        StateManager stateManager = StateManager.getStateMngrUnqInst();
        return stateManager.performTransaction(transactionType);
    }
    public void endSession(){
        StateManager stateManager = StateManager.getStateMngrUnqInst();
        stateManager.endSession();
    }
}

public class StateManager{
    State state;
    State inactiveSessionState;
    State sessionRequestedState;
    State activeSession;
    static volatile StateManager stateMngrUnqInst;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean requestSession(){
        return state.requestSession();
    }


    private StateManager(){
        this.inactiveSessionState=new InactiveSessionState();
        this.sessionRequestedState= new SessionRequestedState();
        this.activeSession= new ActiveSessionState();
        setState(this.inactiveSessionState);
    }
    public static StateManager getStateMngrUnqInst(){
        if(stateMngrUnqInst==null){
            synchronized (StateManager.class){
                if (stateMngrUnqInst==null){
                    stateMngrUnqInst=new StateManager();
                }
            }
        }
        return stateMngrUnqInst;
    }
    public boolean performTransaction(TransactionType transactionType) {
        return state.performTransaction(transactionType);
    }

    public void endSession() {
        state.endSession();
    }
}

public interface State{
    boolean requestSession();
    boolean performTransaction(TransactionType transactionType);
    void endSession();
}

public class InactiveSessionState implements State{

    @Override
    public boolean requestSession() {
        CardReader cardReader = CardReader.getUniqueCardReaderInstance();
        StateManager stateManager=StateManager.getStateMngrUnqInst();
        stateManager.setState(stateManager.sessionRequestedState);
        return cardReader.readCardDetails();
    }

    @Override
    public boolean performTransaction(TransactionType transactionType) {
        System.out.println("Session is not active, cant perform any transaction");
        return false;
    }

    @Override
    public void endSession() {
        System.out.println("No active session to end");
    }
}

public class SessionRequestedState implements State{
    @Override
    public boolean requestSession() {
        System.out.println("A session request is already in progress");
        return false;
    }

    @Override
    public boolean performTransaction(TransactionType transactionType) {
        System.out.println("Session is not active, cant perform any transaction");
        return false;
    }

    @Override
    public void endSession() {
        System.out.println("No active session to end");
    }
}

public class ActiveSessionState implements State{

    public ActiveSessionState() {
    }

    @Override
    public boolean requestSession() {
        System.out.println("A session is already active, kindly the current session to start a new session");
        return false;
    }

    @Override
    public boolean performTransaction(TransactionType transactionType){
        ATM atm = ATM.getATMUniqueInstance();
        return atm.performTransaction(transactionType);
    }

    @Override
    public void endSession(){
        ATM atm=ATM.getATMUniqueInstance();
        atm.clearATMServicesProxy();
        CardReader cardReader = CardReader.getUniqueCardReaderInstance();
        cardReader.ejectCard();
    }
}

public class CardReader{
    RemoteAuthManager authMngr;
    static volatile CardReader cardReaderUniqueInstance;
    public boolean readCardDetails(){
        // read the metadata from the magnetic strip of an ATM card
        MetaData metaData=new MetaData(); // for simplicity and practical reasons.
        return authMngr.validateSession(metaData);
    }

    public void ejectCard(){
        // run routines to eject the card from card reader
        System.out.println("Please collect your card");
    }
    public static CardReader getUniqueCardReaderInstance(){
        if (cardReaderUniqueInstance==null){
            synchronized (CardReader.class){
                if (cardReaderUniqueInstance==null){
                    cardReaderUniqueInstance=new CardReader();
                }
            }
        }
        return cardReaderUniqueInstance;
    }
    private CardReader(){
        this.authMngr=RemoteAuthManager.getUnqRemoteAuthMngrInst();
    }
}

public class RemoteAuthManager{
    ValidatePin authManagerProxy;
    static volatile RemoteAuthManager remoteAuthManagerUnqInst;
    public boolean validateSession(MetaData metaData){
        Screen screen = Screen.getScreenUniqueInstance();
        screen.displayEnterPin();
        Keypad keypad= Keypad.getKeypadUniqueInstance();
        int enteredPin = keypad.getPin();
        try {
            boolean ack = authManagerProxy.validatePin(metaData, enteredPin);
            if (ack) {
                ATM atm = ATM.getATMUniqueInstance();
                atm.lookUpATMServicesProxy(metaData);
                changeToActiveState();
            }
            return ack;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    private void changeToActiveState(){
        StateManager stateManager= StateManager.getStateMngrUnqInst();
        stateManager.setState(stateManager.activeSession);
    }
    public static RemoteAuthManager getUnqRemoteAuthMngrInst(){
        if (remoteAuthManagerUnqInst==null){
            synchronized (RemoteAuthManager.class){
                if (remoteAuthManagerUnqInst==null){
                    remoteAuthManagerUnqInst=new RemoteAuthManager();
                }
            }
        }
        return remoteAuthManagerUnqInst;
    }
    private RemoteAuthManager(){
        try {
            this.authManagerProxy = (ValidatePin) Naming.lookup("rmi://127.0.0.1/AuthManager");
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}

public interface ValidatePin extends Remote{
    boolean validatePin(MetaData metaData, int pin) throws RemoteException;
}
public class AuthManager extends UnicastRemoteObject implements ValidatePin{
    private static final long serialVersionId=1L;
    HashMap<Long, Integer> cardNumPinMap= new HashMap<Long, Integer>();
    static volatile AuthManager authManagerUnqInst;
    public static AuthManager getAuthUnqInst() throws RemoteException {
        // implement singleton pattern for auth manager
        return new AuthManager(); // to avoid warnings
    }
    private AuthManager() throws RemoteException{}

    @Override
    public boolean validatePin(MetaData metaData, int pin) {
        int registeredPin=cardNumPinMap.get(metaData.cardNum);
        if (registeredPin==pin){
            return true;
        }
        else{
            return false;
        }
    }
}

public class Screen{
    static volatile Screen screenUniqueInstance;

    public void displayEnterPin(){
        System.out.println(" enter your verification pin");
    }
    public void displayEnterAmount(){
        System.out.println("enter the amount of the transaction");
    }
    public boolean showAmount(long amount){
        // display the given amount on the screen
        return true;
    }

    public void showSuccessMsg(){
        System.out.println("Transaction is successful");
    }
    public void showFailureMsg(){
        System.out.println("Transaction has failed");
    }

    public static Screen getScreenUniqueInstance(){
        if (screenUniqueInstance==null){
            synchronized (Screen.class){
                if(screenUniqueInstance==null){
                    screenUniqueInstance=new Screen();
                }
            }
        }
        return screenUniqueInstance;
    }
    private Screen(){}
}

public class Keypad{
    static volatile Keypad keypadUniqueInstance;
    public int getPin(){
        Scanner sc= new Scanner(System.in);
        return sc.nextInt();
    }
    public int getAmount(){
        Scanner sc= new Scanner(System.in);
        return sc.nextInt();
    }
    public static Keypad getKeypadUniqueInstance(){
        if (keypadUniqueInstance==null){
            synchronized (Keypad.class){
                if (keypadUniqueInstance==null){
                    keypadUniqueInstance=new Keypad();
                }
            }
        }
        return keypadUniqueInstance;
    }
    private Keypad(){}

    public long getRecipientAccNum() {
        Scanner sc= new Scanner(System.in);
        long recipientAccNum=sc.nextLong();
        return recipientAccNum;
    }
}

public class ATM{
    RMI_ATM_servicesHandler rmiAtmServicesHandlerProxy;
    ATMServices atmServicesProxy;
    static volatile ATM atmUniqueInstance;


    public static ATM getATMUniqueInstance(){
        if (atmUniqueInstance==null){
            synchronized (ATM.class){
                if (atmUniqueInstance==null){
                    atmUniqueInstance=new ATM();
                }
            }
        }
        return atmUniqueInstance;
    }

    private ATM(){
        try{
            this.rmiAtmServicesHandlerProxy= (RMI_ATM_servicesHandler) Naming.lookup("rmi://127.0.0.1/RMI_ATM_servicesHandlerC");
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void lookUpATMServicesProxy(MetaData metaData) throws RemoteException {
        String path= rmiAtmServicesHandlerProxy.getATMServicesProxy(metaData);
        try{
            atmServicesProxy=(ATMServices) Naming.lookup("rmi://127.0.0.1/"+path);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void clearATMServicesProxy(){
        atmServicesProxy=null;
    }
    public ATMServices getATMServicesProxy(){
        if (atmServicesProxy!=null){
            return this.atmServicesProxy;
        }
        else{
            throw new NoSuchElementException("The requested ATMServicesProxy doesn't exist");
        }
    }
    public boolean performTransaction(TransactionType transactionType) {
        Transaction transaction=TransactionFactory.createTransaction(transactionType);

        boolean ack=transaction.execute();
        if (ack){
            // call the setter method for changing transactionStatus to successful for the respective transaction object
            clearATMServicesProxy();
            StateManager stateManager=StateManager.getStateMngrUnqInst();
            CardReader cardReader=CardReader.getUniqueCardReaderInstance();
            Printer printer = Printer.getPrinterUnqInst();
            printer.printReceipt(transaction);
            stateManager.setState(StateManager.stateMngrUnqInst.inactiveSessionState);
            cardReader.ejectCard();
        }
        else {
            // call the setter method for changing transactionStatus to failed for the respective transaction object
        }
        return ack;
    }
}
public class TransactionFactory{
    public static Transaction createTransaction(TransactionType transactionType){
        Transaction transaction=null;
        if (transactionType==TransactionType.DepositCash){
            transaction=new DepositCash();
        } else if (transactionType==TransactionType.TransferFunds) {
            transaction=new TransferFunds();
        } else if (transactionType==TransactionType.CheckBalance) {
            transaction=new CheckBalance();
        } else if (transactionType==TransactionType.DepositCheck) {
            transaction=new DepositCheck();
        } else if (transactionType==TransactionType.WithdrawCash) {
            transaction=new WithdrawCash();
        } else {
            System.out.println("Not a valid transaction");
        }
        return transaction;
    }
}
public interface Transaction{
    boolean execute();
}
public interface Deposit extends Transaction{
    // can add any generic attributes or methods
}
public class DepositCheck implements Deposit{
    TransactionStatus transactionStatus;

    @Override
    public boolean execute() {
        this.transactionStatus=TransactionStatus.Processing;
        Screen screen= Screen.getScreenUniqueInstance();
        screen.displayEnterAmount();
        Keypad keypad=Keypad.getKeypadUniqueInstance();
        long amount= keypad.getAmount();
        ATM atm=ATM.getATMUniqueInstance();
        ATMServices atmServices=atm.getATMServicesProxy();
        return atmServices.depositCheck(amount);
    }
}
public class DepositCash implements Deposit{
    TransactionStatus transactionStatus;

    @Override
    public boolean execute() {
        this.transactionStatus=TransactionStatus.Processing;
        Screen screen= Screen.getScreenUniqueInstance();
        screen.displayEnterAmount();
        Keypad keypad=Keypad.getKeypadUniqueInstance();
        long amount= keypad.getAmount();
        ATM atm=ATM.getATMUniqueInstance();
        ATMServices atmServices=atm.getATMServicesProxy();
        return atmServices.depositCash(amount);
    }
}
public class CheckBalance implements Transaction{
    TransactionStatus transactionStatus;

    @Override
    public boolean execute() {
        this.transactionStatus=TransactionStatus.Processing;
        Screen screen= Screen.getScreenUniqueInstance();
        ATM atm=ATM.getATMUniqueInstance();
        ATMServices atmServices=atm.getATMServicesProxy();
        long balance=atmServices.checkBalance();
        return screen.showAmount(balance);
    }
}
public class WithdrawCash implements Transaction{
    TransactionStatus transactionStatus;

    @Override
    public boolean execute() {
        this.transactionStatus=TransactionStatus.Processing;
        Screen screen= Screen.getScreenUniqueInstance();
        screen.displayEnterAmount();
        Keypad keypad=Keypad.getKeypadUniqueInstance();
        long amount= keypad.getAmount();
        ATM atm=ATM.getATMUniqueInstance();
        ATMServices atmServices=atm.getATMServicesProxy();
        boolean ack=atmServices.withdrawCash(amount);
        if (ack){
            CashDispenser cashDispenser=CashDispenser.getCashDispenserUnqInst();
            return cashDispenser.dispenseCash(amount);
        }
        else {
            System.out.println("Transaction has failed");
            return false;
        }
    }
}
public class TransferFunds implements Transaction{
    TransactionStatus transactionStatus;

    @Override
    public boolean execute() {
        this.transactionStatus=TransactionStatus.Processing;
        Screen screen= Screen.getScreenUniqueInstance();
        screen.displayEnterAmount();
        Keypad keypad=Keypad.getKeypadUniqueInstance();
        long amount= keypad.getAmount();
        long recipientAccNum= keypad.getRecipientAccNum();
        ATM atm=ATM.getATMUniqueInstance();
        ATMServices atmServices=atm.getATMServicesProxy();
        return atmServices.transferFunds(recipientAccNum,amount);
    }
}

public class CashDispenser{

    public static CashDispenser getCashDispenserUnqInst() {
        // implement singleton pattern here
        return new CashDispenser();
    }

    public boolean dispenseCash(long amount) {
        // run the  routines to count and dispense the cash
        return true;
    }
}
public interface RMI_ATM_servicesHandler extends Remote{
    String getATMServicesProxy(MetaData metaData) throws RemoteException;
}

public class RMI_ATM_servicesHandlerC extends UnicastRemoteObject implements RMI_ATM_servicesHandler{
    HashMap<Long, String> cardNumProxyLookUpAdrsMap = new HashMap<Long, String>();
    private static final long serialVersionId=2L;
    static volatile RMI_ATM_servicesHandlerC rmiAtmServicesHandlerUnqInst;
    @Override
    public String getATMServicesProxy(MetaData metaData){
        return cardNumProxyLookUpAdrsMap.get(metaData.cardNum);
    }
    public static RMI_ATM_servicesHandlerC getRmiAtmServicesHandlerUnqInst() throws RemoteException {
        if (rmiAtmServicesHandlerUnqInst==null){
            synchronized (RMI_ATM_servicesHandlerC.class){
                if (rmiAtmServicesHandlerUnqInst==null){
                    rmiAtmServicesHandlerUnqInst=new RMI_ATM_servicesHandlerC();
                }
            }
        }
        return rmiAtmServicesHandlerUnqInst;
    }
    private RMI_ATM_servicesHandlerC() throws RemoteException {}
}

public interface EBankingServices{
    // add the EBanking services methods like UPI, NIFT etc
}

public interface ATMServices extends Remote{
    boolean depositCash(long amount);
    boolean depositCheck(long amount);
    boolean withdrawCash(long amount);
    boolean transferFunds(long recipientBankAccNum, long amount);
    long checkBalance();
}

public class BankingServices extends UnicastRemoteObject implements ATMServices, EBankingServices{
    BankAccount bankAccount;

    @Override
    public boolean depositCash(long amount) {
        CashDepositManager cashDepositManager= CashDepositManager.getUnqCashDepositMngr();
        DepositInfoLog depositInfoLog= new DepositInfoLog(bankAccount, amount);
        cashDepositManager.addToProcessingItems(depositInfoLog);
        return true;
    }

    @Override
    public boolean depositCheck(long amount) {
        CheckDepositManager checkDepositManager = CheckDepositManager.getUnqCheckDepositMngr();
        DepositInfoLog depositInfoLog= new DepositInfoLog(bankAccount, amount);
        checkDepositManager.addToProcessingItems(depositInfoLog);
        return true;
    }

    @Override
    public boolean withdrawCash(long amount) {
        if (bankAccount.getBalance()>amount){
            return bankAccount.decrementBalance(amount);
        }
        else {
            return false;
        }
    }

    @Override
    public boolean transferFunds(long recipientBankAccNum, long amount) {
        // use this recipientBankAccNum and query the DB to get it recipientBankAcc's service object
        BankAccount recipientBankAcc=new BankAccount(); // this object is usually fetched from DB.
        boolean ack1=false;
        boolean ack2=false;
        if (bankAccount.getBalance()>amount){
            ack1=bankAccount.decrementBalance(amount);
            if(ack1){
                ack2=recipientBankAcc.incrementBalance(amount);
                if(ack2==false){
                    // revert the first operation
                    boolean ack3=bankAccount.incrementBalance(amount);
                }
            }
        }
        return ack2;
    }

    @Override
    public long checkBalance() {
        return bankAccount.getBalance();
    }
}

public class DepositInfoLog{
    BankAccount bankAccount;
    long amount;
    public DepositInfoLog(BankAccount bankAccount, long amount) {
        this.bankAccount=bankAccount;
        this.amount=amount;
        // this log item can contain more information than just two attributes. this is just an example
    }
}

public class CashDepositManager{
    ArrayList<DepositInfoLog> processingItems = new ArrayList<DepositInfoLog>();

    public static CashDepositManager getUnqCashDepositMngr() {
        // implement singleton pattern
        return new CashDepositManager();
    }

    public void addToProcessingItems(DepositInfoLog depositInfoLog) {
        processingItems.add(depositInfoLog);
    }
    public void processItems(){
        // as the items in the processingItems list gets processed the balance in the respective bank accounts gets updated
    }
}
public class CheckDepositManager{
    ArrayList<DepositInfoLog> processingItems = new ArrayList<DepositInfoLog>();

    public static CheckDepositManager getUnqCheckDepositMngr() {
        // implement singleton pattern
        return new CheckDepositManager();
    }

    public void addToProcessingItems(DepositInfoLog depositInfoLog) {
        processingItems.add(depositInfoLog);
    }
    public void processItems(){
        // as the items in the processingItems list gets processed the balance in the respective bank accounts gets updated
    }
}
