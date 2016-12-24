package cn.a17cc.joycharge;

import android.app.Application;

/**
 * 创建人：luying
 * 创建时间：16/12/13
 * 类说明：
 */

public class BaseApp extends Application{
    private static BaseApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this ;
    }
    public static BaseApp getInstance() {
        return app;
    }

}
