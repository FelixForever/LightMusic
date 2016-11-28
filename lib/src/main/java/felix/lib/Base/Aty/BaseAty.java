package felix.lib.Base.Aty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import felix.lib.Base.AnnotationUtil;
import felix.lib.Base.App.BaseApp;

/**
 * Created by felix on 9/9/2016.
 */

public abstract class BaseAty extends AppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();
    protected final String FIRST = "first";
    protected Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(getLayoutId());
        BaseApp.addActivity(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApp.remove(this);
    }

    private void init() {
        bindView();
        initData();
    }

    protected void bindView() {
        ButterKnife.bind(this);
    }

    protected void startAty(Class cls) {
        startActivity(new Intent(mContext, cls));
    }

    protected int getLayoutId() {
        return AnnotationUtil.getAnnotationLayoutId(this);
    }

    protected void initData() {
        initViewEvent();
    }


    /**
     * 返回点击事件，默认返回
     *
     * @param view
     */
    protected void onBackClick(View view) {
        onBackPressed();
    }

    /**
     * 右边点击事件
     *
     * @param view
     */
    protected void onEditClick(View view) {

    }

    /**
     * 设置标题事件
     *
     * @param textView
     */
    protected void onSetTitleText(TextView textView) {
        textView.setText(getTitle());
    }

    /**
     * 初始化标题栏控件事件
     */
    protected void initViewEvent() {
        final View view = findViewById(android.R.id.closeButton);
        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackClick(v);
                }
            });
        }
        final View view1 = findViewById(android.R.id.edit);
        if (view1 != null) {
            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onEditClick(view);
                }
            });
        }

        final View textView = findViewById(android.R.id.title);
        if (textView != null && textView instanceof TextView) {
            onSetTitleText(((TextView) textView));
        }

    }
}
