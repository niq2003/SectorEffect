package test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.text.StaticLayout;
import android.util.AttributeSet;

public class SectorTextView extends android.support.v7.widget.AppCompatTextView {

    private Path mPath = new Path();
    private float mPercent;
    private boolean mStart;

    public SectorTextView(Context context) {
        this(context, null);
    }

    public SectorTextView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public SectorTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setPercent(float mPercent) {
        this.mPercent = mPercent;
        mStart = true;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!mStart) super.onDraw(canvas);
        execute(canvas);
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
        canvas.save();
        canvas.clipPath(mPath);
        StaticLayout sl = new StaticLayout(getText(), getPaint(), getWidth(), getLayout().getAlignment(), getLineSpacingMultiplier(), getLineSpacingExtra(), getIncludeFontPadding());
        sl.draw(canvas);
        canvas.restore();
    }
}
