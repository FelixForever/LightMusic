package felix.lib.Base.App;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by felix on 9/9/2016.
 */

public class BaseApp extends Application {
    protected static List<Activity> sActivityList;
    protected static Context sContext;

    public static void init() {
        sActivityList = new ArrayList<>();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }

    public static Context getContext() {
        return sContext;
    }

    public static void addActivity(Activity activity) {
        if (sActivityList == null) {
            sActivityList = new ArrayList<>();
        }
        sActivityList.add(activity);
    }

    public static void remove(Activity activity) {
        if (sActivityList != null) {
            sActivityList.remove(activity);
        }
    }

    public static void exit() {
        if (sActivityList != null) {
            for (Activity activity : sActivityList) {
                activity.finish();
            }
        }
    }
}
