package util;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Administrator on 2016/4/8.
 */
public class BitmapCache implements ImageLoader.ImageCache {

    private LruCache<String, Bitmap> mCache;
    private static BitmapCache mBitmapCache;

    public BitmapCache() {
        int maxSize = (int) (Runtime.getRuntime().maxMemory()/8);
        mCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
    }


    public static BitmapCache getInstance(){
        if(mBitmapCache == null){
            synchronized (BitmapCache.class){
                if(mBitmapCache == null){
                    mBitmapCache = new BitmapCache();
                }
            }
        }
        return mBitmapCache;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return mCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mCache.put(url, bitmap);
    }

}