package OODPracticeExample.Splitwise;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private volatile static UserManager userMgrUnqInst;
    private HashMap<String, User> userIdMap = new HashMap<String, User>();
    private UserManager(){}
    public static UserManager getUserMgrUnqInst() {
        if (userMgrUnqInst == null) {
            synchronized (UserManager.class) {
                if (userMgrUnqInst == null) {
                    userMgrUnqInst = new UserManager();
                }
            }
        }
        return userMgrUnqInst;
    }

    public User getUser(String userId) {
        return userIdMap.get(userId);
    }

    public void show(String userId) {
        User user = userIdMap.get(userId);
        boolean ack=user.displayFullLedger();
        if (!ack){
            System.out.println("No balances");
        }
    }

    public void show() {
        boolean ack=false;
        for (Map.Entry<String, User> userRecord : userIdMap.entrySet()) {
            User user = userRecord.getValue();
            boolean acknowledge=user.displayLiabilitiesLedger();
            ack=ack||acknowledge;
        }
        if(!ack){
            System.out.println("No balances");
        }
    }

    public void addUser(User user) {
        userIdMap.put(user.getUserId(), user);
    }
}
