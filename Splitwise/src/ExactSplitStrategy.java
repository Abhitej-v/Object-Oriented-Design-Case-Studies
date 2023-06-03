package OODPracticeExample.Splitwise;

import java.util.ArrayList;

public class ExactSplitStrategy implements SplitStrategy {

    @Override
    public void distributeExpense(ExpenseMetaData expenseMetaData) {
        double amount;
        ArrayList<Double> expenseBreakUp = expenseMetaData.getExpenseBreakUp();
        if (isValid(expenseMetaData.getExpense(), expenseBreakUp)) {
            ArrayList<User> splitAmongstList = expenseMetaData.getSplitAmongUser();
            for (int i = 0; i < splitAmongstList.size(); i++) {
                amount = expenseBreakUp.get(i);
                updateLedger(expenseMetaData.getPayer(), splitAmongstList.get(i), amount);
            }
        } else {
            System.out.println("Invalid exact expense breakup inputs");
        }
    }

    private boolean isValid(double expense, ArrayList<Double> expenseBreakUp) {
        Double amountTracker = 0.00;
        for (Double amount : expenseBreakUp) {
            amountTracker += amount;
        }
        boolean isEqual = amountTracker.equals(expense);
        System.out.println("amountTracker : " + amountTracker + " expense : " + expense + " isEqual : " + isEqual);
        return isEqual;
    }
}
