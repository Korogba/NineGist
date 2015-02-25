package zumma.com.ninegistapp.ui.helpers;

import android.graphics.Color;
import android.text.format.DateUtils;

import java.util.Date;
import java.util.Random;

/**
 * Created by Okafor on 30/12/2014.
 */
public class FriendsUtilHelper {

    private static final String TAG = FriendsUtilHelper.class.getSimpleName();

    public String capitaliseFirst(String input){
        String output = input.substring(0, 1).toUpperCase() + input.substring(1);
        return output;
    }


    public String formatChatTime(String time){
        Date createdAt = new Date(Long.parseLong(time));
        long now = new Date().getTime();
        String convertedDate = DateUtils.getRelativeTimeSpanString(
                createdAt.getTime(),
                now,
                DateUtils.SECOND_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_RELATIVE

        ).toString();

        return convertedDate;
    }

    public int getFriendProfileColor(){
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        return color;
    }
}
