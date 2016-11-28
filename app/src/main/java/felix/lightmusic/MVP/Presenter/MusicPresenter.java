package felix.lightmusic.MVP.Presenter;

import android.content.Context;

import java.util.List;

import felix.lib.Base.Rx.NextSubscriber;
import felix.lightmusic.DB.Music;
import felix.lightmusic.MVP.Model.MusicModel;

/**
 * Created by felix on 11/12/2016.
 */


public class MusicPresenter extends BasePresenter {
    private MusicView mMusicView;

    public MusicPresenter(Context context, MusicView musicView) {
        super(context);
        mMusicView = musicView;
    }

    public void loadMusic() {
        MusicModel.getMusicListFromDB(mContext)
                .subscribe(new NextSubscriber<List<Music>>() {
                    @Override
                    public void onNext(List<Music> musics) {
                        mMusicView.loadMusic(musics);
                    }
                });
    }

    public interface MusicView {
        void loadMusic(List<Music> musics);
    }
}
