package OODPracticeExample.Splitwise;

import java.util.ArrayList;

public class EqualSplitStrategy implements SplitStrategy {

    @Override
    public void distributeExpense(ExpenseMetaData expenseMetaData) {
        double roundedUp;
        ArrayList<User> splitAmongstList = expenseMetaData.getSplitAmongUser();
        double amount = expenseMetaData.getExpense() / (double) expenseMetaData.getNumOfUsers();
        for (int i = 0; i < splitAmongstList.size(); i++) {
            if (i == splitAmongstList.size() - 1) {
                roundedUp = Math.ceil(amount * 100) / 100.00;
            } else {
                roundedUp = Math.floor(amount * 100) / 100.00;
            }
            updateLedger(expenseMetaData.getPayer(), splitAmongstList.get(i), roundedUp);
        }
    }
}
