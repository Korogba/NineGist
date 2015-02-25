package zumma.com.ninegistapp.pager;

import android.content.res.Resources;
import android.util.TypedValue;

public class Util {
    public static int dpToPx(Resources res, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
    }
}

/* Location:           C:\Users\Okafor\workspace\imate\iMate_dex2jar.jar
 * Qualified Name:     com.imate.pager.Util
 * JD-Core Version:    0.6.0
 */