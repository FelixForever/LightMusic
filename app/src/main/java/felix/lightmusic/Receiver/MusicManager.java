package felix.lightmusic.Receiver;

import java.util.List;
import java.util.Vector;

import felix.lightmusic.DB.Music;

/**
 * Created by felix on 11/22/2016.
 */


public class MusicManager {

    public static List<MusicObserve> sMusicObserveList = new Vector<>();

    public static void observe(MusicObserve musicObserve) {
        if (!sMusicObserveList.isEmpty()) {
            for (MusicObserve observe : sMusicObserveList) {
                if (observe == musicObserve) {
                    return;
                }
            }
        }
        sMusicObserveList.add(musicObserve);

    }

    public static void unObserve(MusicObserve musicObserve) {

        sMusicObserveList.remove(musicObserve);

    }

    public static void updateAll(Music music) {
        if (music == null || sMusicObserveList == null || sMusicObserveList.isEmpty()) {
            return;
        }
        for (MusicObserve musicObserve : sMusicObserveList) {
            musicObserve.updateMusic(music);
        }
    }

    public interface MusicObserve {
        void updateMusic(Music music);
    }
}
