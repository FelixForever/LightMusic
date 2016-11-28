//package felix.lib.weiget;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.drawable.Drawable;
//import android.util.AttributeSet;
//import android.widget.ImageView;
//
///**
// * Created by felix on 11/12/2016.
// */
//
//
//public class CircleImageView extends ImageView {
//    public CircleImageView(Context context, AttributeSet attributeSet) {
//        super(context, attributeSet);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        Drawable drawable = getDrawable();
//        if (drawable == null) {
//            super.onDraw(canvas);
//        } else {
//            canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getMeasuredHeight() / 2, new Paint());
//            drawable.draw(canvas);
//        }
//    }
//}
