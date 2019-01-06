package com.costar.talkwithidol.ui.activities.tutorial.mvp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.costar.talkwithidol.R;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ext.storage.PreferencesManager;
import com.costar.talkwithidol.ui.activities.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;


@SuppressLint("ViewConstructor")
public class TutorialView extends FrameLayout {
    public PreferencesManager preferenceManager;
    public AppCompatActivity activity;
    public SharedPreferences sharedPreferencesN;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tutorial_indicator)
    CircleIndicator indicator;
    MyViewPagerAdapter myViewPagerAdapter;
    @BindView(R.id.next)
    TextView next;
    @BindView(R.id.previous)
    TextView prev;
    private int[] layouts;

    SharedPreferences sharedPreferences;
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                next.setText("Get Started");
            } else {
                // still pages are left
                next.setText("Next");
            }


            if (position > 0) {
                // last page. make button text to GOT IT
                prev.setVisibility(View.VISIBLE);
            } else {
                // still pages are left
                prev.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    public TutorialView(@NonNull AppCompatActivity activity, PreferencesManager preferenceManager) {
        super(activity);
        this.activity = activity;
        this.preferenceManager = preferenceManager;
        inflate(activity, R.layout.tutorial, this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        ButterKnife.bind(this);
        myViewPagerAdapter = new MyViewPagerAdapter();
        layouts = new int[]{
                R.layout.welcomeslide1,
                R.layout.welcomeslide2,
                R.layout.welcomeslide3,
                R.layout.welcomeslide4,

        };
//        prev.setCompoundDrawablesWithIntrinsicBounds(activity.getResources().getDrawable(R.drawable.backward), null, null, null);
//        next.setCompoundDrawablesWithIntrinsicBounds(null, null, activity.getResources().getDrawable(R.drawable.forward), null);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        indicator.setViewPager(viewPager);
        myViewPagerAdapter.notifyDataSetChanged();

        next.setOnClickListener(view -> {
            int current = getItem(+1);
            if (current < layouts.length) {
                viewPager.setCurrentItem(current);
            } else {
                sharedPreferences.edit().putString(Constants.ISFIRSTLAUNCH,"No").apply();
                LoginActivity.start(activity);
                activity.finish();

            }
        });

        prev.setOnClickListener(view -> {
            int current = getItem(-1);
            if (current < layouts.length) {
                viewPager.setCurrentItem(current);
            }
        });
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    public void saveData(String key, String value) {
        preferenceManager.save(key, value);
    }


}
