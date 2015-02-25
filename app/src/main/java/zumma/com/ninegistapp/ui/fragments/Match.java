package zumma.com.ninegistapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zumma.com.ninegistapp.MainActivity;
import zumma.com.ninegistapp.R;
import zumma.com.ninegistapp.custom.CustomFragment;


public class Match extends CustomFragment {

    public void onClick(View paramView) {
        super.onClick(paramView);
        if (paramView.getId() == R.id.btnConversation) {
            ((MainActivity) getActivity()).launchFragment(1, null);
            return;
        }
        ((MainActivity) getActivity()).launchFragment(0, null);
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        View localView = paramLayoutInflater.inflate(R.layout.match, null);
        setTouchNClick(localView.findViewById(R.id.btnConversation));
        setTouchNClick(localView.findViewById(R.id.btnSurf));
        return localView;
    }
}

/* Location:           C:\Users\Okafor\workspace\imate\iMate_dex2jar.jar
 * Qualified Name:     com.imate.ui.Match
 * JD-Core Version:    0.6.0
 */