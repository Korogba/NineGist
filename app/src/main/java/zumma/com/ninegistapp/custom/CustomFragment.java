package zumma.com.ninegistapp.custom;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class CustomFragment extends Fragment
        implements OnClickListener {
    private static final String THEME = "appTheme";
    public static final int THEME_GRAY = 2;
    public static final int THEME_RED = 3;
    public static final int THEME_WHITE = 1;
    private static final String TAG = CustomFragment.class.getSimpleName();

    protected int getAppTheme() {
        return PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt("appTheme", 3);
    }

    public void onClick(View paramView) {
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        Log.d(TAG, "am at onCreate");
        return super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    }

    protected void saveAppTheme(int paramInt) {
        PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putInt("appTheme", paramInt).commit();
    }

    public View setTouchNClick(View paramView) {
        paramView.setOnClickListener(this);
//        paramView.setOnTouchListener(CustomActivity.TOUCH);
        return paramView;
    }
}
