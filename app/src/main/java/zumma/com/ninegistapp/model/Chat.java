package zumma.com.ninegistapp.model;

/**
 * Created by Okafor on 19/02/2015.
 */
public class Chat {

    private String userId;
    private long time;
    private String message;

    public long getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public String getUserId() {
        return userId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
