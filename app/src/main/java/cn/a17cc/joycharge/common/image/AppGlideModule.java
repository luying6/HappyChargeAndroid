package cn.a17cc.joycharge.common.image;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.integration.okhttp3.OkHttpGlideModule;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;

/**
 * 创建人：luying
 * 创建时间：16/12/13
 * 类说明：
 */

public class AppGlideModule extends OkHttpGlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //设置磁盘缓存
        builder.setDiskCache(getDiskFactory(context));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        super.registerComponents(context, glide);
    }

    private DiskCache.Factory getDiskFactory(Context context) {
        return new ExternalCacheDiskCacheFactory(context, "imageCache", 200 * 1024 * 1024);
    }
}
