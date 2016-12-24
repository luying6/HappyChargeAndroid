package cn.a17cc.joycharge.common.util;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.a17cc.joycharge.BaseApp;
import cn.a17cc.joycharge.common.net.NetWorkUtil;
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

import static cn.a17cc.joycharge.common.net.RetrofitUtil.HTTP_CACHE_DIR;

/**
 * 创建人：luying
 * 创建时间：16/12/17
 * 类说明：
 */

public class RetrofitUtils {
    private static OkHttpClient mOkHttpClient;

    public static final CacheControl FORCE_CACHES = new CacheControl.Builder()
            .onlyIfCached()
            .maxStale(36000000, TimeUnit.SECONDS)
            .build();

    public static Retrofit.Builder getRetrofitBuild(String baseUrl, final Context context){

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
                    return response.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "sslkey, max-age=" + maxAge)
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
        try {
            builder.cache(cache);
            builder.addNetworkInterceptor(interceptor1);
            builder.addInterceptor(interceptor1);
            builder.sslSocketFactory(getSSLSocketFactory());
            builder.addNetworkInterceptor(interceptor);


            mOkHttpClient = builder.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    }

    public static SSLSocketFactory getSSLSocketFactory() throws Exception {
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[0];
            }
        }};

        final SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts,
                new java.security.SecureRandom());
        return sslContext
                .getSocketFactory();
    }
}
