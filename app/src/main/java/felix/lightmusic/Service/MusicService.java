package felix.lightmusic.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.List;

import felix.lib.Base.Util.SharedPreUtil;
import felix.lib.Base.Util.ToastUtil;
import felix.lightmusic.DB.Music;
import felix.lightmusic.DB.MusicList;
import felix.lightmusic.DB.MusicListItem;
import felix.lightmusic.DB.MusicListItemDao;
import felix.lightmusic.Receiver.MusicManager;
import felix.lightmusic.Util.DBUtil;

/**
 * Created by felix on 11/17/2016.
 */


public class MusicService extends Service {

    private static final String KEY_SP_MUSIC_LIST_ID = "MusicService_MUSIC_LIST";
    private static final String KEY_SP_CUR_MUSIC_INDEX = "MusicService_CUR_MUSIC";
    private static final String KEY_SP_CUR_MUSIC_POSITION = "MusicService_CUR_MUSIC_POSITION";

    private MediaPlayer mMediaPlayer;
    private MusicList mMusics;
    private long mCurListId = 0;
    private long[] mMusicId;
    private int mCurIndex = 0;
    private int mCurPos = 0;
    public final static String KEY_COMMAND = "MusicService_COMMAND";
    private static final String KEY_NEW_LIST = "MusicService_NEW_LIST";
    private static final String KEY_MUSIC_ID = "MUSIC_INDEX";

    public final static int COMMAND_PLAY = 1;
    public final static int COMMAND_PAUSE = 2;
    public final static int COMMAND_NEXT = 3;
    public final static int COMMAND_PREVIOUS = 4;
    public final static int COMMAND_NEW_LIST = 5;
    public final static int COMMAND_PLAY_WITH_ID = 6;

    private boolean isPrepare = false;
    private boolean isPlaying = false;

    @Override
    public void onCreate() {
        super.onCreate();
        initMusicList(-1, -1);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final int result = super.onStartCommand(intent, flags, startId);
        if (intent == null) {
            return result;
        }
        Bundle bundle = intent.getExtras();
        if (bundle == null || !bundle.containsKey(KEY_COMMAND)) {
            return result;
        }
        int commond = bundle.getInt(KEY_COMMAND);
        switch (commond) {
            case COMMAND_PLAY:
                play();
                break;
            case COMMAND_PAUSE:
                pause();
                break;
            case COMMAND_NEXT:
                playNext();
                break;
            case COMMAND_PREVIOUS:
                playPrevious();
                break;
            case COMMAND_NEW_LIST:
                long listId = bundle.getLong(KEY_NEW_LIST, -1);
                if (listId != -1) {
                    SharedPreUtil.putLongValue(KEY_SP_MUSIC_LIST_ID, listId);
                    final long musicId = bundle.getLong(KEY_MUSIC_ID, -1);
                    initMusicList(listId, musicId);
                    play();
                }
                break;
            case COMMAND_PLAY_WITH_ID:
//                long id = bundle.getLong(KEY_MUSIC_ID, -1);
//                List<MusicListItem> musicListItems = mMusics.getMusicsItems();
//                int size = musicListItems.size();
//                for (int i = 0; i < size; i++) {
//                    if (id == musicListItems.get(i).getMusicId()) {
//                        play(musicListItems.get(i).getMusic());
//                        break;
//                    }
//                }
                break;
            default:
                break;
        }
        return result;
    }

    private void initMusicList(long musicListId, long musicId) {
        if (musicListId != -1) {    //不等于-1的时候，即列表id有效
            if (mCurListId == musicListId) {    //和原来相等的时候，即同一列表的不同音乐，只修改播放的音乐id，并重置播放进度
                final int length = mMusicId.length;
                if (mMusicId != null && length > 0) {
                    if (musicId != -1) {
                        for (int i = 0; i < length; i++) {
                            if (mMusicId[i] == musicId) {
                                mCurIndex = i;
                                mCurPos = 0;
                                //play(mMusicId[i]);

                                break;
                            }
                        }
                    }
                    if (mCurIndex >= mMusicId.length) {
                        mCurIndex = 0;
                    }
                    final Music music = DBUtil.getMusicDao().load(mMusicId[mCurIndex]);
                    preparePlayer(music);       //重新获取列表的时候，初始化第一首歌
                    return;
                }
            } else {            //和原来不相同的时候，列表id重新赋值，当前播放重置（获得列表id的时候获取），播放进度为0
                mCurListId = musicListId;
                mCurIndex = 0;
                mCurPos = 0;
            }
        } else {    //等于-1的时候即第一次初始化，从sp获取，mCurListId为-1则为初始化，否则不做
            if (mCurListId == -1) {
                mCurIndex = SharedPreUtil.getIntValue(KEY_SP_CUR_MUSIC_INDEX, 0);
                mCurPos = SharedPreUtil.getIntValue(KEY_SP_CUR_MUSIC_POSITION, 0);
                mCurListId = SharedPreUtil.getLongValue(KEY_SP_MUSIC_LIST_ID, -1);
                if (mCurListId == -1) { //如果都为-1，说明是首次启动，默认列表id为0
                    mCurListId = 0;
                }
            }
        }
        /**
         * 当列表id重置的时候，获取对应的音乐id列表
         */
        List<MusicListItem> musicListItems = DBUtil.getMusicListItemDao().queryBuilder().where(MusicListItemDao.Properties.MusicListId.eq(mCurListId)).list();
        if (musicListItems == null || musicListItems.isEmpty()) {
            ToastUtil.showToast("当前无音乐");
            stopSelf();
            return;
        }
        final int size = musicListItems.size();
        mMusicId = new long[size];
        for (int i = 0; i < size; i++) {
            mMusicId[i] = musicListItems.get(i).getMusicId();
            if (musicId == mMusicId[i]) {
                mCurIndex = i;                      //重置播放下标
            }
        }
        if (mCurIndex >= size) {                    //如果大于size，异常，重置为0
            mCurIndex = 0;
        }
        final Music music = DBUtil.getMusicDao().load(mMusicId[mCurIndex]);
        preparePlayer(music);       //重新获取列表的时候，初始化第一首歌
    }

