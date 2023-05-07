package OODPracticeExample.ATM;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BankingServices extends UnicastRemoteObject implements ATMServices, EBankingServices {
    BankAccount bankAccount;

    public BankingServices() throws RemoteException {
    }

    @Override
    public boolean depositCash(long amount) {
        CashDepositManager cashDepositManager = CashDepositManager.getUnqCashDepositMngr();
        DepositInfoLog depositInfoLog = new DepositInfoLog(bankAccount, amount);
        cashDepositManager.addToProcessingItems(depositInfoLog);
        return true;
    }

    @Override
    public boolean depositCheck(long amount) {
        CheckDepositManager checkDepositManager = CheckDepositManager.getUnqCheckDepositMngr();
        DepositInfoLog depositInfoLog = new DepositInfoLog(bankAccount, amount);
        checkDepositManager.addToProcessingItems(depositInfoLog);
        return true;
    }

    @Override
    public boolean withdrawCash(long amount) {
        if (bankAccount.getBalance() > amount) {
            return bankAccount.decrementBalance(amount);
        } else {
            return false;
        }
    }

    @Override
    public boolean transferFunds(long recipientBankAccNum, long amount) {
        // use this recipientBankAccNum and query the DB to get it recipientBankAcc's service object
        BankAccount recipientBankAcc = new BankAccount(); // this object is usually fetched from DB.
        boolean ack1 = false;
        boolean ack2 = false;
        if (bankAccount.getBalance() > amount) {
            ack1 = bankAccount.decrementBalance(amount);
            if (ack1) {
                ack2 = recipientBankAcc.incrementBalance(amount);
                if (ack2 == false) {
                    // revert the first operation
                    boolean ack3 = bankAccount.incrementBalance(amount);
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
