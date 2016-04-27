package pe.servosa.android.application;

import android.app.Application;

import com.orm.SugarContext;

/**
 * Created by ucweb02 on 12/04/2016.
 */
public class Configuration extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
        // UNIVERSAL IMAGE LOADER SETUP
//        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
//                .cacheOnDisc(true).cacheInMemory(true)
//                .imageScaleType(ImageScaleType.EXACTLY)
//                .displayer(new FadeInBitmapDisplayer(300)).build();



//        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
//                .resetViewBeforeLoading(false)
//                .delayBeforeLoading(1000)
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
//                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
//                .displayer(new SimpleBitmapDisplayer()) // default
//                .handler(new Handler()) // default
//                .build();
//
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
//                getApplicationContext())
//                .defaultDisplayImageOptions(defaultOptions)
//                .memoryCache(new WeakMemoryCache())
//                .discCacheSize(100 * 1024 * 1024).build();
//
//        ImageLoader.getInstance().init(config);

//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
//                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
//                .diskCacheExtraOptions(480, 800, null)
//                .threadPoolSize(3) // default
//                .threadPriority(Thread.NORM_PRIORITY - 2) // default
//                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
//                .denyCacheImageMultipleSizesInMemory()
//                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
//                .memoryCacheSize(2 * 1024 * 1024)
//                .memoryCacheSizePercentage(13) // default
//                .diskCacheSize(50 * 1024 * 1024)
//                .diskCacheFileCount(100)
//                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
//                .imageDownloader(new BaseImageDownloader(getApplicationContext())) // default
//                .imageDecoder(new BaseImageDecoder(true)) // default
//                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
//                .writeDebugLogs()
//                .build();
//
//        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP
    }

    @Override
    public void onTerminate() {
        SugarContext.terminate();
        super.onTerminate();
    }
}
