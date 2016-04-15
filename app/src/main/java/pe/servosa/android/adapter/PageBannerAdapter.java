package pe.servosa.android.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import pe.servosa.android.R;
import pe.servosa.android.fragment.PageBannerFragment;

/**
 * Created by ucweb02 on 14/04/2016.
 */
public class PageBannerAdapter extends FragmentStatePagerAdapter {

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 5;

    private final int BANNERS_RES_ID[] = new int[]{
            R.drawable.banner_1,
            R.drawable.banner_2,
            R.drawable.banner_3,
            R.drawable.banner_4,
            R.drawable.banner_5
    };

    public PageBannerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        PageBannerFragment pageBannerFragment = new PageBannerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("bannerResID", BANNERS_RES_ID[position]);
        pageBannerFragment.setArguments(bundle);
        return pageBannerFragment;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

}
