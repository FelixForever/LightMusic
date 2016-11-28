package felix.lib.Base;

import android.content.Context;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by felix on 10/18/2016.
 */

public class AnnotationUtil {
    private static final String TAG = AnnotationUtil.class.getSimpleName();

    public static int getAnnotationLayoutId(Object obj) {
        BindLayout bindLayout = obj.getClass().getAnnotation(BindLayout.class);
        if (bindLayout != null) {
            final int layoutId = bindLayout.value();
            return layoutId;
        }
        return -1;
    }

    public static <T> T getT(Object o, int i) {
        try {
            Type[] types = ((ParameterizedType) o.getClass().getGenericSuperclass()).getActualTypeArguments();
            if (types == null || types.length <= i)
                return null;
            T t = ((Class<T>) types[i]).newInstance();
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <VH> VH getTWithADPParame(Object o, int i, Context context, View view) {
        Type[] types = ((ParameterizedType) o.getClass().getGenericSuperclass()).getActualTypeArguments();
        if (types == null || types.length <= i) {
            return null;
        }
        Constructor<?> constructor = null;
        try {
            constructor = ((Class) types[i]).getConstructor(o.getClass(), Context.class, View.class);
            if (constructor == null) {
                constructor = ((Class) types[i]).getConstructor(Context.class, View.class);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            VH vh = ((VH) constructor.newInstance(o, context, view));
            if (vh == null) {
                vh = ((VH) constructor.newInstance(constructor, view));
            }
            return vh;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
