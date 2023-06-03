package OODPracticeExample.Splitwise;

public interface SplitStrategy {
    public void distributeExpense(ExpenseMetaData expenseMetaData);

    default void updateLedger(User payer, User lender, double amount) {
        payer.updateLedger(lender, amount);
        lender.updateLedger(payer, 0.00 - amount);
    }
}
