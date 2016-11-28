package felix.lightmusic.View;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import felix.lightmusic.R;

/**
 * Created by felix on 11/18/2016.
 */


public class TestOnCreate extends Activity {
    private static final String TAG = TestOnCreate.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final long now = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.aty_main, null, false);
            setContentView(view);
        }

        Log.i(TAG, "onCreate: " + (System.currentTimeMillis() - now));

    }
}
