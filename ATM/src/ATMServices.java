package OODPracticeExample.ATM;

import java.rmi.Remote;

public interface ATMServices extends Remote {
    boolean depositCash(long amount);

    boolean depositCheck(long amount);

    boolean withdrawCash(long amount);

    boolean transferFunds(long recipientBankAccNum, long amount);

    long checkBalance();
}
