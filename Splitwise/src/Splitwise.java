package OODPracticeExample.Splitwise;


public class Splitwise {
    public static void main(String[] args) {
        User user1=new User("u1", "Abhinav", "abhinav@gmail.com", 123456);
        User user2=new User("u2", "Ankita", "ankita@gmail.com", 123456);
        User user3=new User("u3", "Abhay", "abhay@gmail.com", 123456);
        User user4=new User("u4", "Arjun", "arjun@gmail.com", 123456);
        UserManager userManager=UserManager.getUserMgrUnqInst();
        userManager.show();
        userManager.show("u1");
        user1.shareExpense();
        userManager.show("u4");
        userManager.show("u1");
        user1.shareExpense();
        userManager.show();
        user4.shareExpense();
        userManager.show("u1");
        userManager.show();
    }
}
