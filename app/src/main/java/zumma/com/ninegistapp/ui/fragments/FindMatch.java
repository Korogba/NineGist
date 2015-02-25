package zumma.com.ninegistapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import zumma.com.ninegistapp.R;
import zumma.com.ninegistapp.custom.CustomFragment;


public class FindMatch extends CustomFragment {

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        View localView1 = paramLayoutInflater.inflate(R.layout.find_match, null);
        View localView2 = setTouchNClick(localView1.findViewById(R.id.btnScan));
        View localView3 = setTouchNClick(localView1.findViewById(R.id.btnInvite));
        int i = getAppTheme();
        if (i == 2) {
            localView1.setBackgroundColor(getResources().getColor(R.color.gray_dk));
            localView3.setBackgroundColor(getResources().getColor(R.color.main_color_red));
            TextView localTextView3 = (TextView) localView1.findViewById(R.id.lblInvite);
//      localTextView3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lblReg, 0, 0, 0);
            localTextView3.setTextColor(getResources().getColor(R.color.white));
            localView1.findViewById(R.id.img).setBackgroundResource(R.drawable.rings_gray);

            return localView1;
        }

        localView1.setBackgroundColor(getResources().getColor(R.color.white));
        localView3.setBackgroundColor(getResources().getColor(R.color.main_color_red));
        localView2.setBackgroundResource(R.drawable.gray_border_rect);
        TextView localTextView1 = (TextView) localView1.findViewById(R.id.lblInvite);
//    localTextView1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lblInvite, 0, 0, 0);
        localTextView1.setTextColor(getResources().getColor(R.color.white));
        TextView localTextView2 = (TextView) localView1.findViewById(R.id.lblScan);
        localTextView2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_scan_gray, 0, 0, 0);
        localTextView2.setTextColor(getResources().getColor(R.color.gray_dk));
        ((TextView) localView1.findViewById(R.id.lbl)).setTextColor(getResources().getColor(R.color.gray_dk));
        localView1.findViewById(R.id.img).setBackgroundResource(R.drawable.rings_white);

        return localView1;
    }
}
