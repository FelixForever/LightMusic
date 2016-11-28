package felix.lib.Base.Adp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by felix on 10/25/2016.
 */


public class BaseRVVH<T> extends RecyclerView.ViewHolder {
    protected Context mContext;
    protected View mView;
//    protected T mData;

    private OnRecyclerViewItemClickListenner mOnRecyclerViewItemClickListenner;

    public interface OnRecyclerViewItemClickListenner {
        <T> void onItemClick(View view, T t, int position, int size);
    }

    public void setOnRecyclerViewItemClickListenner(OnRecyclerViewItemClickListenner onRecyclerViewItemClickListenner) {
        mOnRecyclerViewItemClickListenner = onRecyclerViewItemClickListenner;
    }

    public BaseRVVH(Context context, View view) {
        super(view);
        mView = view;
        mContext = context;
        onBindView(mView);
    }

    protected void onBindView(View view) {
        ButterKnife.bind(this, view);
    }


    public void updateData(final int position, final T t, final int size, View parent) {
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnRecyclerViewItemClickListenner != null) {
                    mOnRecyclerViewItemClickListenner.onItemClick(mView, t, position, size);
                }
            }
        });
    }
}
