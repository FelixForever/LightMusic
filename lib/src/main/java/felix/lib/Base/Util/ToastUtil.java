package felix.lib.Base.Util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by felix on 10/19/2016.
 */

public class ToastUtil {
    private static Toast mToast = null;
    private final static int ShowTime = Toast.LENGTH_SHORT;
    private static Context sContext;

    public static void init(Context context) {
        if (sContext != null) {
            return;
        }
        sContext = context;
        mToast = Toast.makeText(context, "", ShowTime);
      //  mToast.setDuration(ShowTime);
    }

    /**
     * 显示指定字符串ID的toast
     *
     * @param contentId
     */
    public static void showToast(int contentId) {
        mToast.setText(contentId);
        mToast.show();
    }

    /**
     * 显示指定字符串
     *
     * @param content
     */
    public static void showToast(String content) {
        mToast.setText(content);
        mToast.show();
    }

    /**
     * 关闭toast
     */
    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

}
