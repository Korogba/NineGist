package zumma.com.ninegistapp.model;

import java.util.List;

/**
 * Created by Kaba Yusuf on 2/16/2015.
 */
public class Friend {

    private String name;
    private String phone_number;
    private String status;

    private List<Conversation> conversations;

    public Friend(String name, String phone_number, List<Conversation> conversations) {
        this.name = name;
        this.phone_number = phone_number;
        this.conversations = conversations;
        this.status = "Hey, am on Ninegist";
    }

    public Friend(String name, String phone_number) {
        this.name = name;
        this.phone_number = phone_number;
        this.status = "Hey, am on Ninegist";
    }

    public String getName() {
        return name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
