package pe.servosa.android.util;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by ucweb02 on 14/04/2016.
 */
public class MyToolbar {

    private AppCompatActivity activity;
    private Toolbar toolbar;

    public MyToolbar(AppCompatActivity activity, Toolbar toolbar) {
        this.activity = activity;
        this.toolbar = toolbar;
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle("");
    }

    public ActionBar getActionBar() {
        return activity.getSupportActionBar();
    }

}
