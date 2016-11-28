package felix.lib.Base.Rx;

import rx.Subscriber;

/**
 * Created by felix on 11/10/2016.
 */


public abstract class NextSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

}
