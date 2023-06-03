package OODPracticeExample.Splitwise;

import java.util.ArrayList;
import java.util.Scanner;

public class User {
    private String userId;
    private String name;
    private String emailId;
    private long phoneNum;
    private Ledger ledger;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User(String userId, String name, String emailId, long phoneNum) {
        this.userId = userId; // ideally a global unique Id generator algorithm shall be used.
        this.name = name;
        this.emailId = emailId;
        this.phoneNum = phoneNum;
        this.ledger= new Ledger(); // to edit
        UserManager userManager = UserManager.getUserMgrUnqInst();
        userManager.addUser(this);
    }

    public void shareExpense(){
        Scanner sc= new Scanner(System.in);
        System.out.println("Enter the total expense amount");
        Double expense= sc.nextDouble();
        System.out.println("Enter the number of users you want to split the bill with");
        int numOfUsers=sc.nextInt();
        ArrayList<User> splitAmongUser=new ArrayList<User>();
        UserManager userManager = UserManager.getUserMgrUnqInst();
        System.out.println("Enter their user Id's");
        for(int i=0; i<numOfUsers; i++){
            String UserId=sc.next();
            User user=userManager.getUser(UserId);
            splitAmongUser.add(user);
        }
        System.out.println("Choose a split format:-\n1, Equal\n2, Exact\n3, Percentage");
        String input=sc.next();
        SplitFormat splitFormat=null;
        try{
            splitFormat=SplitFormat.valueOf(input);
            System.out.println("chosen The split format is "+splitFormat);
        }
        catch (IllegalArgumentException e){
            System.out.println("Invalid Split Format");
            return;
        }

        ArrayList<Double>  expenseBreakUp=new ArrayList<Double>();
        if(splitFormat!=SplitFormat.Equal){
            System.out.println("Enter the expense breakup values.\nProvide inputs by rounding off till two decimals only.");
            for(int i=0; i<numOfUsers; i++){
                Double userShare= sc.nextDouble();
                expenseBreakUp.add(userShare);
            }
        }
        ExpenseMetaData expenseMetaData=new ExpenseMetaData(this,splitAmongUser,splitFormat,numOfUsers,expenseBreakUp,expense);

        SplitManager splitManager = SplitManager.getSplitMgrUniInst();
        splitManager.distributeExpense(expenseMetaData);
    }
    public void updateLedger(User user, Double amount){
        this.ledger.updateLedger(user,amount);
    }
    public boolean displayFullLedger(){
        return this.ledger.displayFullLedger(this.userId);
    }
    public boolean displayLiabilitiesLedger(){
        return this.ledger.displayLiabilities(this.userId);
    }
}

