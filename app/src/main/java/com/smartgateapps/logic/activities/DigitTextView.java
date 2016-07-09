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

import java.util.Locale;

/**
 * Created by Raafat on 28/02/2016.
 */
public class DigitTextView extends FrameLayout {

    private static int ANIMATION_DURATION = 250;
    private TextView currentTextView, nextTextView;
    private Context ctx;

    public DigitTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DigitTextView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.ctx = context;
        LayoutInflater.from(context).inflate(R.layout.digit_text_view, this);
        currentTextView = (TextView) getRootView().findViewById(R.id.currentTextView);
        nextTextView = (TextView) getRootView().findViewById(R.id.nextTextView);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/digital-7.ttf");
        currentTextView.setTypeface(typeface);
        nextTextView.setTypeface(typeface);
        nextTextView.setTranslationY(getHeight());
        setValue(0);
    }

    public void setValue(final String desiredValue) {

        currentTextView.setTextColor(ctx.getResources().getColor(R.color.colorCyan));
        nextTextView.setTextColor(ctx.getResources().getColor(R.color.colorCyan));
        currentTextView.setTextSize(24);
        nextTextView.setTextSize(24);

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

    public void setValue(final int desiredValue) {
        if (currentTextView.getText() == null || currentTextView.getText().length() == 0) {
            currentTextView.setText(desiredValue + "");
        }

        final int oldValue = Integer.parseInt(currentTextView.getText().toString());

        if (oldValue > desiredValue) {
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
                    currentTextView.setText((desiredValue) + "");
                    currentTextView.setTranslationY(0);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            }).start();
        } else if (oldValue < desiredValue) {
            nextTextView.setText((desiredValue) + "");

            currentTextView.animate().translationY(getHeight()).setDuration(ANIMATION_DURATION).start();
            nextTextView.setTranslationY(-nextTextView.getHeight());
            nextTextView.animate().translationY(0).setDuration(ANIMATION_DURATION).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    nextTextView.setVisibility(VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    nextTextView.setVisibility(INVISIBLE);
                    currentTextView.setText((desiredValue) + "");
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
}
