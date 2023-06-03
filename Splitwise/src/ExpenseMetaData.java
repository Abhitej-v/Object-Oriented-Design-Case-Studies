package OODPracticeExample.Splitwise;

import java.util.ArrayList;

public class ExpenseMetaData {
    private User payer;
    private ArrayList<User> splitAmongUser = new ArrayList<User>();
    private SplitFormat splitFormat;
    private int numOfUsers;
    private ArrayList<Double> expenseBreakUp;
    private double expense;

    public ExpenseMetaData(User payer, ArrayList<User> splitAmongUser, SplitFormat splitFormat, int numOfUsers, ArrayList<Double> expenseBreakUp, double expense) {
        this.payer = payer;
        this.splitAmongUser = splitAmongUser;
        this.splitFormat = splitFormat;
        this.numOfUsers = numOfUsers;
        this.expenseBreakUp = expenseBreakUp;
        this.expense = expense;
    }

    public User getPayer() {
        return payer;
    }

    public void setPayer(User payer) {
        this.payer = payer;
    }

    public ArrayList<User> getSplitAmongUser() {
        return splitAmongUser;
    }

    public void setSplitAmongUser(ArrayList<User> splitAmongUser) {
        this.splitAmongUser = splitAmongUser;
    }

    public SplitFormat getSplitFormat() {
        return splitFormat;
    }

    public void setSplitFormat(SplitFormat splitFormat) {
        this.splitFormat = splitFormat;
    }

    public int getNumOfUsers() {
        return numOfUsers;
    }

    public void setNumOfUsers(int numOfUsers) {
        this.numOfUsers = numOfUsers;
    }

    public ArrayList<Double> getExpenseBreakUp() {
        return expenseBreakUp;
    }

    public void setExpenseBreakUp(ArrayList<Double> expenseBreakUp) {
        this.expenseBreakUp = expenseBreakUp;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }
}
