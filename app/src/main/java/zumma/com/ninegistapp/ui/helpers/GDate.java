package zumma.com.ninegistapp.ui.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Okafor on 27/06/2014.
 */
public class GDate {

    private Date dNow = new Date( );
    private SimpleDateFormat ft = new SimpleDateFormat("hh:mm");

    private String current_time;

    public String getCurrent_time() {
        String time = ft.format(dNow);
        return time;
    }

    public long getTimeStamp(){
        return dNow.getTime();
    }
}
