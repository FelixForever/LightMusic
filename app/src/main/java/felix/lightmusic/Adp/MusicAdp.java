package felix.lightmusic.Adp;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import felix.lib.Base.Adp.BaseRVAdp;
import felix.lib.Base.Adp.BaseRVVH;
import felix.lib.Base.BindLayout;
import felix.lib.Base.Util.ViewUtil;
import felix.lightmusic.DB.Music;
import felix.lightmusic.R;

/**
 * Created by felix on 11/12/2016.
 */

@BindLayout(R.layout.item_music)
public class MusicAdp extends BaseRVAdp<Music, MusicAdp.MusciVH> {

    private MoreClick mMoreClick;

    public void setMoreClick(MoreClick moreClick) {
        mMoreClick = moreClick;
    }

    public interface MoreClick<T> {
        void click(View view, T t, int position, int size);
    }

    public MusicAdp(Context context, List<Music> cell) {
        super(context, cell);
    }

    @Override
    protected MusciVH getViewHolder(ViewGroup parent, int viewType) {
        final View view = mLayoutInflater.inflate(getLayoutId(viewType), parent, false);
        return new MusciVH(mContext, view);
    }

    class MusciVH extends BaseRVVH<Music> {
        @Bind(R.id.iv_album)
        ImageView mIvAlbum;
        @Bind(R.id.tv_name)
        TextView mTvName;
        @Bind(R.id.tv_artist)
        TextView mTvArtist;
        @Bind(R.id.chk_favorite)
        CheckBox mChkFavorite;
        @Bind(R.id.iv_settting)
        ImageView mIvSettting;

        public MusciVH(Context context, View view) {
            super(context, view);
        }

        public void updateData(int position, Music music, int size, View parent) {
            super.updateData(position, music, size, parent);
            mTvName.setText(music.getTitle());
            mTvArtist.setText(music.getAlbum());
            mChkFavorite.setChecked(music.getIsFavorite());
            mIvSettting.setOnClickListener(v -> {
                if (mMoreClick != null) {
                    mMoreClick.click(mIvSettting, music, position, size);
                }
            });
//            Drawable drawable = mIvAlbum.getDrawable();
//            if (!TextUtils.isEmpty(music.getAlbum())) {
//                ToastUtil.showToast("album is not null " + music.getTitle());
//                Bitmap bitmap = BitmapFactory.decodeFile(music.getAlbum());
//                if (bitmap != null) {
//                    mIvAlbum.setImageBitmap(bitmap);
//                } else {
//                    mIvAlbum.setImageDrawable(ViewUtil.getDrawableById(R.drawable.v_def_music));
//                    ToastUtil.showToast("bitmap is null " + music.getTitle() + music.getAlbum());
//                }
//            }
            if (!TextUtils.isEmpty(music.getAlbum())) {
                Log.i("hasdljfsldkjfsd", "updateData: " + music.getUrl());
            }
            Glide.with(mContext)
                    .load(music.getAlbum())
                    .placeholder(ViewUtil.getDrawableById(R.drawable.v_def_music))
                    .dontAnimate()
                    .into(mIvAlbum);
        }
    }
}
