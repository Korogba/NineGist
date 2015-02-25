package zumma.com.ninegistapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import zumma.com.ninegistapp.R;
import zumma.com.ninegistapp.custom.CustomFragment;


public class Settings extends CustomFragment {
    private View current;

    public void onClick(View paramView) {
        switch (paramView.getId()){
            case R.id.edit_profile:
                openSelectPicture();
                break;
            default:
                Toast.makeText(getActivity(), "Default", Toast.LENGTH_SHORT).show();
                break;
        }
//        super.onClick(paramView);
//        int i = 0;
//        if ((paramView.getId() == R.id.themeGray) || (paramView.getId() == R.id.themeWhite) || (paramView.getId() == R.id.themeRed)) {
//            this.current.setEnabled(true);
//            this.current = paramView;
//            this.current.setEnabled(false);
//            if (paramView.getId() != R.id.themeGray)
//                i = 2;
//
//        }
//        while (true) {
//            saveAppTheme(i);
//            if (paramView.getId() == R.id.themeWhite) {
//                i = 1;
//                continue;
//            }
//            i = 3;
//        }
    }

    private void openSelectPicture(){
        Intent intent = new Intent(getActivity(), zumma.com.ninegistapp.ui.activities.SelectPicture.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        View localView1 = paramLayoutInflater.inflate(R.layout.setting, null);
        View localView2 = setTouchNClick(localView1.findViewById(R.id.themeGray));
        View localView3 = setTouchNClick(localView1.findViewById(R.id.themeRed));
        View localView4 = setTouchNClick(localView1.findViewById(R.id.themeWhite));
        localView1.findViewById(R.id.edit_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectPicture();
            }
        });
        int i = getAppTheme();
        if (i == 2) {
            localView2.setEnabled(false);
            this.current = localView2;
            return localView1;
        }
        if (i == 1) {
            localView4.setEnabled(false);
            this.current = localView4;
            return localView1;
        }
        localView3.setEnabled(false);
        this.current = localView3;
        return localView1;
    }
}
