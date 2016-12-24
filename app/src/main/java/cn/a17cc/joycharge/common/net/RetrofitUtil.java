package cn.a17cc.joycharge.common.net;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.a17cc.joycharge.BaseApp;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 创建人：luying
 * 创建时间：16/12/13
 * 类说明：
 */

public class RetrofitUtil {
    private static OkHttpClient mokHttpClient;
    public static final String HTTP_CACHE_DIR = "HttpCaches";


    public static final CacheControl FORCE_CACHES = new CacheControl.Builder()
            .onlyIfCached()
            .maxStale(36000000, TimeUnit.SECONDS)//这里是3600s，CacheControl.FORCE_CACHE--是int型最大值
            .build();


    public static Retrofit.Builder getRetrofitBuild(String baseUrl, final Context context) {
        //注意这里的Gson的构建方式为GsonBuilder,区别于test1中的Gson gson = new Gson();
//        Gson gson = new GsonBuilder()
//                .excludeFieldsWithoutExposeAnnotation() //不导出实体中没有用@Expose注解的属性
//                .enableComplexMapKeySerialization() //支持Map的key为复杂对象的形式
//                .serializeNulls()
//                .setDateFormat("yyyy-MM-dd")//时间转化为特定格式
//                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)//会把字段首字母大写,注:对于实体上使用了@SerializedName注解的不会生效.
//                .setPrettyPrinting() //对json结果格式化.
//                .setVersion(1.0)    //有的字段不是一开始就有的,会随着版本的升级添加进来,那么在进行序列化和返序列化的时候就会根据版本号来选择是否要序列化.
//                //@Since(版本号)能完美地实现这个功能.还的字段可能,随着版本的升级而删除,那么
//                //@Until(版本号)也能实现这个功能,GsonBuilder.setVersion(double)方法需要调用.
//                .create();


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Cache cache = new Cache(new File(BaseApp.getInstance().getCacheDir(), HTTP_CACHE_DIR),
                1024 * 1024 * 50);


        Interceptor interceptor1 = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (NetWorkUtil.isNetworkAvailable(context)) {
                    Response response = chain.proceed(request);
                    int maxAge = 0; // 在线缓存在1天内可读取
//                    String cacheControl = request.cacheControl().toString();
//                    String totals = response.header("X-Total")
                    return response.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                } else {
                    request = request.newBuilder()
                            .cacheControl(FORCE_CACHES)//此处设置了3600秒---修改了系统方法
                            .build();
                    Response response = chain.proceed(request);
                    return response.newBuilder()
                            .build();
                }
            }
        };
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.readTimeout(5,TimeUnit.SECONDS);
//        builder.connectTimeout(5,TimeUnit.SECONDS);
//        builder.writeTimeout(5,TimeUnit.SECONDS);
        builder.cache(cache);
        builder.addNetworkInterceptor(interceptor1);
        builder.addInterceptor(interceptor1);
//        if (BuildConfig.DEBUGABLE) {
            builder.addNetworkInterceptor(interceptor);
//            builder.addNetworkInterceptor(new StethoInterceptor());
//        }
        mokHttpClient = builder.build();
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mokHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

    }


}
