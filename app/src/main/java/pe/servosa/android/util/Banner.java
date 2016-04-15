package pe.servosa.android.util;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.Timer;
import java.util.TimerTask;

import pe.servosa.android.adapter.PageBannerAdapter;

/**
 * Created by ucweb02 on 14/04/2016.
 */
public class Banner {

    private PagerAdapter mPagerAdapter;
    private ViewPager viewPager;
    private static int currentPage = 0;

    public Banner(Activity activity, ViewPager viewPager) {
        this.viewPager = viewPager;
        mPagerAdapter = new PageBannerAdapter(((FragmentActivity) activity).getSupportFragmentManager());
        this.viewPager.setAdapter(mPagerAdapter);
        init();
    }

    private void init() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void autoScroll() {
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == mPagerAdapter.getCount()) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
    }

}
