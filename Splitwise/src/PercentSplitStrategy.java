package OODPracticeExample.Splitwise;

import java.util.ArrayList;

public class PercentSplitStrategy implements SplitStrategy {

    @Override
    public void distributeExpense(ExpenseMetaData expenseMetaData) {
        ArrayList<Double> expenseBreakUp = expenseMetaData.getExpenseBreakUp();
        if (isValid(expenseBreakUp)) {
            double roundedUp;
            double amount;
            double expense = expenseMetaData.getExpense();
            ArrayList<User> splitAmongstList = expenseMetaData.getSplitAmongUser();
            for (int i = 0; i < splitAmongstList.size(); i++) {
                amount = (expense * expenseBreakUp.get(i)) / 100.00;
                roundedUp = Math.ceil(amount * 100) / 100.00;
                updateLedger(expenseMetaData.getPayer(), splitAmongstList.get(i), roundedUp);
            }
        } else {
            System.out.println("invalid expense breakup percentage input");
        }
    }

    private boolean isValid(ArrayList<Double> expenseBreakUp) {
        Double percentTracker = 0.00;
        for (Double percent : expenseBreakUp) {
            percentTracker += percent;
        }
        boolean isEqual = percentTracker.equals(100.00);
        System.out.println("percentTracker : " + percentTracker + " isEqual : " + isEqual);
        return isEqual;
    }
}
