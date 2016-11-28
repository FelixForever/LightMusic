package felix.lib.Base.Util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;

/**
 * Created by felix on 11/10/2016.
 */


public class ViewUtil {
    private static Context sContext;
    private static Resources sResources;

    public static void init(Context context) {
        sContext = context;
        sResources = sContext.getResources();
    }

    public static Drawable getDrawableById(int id) {
        if (Build.VERSION.SDK_INT > 19) {
            return sContext.getResources().getDrawable(id, sContext.getTheme());
        } else {
            return sContext.getDrawable(id);
        }
    }
}
