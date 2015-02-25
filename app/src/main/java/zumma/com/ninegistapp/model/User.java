package zumma.com.ninegistapp.model;

import java.util.HashMap;

/**
 * Created by Kaba Yusuf on 2/13/2015.
 */
public class User {

    private BasicInfo basicInfo;
    private HashMap<String, Friend> friends;

    public User() {}

    public User(BasicInfo basicInfo, HashMap<String, Friend> friends) {
        this.basicInfo = basicInfo;
        this.friends = friends;
    }

    public HashMap<String, Friend> getFriends() {
        return friends;
    }

    public void setFriends(HashMap<String, Friend> friends) {
        this.friends = friends;
    }

    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(BasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }
}
