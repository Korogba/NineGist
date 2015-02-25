
package zumma.com.ninegistapp.ui.activities;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseUser;

import zumma.com.ninegistapp.MainActivity;
import zumma.com.ninegistapp.R;
import zumma.com.ninegistapp.custom.CustomActivity;


public class IntroActivity extends CustomActivity {

    private static final String TAG = IntroActivity.class.getSimpleName();

    private ViewPager viewPager;
    private ImageView topImage1;
    private ImageView topImage2;
    private ViewGroup bottomPages;
    private Button agree;
    private int lastPage = 0;
    private boolean justCreated = false;
    private boolean startPressed = false;
    private int[] icons;
    private int[] titles;
    private int[] messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseUser user = ParseUser.getCurrentUser();
        if (user != null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.intro_layout);

        icons = new int[] {
                R.drawable.intro1,
                R.drawable.intro2,
                R.drawable.intro3
        };
        titles = new int[] {
                R.string.Page1Title,
                R.string.Page2Title,
                R.string.Page3Title
        };
        messages = new int[] {
                R.string.Page1Message,
                R.string.Page2Message,
                R.string.Page3Message
        };


        viewPager = (ViewPager)findViewById(R.id.intro_view_pager);

        agree = (Button) findViewById(R.id.start_messaging_button);


        TextView terms = (TextView) setTouchNClick(R.id.terms);
        terms.setText(Html.fromHtml(terms.getText().toString()));

        topImage1 = (ImageView)findViewById(R.id.icon_image1);
        topImage2 = (ImageView)findViewById(R.id.icon_image2);
        bottomPages = (ViewGroup)findViewById(R.id.bottom_pages);
        topImage2.setVisibility(View.GONE);
        viewPager.setAdapter(new IntroAdapter());
        viewPager.setPageMargin(0);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if (i == ViewPager.SCROLL_STATE_IDLE || i == ViewPager.SCROLL_STATE_SETTLING) {
                    if (lastPage != viewPager.getCurrentItem()) {
                        lastPage = viewPager.getCurrentItem();

                        final ImageView fadeoutImage;
                        final ImageView fadeinImage;
                        if (topImage1.getVisibility() == View.VISIBLE) {
                            fadeoutImage = topImage1;
                            fadeinImage = topImage2;

                        } else {
                            fadeoutImage = topImage2;
                            fadeinImage = topImage1;
                        }

                        fadeinImage.bringToFront();
                        fadeinImage.setImageResource(icons[lastPage]);
                        fadeinImage.clearAnimation();
                        fadeoutImage.clearAnimation();


                        Animation outAnimation = AnimationUtils.loadAnimation(IntroActivity.this, R.anim.icon_anim_fade_out);
                        outAnimation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                fadeoutImage.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });

                        Animation inAnimation = AnimationUtils.loadAnimation(IntroActivity.this, R.anim.icon_anim_fade_in);
                        inAnimation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                fadeinImage.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });


                        fadeoutImage.startAnimation(outAnimation);
                        fadeinImage.startAnimation(inAnimation);
                    }
                }
            }
        });

        setTouchNClick(R.id.start_messaging_button);
    }

    @Override
    public void onClick(View v) {
        if ((v.getId() == R.id.start_messaging_button) ) {
            Intent intent = new Intent(IntroActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private class IntroAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(container.getContext(), R.layout.intro_view_layout, null);
            TextView headerTextView = (TextView)view.findViewById(R.id.header_text);
            TextView messageTextView = (TextView)view.findViewById(R.id.message_text);
            container.addView(view, 0);

            headerTextView.setText(getString(titles[position]));
            messageTextView.setText(Html.fromHtml(getString(messages[position])));

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            int count = bottomPages.getChildCount();
            for (int a = 0; a < count; a++) {
                View child = bottomPages.getChildAt(a);
                if (a == position) {
                    child.setBackgroundColor(getResources().getColor(R.color.red_light));
                } else {
                    child.setBackgroundColor(0xffbbbbbb);
                }
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {
            if (observer != null) {
                super.unregisterDataSetObserver(observer);
            }
        }
    }
}
