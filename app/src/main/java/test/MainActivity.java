package test;

import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.CharacterStyle;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.test.R;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView imageView = (ImageView) findViewById(R.id.image);
        findViewById(R.id.btn_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setVisibility(View.VISIBLE);
                SectorDrawable sectorDrawable = new SectorDrawable(imageView.getDrawable());
                imageView.setImageDrawable(sectorDrawable);
                ObjectAnimator animator = ObjectAnimator.ofFloat(sectorDrawable, "percent", 0, 1);
                animator.setDuration(5000);
                animator.setInterpolator(new LinearInterpolator());
                animator.start();
            }
        });

        final SectorTextView textView = (SectorTextView) findViewById(R.id.textview);
        SpannableString spannable = new SpannableString("safasfasfsaffasfasfaffaffdsafjaksfjasfasf");
        CharacterStyle span=new UnderlineSpan();
        spannable.setSpan(span,5,10, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spannable);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        findViewById(R.id.btn_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setAlpha(1);
                textView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                ObjectAnimator animator = ObjectAnimator.ofFloat(textView, "percent", 0, 1);
                animator.setDuration(3000);
                animator.setInterpolator(new LinearInterpolator());
                animator.start();
            }
        });
    }
}
