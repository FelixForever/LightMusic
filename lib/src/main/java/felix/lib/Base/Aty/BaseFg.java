package felix.lib.Base.Aty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import felix.lib.Base.AnnotationUtil;

/**
 * Created by felix on 11/12/2016.
 */


public class BaseFg extends Fragment {
    protected final String TAG = this.getClass().getSimpleName();
    protected final String FIRST = "first";
    protected Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        final int layout = getLayoutId();
        if (layout != -1) {
            View view = LayoutInflater.from(mContext).inflate(layout, container, false);
            init(view);
            return view;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    private void init(View view) {
        bindView(view);
        initData(view);
    }

    private boolean isBindView = false;

    protected void bindView(View view) {
        isBindView = true;
        ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (isBindView) {
            ButterKnife.unbind(this);
        }
    }

    protected void startAty(Class cls) {
        startActivity(new Intent(mContext, cls));
    }

    protected int getLayoutId() {
        return AnnotationUtil.getAnnotationLayoutId(this);
    }

    protected void initData(View view) {

    }

    public String getTitle() {
        return TAG;
    }
}
