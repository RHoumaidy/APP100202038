package com.smartgateapps.logic.activities;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.smartgateapps.logic.R;

/**
 * Created by Raafat on 28/02/2016.
 */
public class FlipTextView extends FrameLayout {

    private static int ANIMATION_DURATION = 250;
    private TextView currentTextView, nextTextView;
    private Context ctx;

    public FlipTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FlipTextView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.ctx = context;
        LayoutInflater.from(context).inflate(R.layout.flip_txt_view, this);
        currentTextView = (TextView) getRootView().findViewById(R.id.currentTextView);
        nextTextView = (TextView) getRootView().findViewById(R.id.nextTextView);
        nextTextView.setTranslationY(getHeight());

        setValue("");
    }

    public void setValue(final String desiredValue) {

        if (TextUtils.isEmpty(currentTextView.getText())) {
            currentTextView.setText(desiredValue);
        }

        final String oldValue = currentTextView.getText().toString();


            nextTextView.setText((desiredValue) + "");

            currentTextView.animate().translationY(-getHeight()).setDuration(ANIMATION_DURATION).start();
            nextTextView.setTranslationY(nextTextView.getHeight());
            nextTextView.animate().translationY(0).setDuration(ANIMATION_DURATION).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    nextTextView.setVisibility(VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    nextTextView.setVisibility(INVISIBLE);
                    currentTextView.setText(desiredValue);
                    currentTextView.setTranslationY(0);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            }).start();

    }

}
