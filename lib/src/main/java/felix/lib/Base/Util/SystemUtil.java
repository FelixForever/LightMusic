package felix.lib.Base.Util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import felix.lib.Base.Util.Base.SystemBarConfig;

/**
 * Created by felix on 10/21/2016.
 */


public class SystemUtil {
    private static Context sContext;
    private static int mNavigationBarHeight = -1;

    public static void init(Context context) {
        if (sContext != null) {
            return;
        }
        sContext = context;
    }

    public static int getNavigationBarHeight(Activity activity) {
        if (mNavigationBarHeight != -1) {
            return mNavigationBarHeight;
        }
        SystemBarConfig systemBarConfig = new SystemBarConfig(activity);
        mNavigationBarHeight = systemBarConfig.getNavigationBarHeight(activity);
        return mNavigationBarHeight;
    }

    // 获得版本号
    public static String getVersionNum() {
        PackageManager pm = sContext.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(sContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pi.versionName;

    }
}
