package felix.lightmusic.View;

import android.os.Debug;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import felix.lib.Base.Aty.BaseAty;
import felix.lib.Base.BindLayout;
import felix.lightmusic.Adp.MusicAdp;
import felix.lightmusic.DB.Music;
import felix.lightmusic.MVP.Presenter.MusicPresenter;
import felix.lightmusic.R;

/**
 * Created by felix on 11/18/2016.
 */

@BindLayout(R.layout.test)
public class Test extends BaseAty implements MusicPresenter.MusicView {
    @Bind(R.id.rlv_music)
    RecyclerView mRlvMusic;

    MusicAdp mMusicAdp;
    List<Music> mMusics;
    private TestMusicAdp mTestMusicAdp;
    private MusicPresenter mMusicPresenter;

    @Override
    protected void initData() {
        super.initData();
        Debug.startMethodTracing();
        mMusicPresenter = new MusicPresenter(mContext, this);
        mMusics = new ArrayList<>();
        mMusicAdp = new MusicAdp(mContext, mMusics);
        mMusicPresenter.loadMusic();
        mRlvMusic.setLayoutManager(new LinearLayoutManager(mContext));
        mTestMusicAdp = new TestMusicAdp();
        // mRlvMusic.setAdapter(mMusicAdp);
        mRlvMusic.setAdapter(mTestMusicAdp);
    }

    @Override
    public void loadMusic(List<Music> musics) {
        mMusics.clear();
        mMusics.addAll(musics);
        mMusicAdp.notifyDataSetChanged();
        Debug.stopMethodTracing();
    }

    class TestAdp extends BaseAdapter {
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getCount() {
            return 22;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_music, parent, false);
            }
            return convertView;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }
    }

    class TestMusicAdp extends RecyclerView.Adapter<TestMusicVH> {
        @Override
        public void onBindViewHolder(TestMusicVH holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 22;
        }

        @Override
        public TestMusicVH onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(mContext).inflate(R.layout.item_music, parent, false);
            return new TestMusicVH(view);
        }
    }

    class TestMusicVH extends RecyclerView.ViewHolder {
        public TestMusicVH(View view) {
            super(view);
        }
    }
}
