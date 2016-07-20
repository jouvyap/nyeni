package bravostudio.nyeni.Custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by jouvyap on 7/21/16.
 */
public class SquareVideoView extends VideoView {
    public SquareVideoView(Context context) {
        super(context);
    }

    public SquareVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }
}
