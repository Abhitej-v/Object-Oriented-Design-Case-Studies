package OODPracticeExample.ATM;

// Design patterns used: singleTon, factory, command, remoteProxy, State

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

