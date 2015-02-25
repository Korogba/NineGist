package zumma.com.ninegistapp.ui.fragments;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import zumma.com.ninegistapp.MainActivity;
import zumma.com.ninegistapp.R;
import zumma.com.ninegistapp.custom.CustomFragment;
import zumma.com.ninegistapp.pager.JazzyViewPager;
import zumma.com.ninegistapp.pager.OutlineContainer;

public class MainFragment extends CustomFragment
{
    private static final String TAG = MainFragment.class.getSimpleName();
    private JazzyViewPager mJazzy;

    private void setupPager(View paramView, JazzyViewPager.TransitionEffect paramTransitionEffect)
    {
//        this.mJazzy = ((JazzyViewPager)paramView.findViewById(R.id.jazzy_pager));
//        this.mJazzy.setTransitionEffect(paramTransitionEffect);
//        this.mJazzy.setAdapter(new MainAdapter());
//        this.mJazzy.setPageMargin(30);
    }

    public void onClick(View paramView)
    {
        super.onClick(paramView);
        if (paramView.getId() == R.id.btnLike)
            ((MainActivity)getActivity()).launchFragment(-1, null);
        else if (paramView.getId() != R.id.btnInfo)
            ((MainActivity)getActivity()).launchFragment(-2, null);
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
    {
        Log.d(TAG, "am at onCreate Fragment");
        View localView = paramLayoutInflater.inflate(R.layout.main_container, null);

//        String title = getActivity().getSupportFragmentManager().getBackStackEntryAt(
//                getActivity().getSupportFragmentManager().getBackStackEntryCount() - 1)
//                .getName();
//
//        JazzyViewPager.TransitionEffect effect = JazzyViewPager.TransitionEffect.valueOf(title);
//
//        setupPager(localView, effect);
//        setTouchNClick(localView.findViewById(R.id.btnUnlikne));
//        setTouchNClick(localView.findViewById(R.id.btnLike));
//        setTouchNClick(localView.findViewById(R.id.btnInfo));
        return localView;
    }

    private class MainAdapter extends PagerAdapter
    {
        public void destroyItem(ViewGroup container, int position, Object paramObject)
        {
            container.removeView(mJazzy.findViewFromObject(position));
        }

        public int getCount()
        {
            return 20;
        }

        public Object instantiateItem(ViewGroup container, int position)
        {
            ImageView localImageView = new ImageView(MainFragment.this.getActivity());
            int i = R.drawable.pager_img;
            if ((position - 1) % 4 == 0)
                i = R.drawable.pager_img1;
            else if ((position - 2) % 4 == 0)
            {
                i = R.drawable.pager_img2;
            }
            else if ((position - 3) % 4 != 0){
                i = R.drawable.pager_img3;
            }

            if (i >= 0){
                localImageView.setBackgroundResource(i);
                container.addView(localImageView, -1, -1);
                mJazzy.setObjectForPosition(localImageView, position);
            }
            return localImageView;
        }

        public boolean isViewFromObject(View view, Object object)
        {
            if (view instanceof OutlineContainer) {
                return ((OutlineContainer) view).getChildAt(0) == object;
            } else {
                return view == object;
            }
        }
    }
}
