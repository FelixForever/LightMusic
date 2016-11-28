package felix.lib.Base.Util;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by felix on 11/18/2016.
 */


public class LayoutUtil {
    private static Context sContext;
    private static SparseArray<View> sViewCaches;

    public static void init(Context context) {
        if (sContext != null) {
            return;
        }
        sContext = context;
        sViewCaches = new SparseArray<>();
    }

    public static void put(int layoutId, View view) {
        sViewCaches.put(layoutId, view);
    }

    public static View get(int layoutId, ViewGroup parent, boolean attachView) {
        View view = sViewCaches.get(layoutId);
        if (view == null) {
            view = LayoutInflater.from(sContext).inflate(layoutId, parent, attachView);
            sViewCaches.put(layoutId, view);
        }
        return view;
    }

    public static View get(int layoutId, ViewGroup parent) {
        return get(layoutId, parent, false);
    }

    public static View get(int layoutId) {
        return get(layoutId, null, false);
    }
}