    private void play(Music music) {
        mCurPos = 0;
        if (preparePlayer(music)) {
            mMediaPlayer.start();
        }
        MusicManager.updateAll(music);
    }

    private void playNext() {
        mCurIndex++;
        if (mCurIndex >= mMusicId.length) {
            mCurIndex = 0;
        }
        play(mMusicId[mCurIndex]);
    }

    private void playPrevious() {
        mCurIndex--;
        if (mCurIndex < 0) {
            mCurIndex = mMusicId.length - 1;
        }
        play(mMusicId[mCurIndex]);
    }

    private void play() {
        play(-1);
    }

    private void play(long musidId) {
        if (musidId == -1) {
            ensurePlayer();
            if (!mMediaPlayer.isPlaying()) {
                mMediaPlayer.start();
            }
            return;
        }

        final Music music = DBUtil.getMusicDao().load(musidId);
        if (music != null) {
            play(music);
        }
    }

//    private void ensureList(long listId) {
//        if (mMusics == null)
//    }

//    private void play(Music music) {
//        ensurePlayer();
//        if (music == null) {
//            getIsReady()
//                    .filter(aBoolean -> aBoolean && mMusics != null && mMusics.getMusicsItems() != null && mMusics.getMusicsItems().size() > 0)
//                    .subscribe(new NextSubscriber<Boolean>() {
//                        @Override
//                        public void onNext(Boolean aBoolean) {
//                            if () {
//                                if (musicId != -1) {
//                                    for (int i = 0; i < mMusics.getMusicsItems().size(); i++) {
//                                        if (musicId == mMusics.getMusicsItems().get(i).getId()) {
//                                            mCurIndex = i;
//                                            break;
//                                        }
//                                    }
//                                }
//                                play(mMusics.getMusicsItems().get(mCurIndex).getMusic());
//                            }
//                        }
//                    });
//        } else {
//            if (!mMediaPlayer.isPlaying()) {
//                mMediaPlayer.start();
//            }
//        }
//    }

    private void pause() {
        ensurePlayer();
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
    }


    private void ensurePlayer() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        }
    }

    private boolean preparePlayer(Music music) {
        ensurePlayer();
        mMediaPlayer.reset();
        try {
            if (music == null) {
                throw new NullPointerException("music is null");
            }
            mMediaPlayer.setDataSource(music.getUrl());
            mMediaPlayer.prepare();
            if (mCurPos > 0) {
                mMediaPlayer.seekTo(mCurPos);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

//    private Observable<Boolean> getIsReady(long listId) {
//        Observable.just(listId)
//                .flatMap(aLong -> {
//                    if (aLong != -1) {
//
//                    } else {
//
//                    }
//                })
//        if (mMusics != null && mMusics.getMusicsItems() != null && !mMusics.getMusicsItems().isEmpty()) {
//            return Observable.just(true);
//        }
//        return Observable
//                .create(new Observable.OnSubscribe<Long>() {
//                    @Override
//                    public void call(Subscriber<? super Long> subscriber) {
//                        mCurIndex = SharedPreUtil.getIntValue(KEY_SP_CUR_MUSIC_INDEX, 0);
//                        mCurPos = SharedPreUtil.getIntValue(KEY_SP_CUR_MUSIC_POSITION, 0);
//                        Long list = SharedPreUtil.getLongValue(KEY_SP_MUSIC_LIST_ID, -1);
//                        subscriber.onNext(list);
//                    }
//                })
//                .flatMap(along -> MusicListModel.getMusicList(along))
//                .map(musicList -> {
//                    mMusics = musicList;
//                    if (mCurIndex >= mMusics.getMusicsItems().size()) {
//                        mCurIndex = 0;
//                        mCurPos = 0;
//                    }
//                    return true;
//                })
//                .compose(new ThreadTransform<>());
//    }


    public static void play(Context context, boolean isPlay) {
        if (isPlay) {
            startCommand(context, COMMAND_PLAY);
        } else {
            startCommand(context, COMMAND_PAUSE);
        }
    }

    public static void playNext(Context context) {
        startCommand(context, COMMAND_NEXT);
    }

    public static void playPrevious(Context context) {
        startCommand(context, COMMAND_PREVIOUS);
    }

    public static void addToNewList(Context context, long listId, long musicId) {
        Intent intent = new Intent(context, MusicService.class);
        intent.putExtra(KEY_COMMAND, COMMAND_NEW_LIST);
        intent.putExtra(KEY_NEW_LIST, listId);
        intent.putExtra(KEY_MUSIC_ID, musicId);
        context.startService(intent);
    }

    public static void playMusic(Context context, long musicId) {
        Intent intent = new Intent(context, MusicService.class);
        intent.putExtra(KEY_COMMAND, COMMAND_NEW_LIST);
        intent.putExtra(KEY_MUSIC_ID, musicId);
        context.startService(intent);
    }


    private static void startCommand(Context context, int commandCode) {
        Intent intent = new Intent(context, MusicService.class);
        intent.putExtra(KEY_COMMAND, commandCode);
        context.startService(intent);
    }


}
