package felix.lib.Base.Util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by felix on 10/18/2016.
 */

public class SharedPreUtil {
    private static Context sContext;
    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;

    public static void init(Context context) {
        if (sContext != null) {
            return;
        }
        sContext = context;
        if (sp == null) {
            sp = context.getSharedPreferences("myConfig", 0);
            editor = sp.edit();
        }
    }

    public static void destory() {
        sp = null;
        editor = null;
    }

    /**
     * 保存整形到本地
     *
     * @param name
     * @param value
     */
    public static void putIntValue(String name, int value) {
        editor.putInt(name, value);
        editor.commit();
    }

    /**
     * 从本地读取整形数据
     *
     * @param name
     * @param defaultValue
     * @return
     */
    public static int getIntValue(String name, int defaultValue) {
        return sp.getInt(name, defaultValue);
    }

    /**
     * 保存字符串到本地
     *
     * @param name
     * @param value
     */
    public static void putStringValue(String name, String value) {
        editor.putString(name, value);
        editor.commit();
    }

    /**
     * 从本地读取字符串
     *
     * @param name
     * @param defaultValue
     * @return
     */
    public static String getStringValue(String name, String defaultValue) {
        return sp.getString(name, defaultValue);
    }

    /**
     * 保留布尔型到本地
     *
     * @param name
     * @param value
     */
    public static void putBooleanValue(String name, boolean value) {
        editor.putBoolean(name, value);
        editor.commit();
    }

    /**
     * 从本地读取布尔型数据
     *
     * @param name
     * @param defaultValue
     * @return
     */
    public static boolean getBooleanValue(String name, boolean defaultValue) {
        return sp.getBoolean(name, defaultValue);
    }

    /**
     * 保存长整形到本地
     *
     * @param name
     * @param value
     */
    public static void putLongValue(String name, long value) {
        editor.putLong(name, value);
        editor.commit();
    }

    /**
     * 从本地读取长整型数据
     *
     * @param name
     * @param defaultValue
     * @return
     */
    public static Long getLongValue(String name, long defaultValue) {
        return Long.valueOf(sp.getLong(name, defaultValue));
    }

    /**
     * 清除指定key值的数据
     *
     * @param context
     * @param name
     */
    public static void cleatValue(Context context, String... name) {
        for (int i = 0; i < name.length; ++i) {
            editor.remove(name[i]);
        }
        editor.commit();
    }
}
