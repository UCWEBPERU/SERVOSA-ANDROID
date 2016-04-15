package pe.servosa.android.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.util.Map;
import java.util.Set;

/**
 * Created by ucweb02 on 13/04/2016.
 */
public class MyPreferences implements SharedPreferences {

    private static MyPreferences myPreferences;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Activity activity;

    private MyPreferences(){

    }

    public static MyPreferences getInstance() {
        if (myPreferences == null) {
            myPreferences = new MyPreferences();
            return myPreferences;
        }
        return myPreferences;
    }

    public MyPreferences init(Activity activity, String namePreferences) {
        this.activity = activity;
        sharedPreferences = activity.getSharedPreferences(namePreferences, Context.MODE_PRIVATE); // Default MODE_PRIVATE
        editor = sharedPreferences.edit();
        return getInstance();
    }

    public MyPreferences init(Activity activity, String namePreferences, int mode) {
        this.activity = activity;
        sharedPreferences = activity.getSharedPreferences(namePreferences, mode);
        editor = sharedPreferences.edit();
        return getInstance();
    }

    @Override
    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }

    @Override
    public String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    @Nullable
    @Override
    public Set<String> getStringSet(String key, Set<String> defValues) {
        return sharedPreferences.getStringSet(key, defValues);
    }

    @Override
    public int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    @Override
    public long getLong(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    @Override
    public float getFloat(String key, float defValue) {
        return sharedPreferences.getFloat(key, defValue);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    @Override
    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    @Override
    public Editor edit() {
        return editor;
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {

    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {

    }
}
