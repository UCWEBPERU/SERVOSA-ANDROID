package pe.servosa.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.Bind;
import butterknife.ButterKnife;
import pe.servosa.android.R;

/**
 * Created by ucweb02 on 14/04/2016.
 */
public class PageBannerFragment extends Fragment {

    @Bind(R.id.imgBanner) ImageView imgBanner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_slide_banner, container, false);
        ButterKnife.bind(this, view);
        Glide.with(this)
                .load(getArguments().getInt("bannerResID"))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(imgBanner);
        return view;
    }

}
