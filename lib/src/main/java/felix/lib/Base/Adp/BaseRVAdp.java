package felix.lib.Base.Adp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import felix.lib.Base.AnnotationUtil;

/**
 * Created by felix on 10/25/2016.
 */


public class BaseRVAdp<T, VH extends BaseRVVH<T>> extends RecyclerView.Adapter<VH> {
    protected List<T> mCell;
    protected Context mContext;
    protected LayoutInflater mLayoutInflater;
    private OnRecyclerViewItemClickListenner mOnRecyclerViewItemClickListenner;
    private BaseRVAdp mBaseRVAdp;

    public interface OnRecyclerViewItemClickListenner<T, ADP extends BaseRVAdp> {
        void onItemClick(View view, ADP adp, T t, int position, int size);
    }

    public BaseRVAdp(Context context, List<T> cell) {
        mCell = cell;
        mContext = context;
        mBaseRVAdp = this;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void setOnRecyclerViewItemClickListenner(OnRecyclerViewItemClickListenner<T, ? extends BaseRVAdp> onRecyclerViewItemClickListenner) {
        mOnRecyclerViewItemClickListenner = onRecyclerViewItemClickListenner;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (mOnRecyclerViewItemClickListenner != null) {
            holder.setOnRecyclerViewItemClickListenner(new BaseRVVH.OnRecyclerViewItemClickListenner() {
                @Override
                public <T> void onItemClick(View view, T t, int position, int size) {
                    mOnRecyclerViewItemClickListenner.onItemClick(view, mBaseRVAdp, t, position, size);
                }
            });
        }
        holder.updateData(position, mCell.get(position), mCell.size(), null);
    }

    @Override
    public int getItemCount() {
        return mCell.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    protected int getLayoutId(int viewType) {
        return AnnotationUtil.getAnnotationLayoutId(this);
    }


    @Override
    final public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return getViewHolder(parent, viewType);
    }

    protected VH getViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(getLayoutId(viewType), parent, false);
        VH vh = AnnotationUtil.getTWithADPParame(this, 1, mContext, view);
        return vh;
    }
}
