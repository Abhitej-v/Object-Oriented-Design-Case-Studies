package OODPracticeExample.ATM;

public class DepositInfoLog {
    BankAccount bankAccount;
    long amount;

    public DepositInfoLog(BankAccount bankAccount, long amount) {
        this.bankAccount = bankAccount;
        this.amount = amount;
        // this log item can contain more information than just two attributes. this is just an example
    }
}
