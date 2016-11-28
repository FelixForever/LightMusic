package felix.lightmusic.View;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import felix.lib.Base.Adp.BaseRVAdp;
import felix.lib.Base.Aty.BaseFg;
import felix.lib.Base.BindLayout;
import felix.lib.Base.Util.ToastUtil;
import felix.lightmusic.Adp.MusicAdp;
import felix.lightmusic.DB.Music;
import felix.lightmusic.MVP.Presenter.MusicPresenter;
import felix.lightmusic.R;
import felix.lightmusic.Service.MusicService;

/**
 * Created by felix on 11/12/2016.
 */

@BindLayout(R.layout.fg_music)
public class MusicFg extends BaseFg implements MusicPresenter.MusicView {
    @Bind(R.id.rlv_music)
    RecyclerView mRlvMusic;

    private MusicAdp mMusicAdp;
    private List<Music> mMusics;

    private MusicPresenter mMusicPresenter;


    @Override
    protected void initData(View view) {


        super.initData(view);
        mMusicPresenter = new MusicPresenter(mContext, this);
        mMusics = new ArrayList<>();
        mMusicAdp = new MusicAdp(mContext, mMusics);
        mRlvMusic.setLayoutManager(new LinearLayoutManager(mContext));
        mRlvMusic.setAdapter(mMusicAdp);
        mMusicAdp.setOnRecyclerViewItemClickListenner(new BaseRVAdp.OnRecyclerViewItemClickListenner<Music, MusicAdp>() {
            @Override
            public void onItemClick(View view, MusicAdp adp, Music t, int position, int size) {
                //ToastUtil.showToast(t.getAlbum());
                long musicId = t.getId();
                MusicService.addToNewList(mContext, 0, musicId);
            }
        });
        mMusicAdp.setMoreClick(new MusicAdp.MoreClick<Music>() {
            @Override
            public void click(View view, Music t, int position, int size) {
                ToastUtil.showToast(t.getName());
            }
        });
        mMusicPresenter.loadMusic();
    }

    @Override
    public void loadMusic(List<Music> musics) {
        // Log.i(TAG, "loadMusic: " + new Gson().toJson(musics));
        if (!mMusics.isEmpty()) {
            mMusics.clear();
        }
        mMusics.addAll(musics);
        mMusicAdp.notifyDataSetChanged();
    }

    @Override
    public String getTitle() {
        return "我的音乐";
    }
}
