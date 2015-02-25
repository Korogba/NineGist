package zumma.com.ninegistapp.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import zumma.com.ninegistapp.R;

public class ImgFragment extends Fragment {
    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        ImageView localImageView = (ImageView) paramLayoutInflater.inflate(R.layout.imageview, null);
        localImageView.setImageResource(getArguments().getInt("img"));
        return localImageView;
    }
}

/* Location:           C:\Users\Okafor\workspace\imate\iMate_dex2jar.jar
 * Qualified Name:     com.imate.ui.ImgFragment
 * JD-Core Version:    0.6.0
 */