package OODPracticeExample.ATM;

public class BankAccount {
    String customerName;
    String verificationIdNum;
    long accountNumber;
    String branchAddress;
    long balance;

    public boolean decrementBalance(long amount) {
        long currBal = getBalance();
        return updateBalance(currBal - amount);
    }

    public boolean incrementBalance(long amount) {
        long currBal = getBalance();
        return updateBalance(currBal + amount);
    }

    //@Transactional;
    public boolean updateBalance(long amount) {
        this.balance = amount;
        return true;
    }

    public long getBalance() {
        return balance;
    }
}
