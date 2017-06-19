package test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.text.Layout;
import android.util.AttributeSet;

/**
 * @author niqiang
 * @version 1.0
 * @since 2017/6/16
 */

public class SectorTextView extends android.support.v7.widget.AppCompatTextView {

    private static final int ANIM_INTERVAL = 50;
    private static float ANIM_FACTOR = 1.35f;

    private Path mPath = new Path();
    private float mPercent;
    private boolean mStart;
    private int mDuring;

    public SectorTextView(Context context) {
        super(context);
    }

    public SectorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SectorTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    long start;

    public void startAnimation(int during) {
        mStart = true;
        mDuring = during;
        setWillNotDraw(false);
        setLayerType(LAYER_TYPE_HARDWARE, null);
        setVisibility(VISIBLE);
        start = System.currentTimeMillis();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mPercent >= 1) {
            super.onDraw(canvas);
            mStart = false;
            mPercent = 0;
            return;
        }
        if (mStart) {
            execute(canvas);
        } else {
            super.onDraw(canvas);
            setVisibility(GONE);
        }
    }

    private void execute(Canvas canvas) {
        Rect rect = new Rect();
        getDrawingRect(rect);
        double radius = Math.pow(Math.pow(rect.right, 2) + Math.pow(rect.bottom, 2), 0.5);
        mPath.reset();
        mPath.moveTo(rect.right / 2, rect.bottom / 2);
        mPath.lineTo(rect.right / 2, 0);
        if (mPercent > 0.125f) {
            mPath.lineTo(rect.right, 0);
        }
        if (mPercent > 0.375f) {
            mPath.lineTo(rect.right, rect.bottom);
        }
        if (mPercent > 0.625f) {
            mPath.lineTo(0, rect.bottom);
        }
        if (mPercent > 0.875f) {
            mPath.lineTo(0, 0);
        }
        mPath.lineTo((float) (rect.right / 2 + radius * Math.sin(Math.PI * 2 * mPercent)),
                (float) (rect.bottom / 2 - radius * Math.cos(Math.PI * 2 * mPercent)));
        mPath.close();
        if (mPercent >= 0 && mPercent <= 1) {
            mPercent += ANIM_INTERVAL * ANIM_FACTOR / mDuring;
            canvas.save();
            canvas.clipPath(mPath);
            int x = 0, y = getBaseline();
            Layout layout = getLayout();
            StringBuilder str = new StringBuilder(getText().toString());
            int lineCount = getLineCount();
            for (int i = 0; i < lineCount; i++) {
                String line = str.subSequence(layout.getLineStart(i),layout.getLineEnd(i)).toString();
                canvas.drawText(line, x, y, getPaint());
                y += getPaint().descent() - getPaint().ascent();
            }
            canvas.restore();
        }
        postInvalidateDelayed(ANIM_INTERVAL);
    }
}
