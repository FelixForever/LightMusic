package felix.lib.Base.Util;

import android.content.Context;

import felix.lib.Base.App.BaseApp;

/**
 * Created by felix on 10/18/2016.
 */

public class AInitUtil {
    public static void init(Context ctx) {
        final Context context = ctx.getApplicationContext();
        BaseApp.init();
        SharedPreUtil.init(context);
        DentisityUtil.init(context);
        ToastUtil.init(context);
        SystemUtil.init(context);
        ViewUtil.init(context);
        LayoutUtil.init(context);
        final long time = System.currentTimeMillis();
//        final long endTime = time + 2000;
//        while (true) {
//            if (endTime <= System.currentTimeMillis()) {
//                break;
//            }
//        }
    }
}
