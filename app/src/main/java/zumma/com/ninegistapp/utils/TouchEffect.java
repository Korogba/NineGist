package zumma.com.ninegistapp.utils;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class TouchEffect
        implements OnTouchListener {

    public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
        if (paramMotionEvent.getAction() == 0) {
            paramView.setAlpha(0.7F);
            return false;
        }
        else if((paramMotionEvent.getAction() != 1) && (paramMotionEvent.getAction() != 3)){
            paramView.setAlpha(0.7F);
            return false;
        }

        else{
            paramView.setAlpha(1.0F);
            return false;
        }



//        while (true) {
//            if ((paramMotionEvent.getAction() != 1) && (paramMotionEvent.getAction() != 3))
//                continue;
//            paramView.setAlpha(1.0F);
//            return false;
//        }
    }
}
