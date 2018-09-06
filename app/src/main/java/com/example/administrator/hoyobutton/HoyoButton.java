package com.example.administrator.hoyobutton;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;


/**
 * Created by Abhishek
 */

public class HoyoButton extends android.support.v7.widget.AppCompatButton implements View.OnTouchListener, View.OnClickListener {

    //Custom values
    private boolean isShadowEnabled = true;
    private int mButtonColor;
    private int mShadowHeight;
    private int mCornerRadius;
    private int mShape;

  //  private int mClickSound;

    private int mAnimation;
    private int manimationDuration;


    HoyoClickListner hoyoClickListner;

    HoyoAnimationListner hoyoAnimationListner;


    //user Colors
    private int uShapeColor;
    private int uStrokeColor;
    private int uStrokeWidth;


    Drawable drawable;

    private int mCatgory;


    public HoyoButton(Context context) {
        super(context);
        init();

    }

    public HoyoButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        parseAttrs(context, attrs);

    }

    public HoyoButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        parseAttrs(context, attrs);


    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        refresh();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
     /*   switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                Rect r = new Rect();
                view.getLocalVisibleRect(r);
                if (!r.contains((int) motionEvent.getX(), (int) motionEvent.getY() + 3 * mShadowHeight) &&
                        !r.contains((int) motionEvent.getX(), (int) motionEvent.getY() - 3 * mShadowHeight)) {

                }
                break;
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:

                break;
        }*/
        return false;
    }

    private void init() {

        Resources resources = getResources();
        if (resources == null)
            return;


    }

    private void parseAttrs(Context context, AttributeSet attrs) {
        //Load from custom attributes
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.hoyo);

        isShadowEnabled = typedArray.getBoolean(R.styleable.hoyo_shadowEnabled, false); //Default is false
        mButtonColor = typedArray.getColor(R.styleable.hoyo_buttonColor, getResources().getColor(R.color.colorPrimary));
        mShadowHeight = typedArray.getDimensionPixelSize(R.styleable.hoyo_shadowHeight, R.dimen.fbutton_default_shadow_height);
        mCornerRadius = typedArray.getDimensionPixelSize(R.styleable.hoyo_cornerRadius, R.dimen.fbutton_default_conner_radius);
       // mClickSound = typedArray.getInt(R.styleable.hoyo_OnClickSound, 0);
        mShape = typedArray.getInt(R.styleable.hoyo_shape, 0);


        mCatgory = typedArray.getInt(R.styleable.hoyo_category, 0);

        manimationDuration = typedArray.getInt(R.styleable.hoyo_animation_duration, 500);
        uStrokeColor = typedArray.getInt(R.styleable.hoyo_stroke_color, getResources().getColor(R.color.colorPrimary));
        uShapeColor = typedArray.getInt(R.styleable.hoyo_shape_color, getResources().getColor(R.color.colorPrimary));
        uStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.hoyo_shape_stroke_width, 0);
        mAnimation = typedArray.getInt(R.styleable.hoyo_animation, 0);
        typedArray.recycle();

    }

    public void refresh() {
        updateBackground();
    }


    //Setter
    public void setShadowEnabled(boolean isShadowEnabled) {
        this.isShadowEnabled = isShadowEnabled;
        setShadowHeight(0);
        refresh();
    }

    public void setButtonColor(int buttonColor) {
        this.mButtonColor = buttonColor;
        refresh();
    }


    public void setShadowHeight(int shadowHeight) {
        this.mShadowHeight = shadowHeight;
        refresh();
    }

    public void setCornerRadius(int cornerRadius) {
        this.mCornerRadius = cornerRadius;
        refresh();
    }


    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        refresh();
    }


    public void setAnimation(Animation animation) {

        switch (animation) {
            case FADEIN:
                this.mAnimation = 1;
                break;
            case FADEOUT:
                this.mAnimation = 2;
                break;
        }


    }

    public void setAnimationListner(HoyoAnimationListner hoyoAnimationListner) {
        this.hoyoAnimationListner = hoyoAnimationListner;
    }


    //Getter
    public boolean isShadowEnabled() {
        return isShadowEnabled;
    }

    public int getButtonColor() {
        return mButtonColor;
    }


    public int getShadowHeight() {
        return mShadowHeight;
    }

    public int getCornerRadius() {
        return mCornerRadius;
    }


    private void updateBackground() {
        if (mShape == 0) {
            this.setBackgroundColor(mButtonColor);
        } else if (mShape == 1) {
            drawable = getContext().getDrawable(R.drawable.circle);
            updateDrawable();
        } else if (mShape == 2) {
            drawable = getContext().getDrawable(R.drawable.rect);
            updateDrawable();
        }

    }

    private void updateDrawable() {
        if (mShape == 1) {
            StateListDrawable shapeDrawable = (StateListDrawable) drawable;
            DrawableContainer.DrawableContainerState dcs = (DrawableContainer.DrawableContainerState) shapeDrawable.getConstantState();
            Drawable[] drawableItems = dcs.getChildren();
            GradientDrawable gradientDrawableChecked = (GradientDrawable) drawableItems[0];
            gradientDrawableChecked.setColor(uShapeColor);
            gradientDrawableChecked.setStroke(uStrokeWidth, uStrokeColor);
            this.setBackground(drawable);
        } else if (mShape == 2) {
            GradientDrawable gradientDrawable = (GradientDrawable) drawable;
            gradientDrawable.setColor(uShapeColor);
            gradientDrawable.setStroke(uStrokeWidth, uStrokeColor);
            this.setBackground(drawable);
        }

    }


    @Override
    public void onClick(View view) {
        performSound(view);
        performAnimation(view);
    }

    private void performAnimation(final View view) {
        android.view.animation.Animation animation = null;
        if (mAnimation == 0) {
            return;
        } else if (mAnimation == 1) {
            animation = AnimationUtils.loadAnimation(getContext(), R.anim.fadein);


        } else if (mAnimation == 2) {
            animation = AnimationUtils.loadAnimation(getContext(), R.anim.fadeout);
        }
        animation.setDuration(manimationDuration);
        view.startAnimation(animation);
        view.startAnimation(animation);

        animation.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
            @Override
            public void onAnimationStart(android.view.animation.Animation animation) {

                if (hoyoAnimationListner != null) {
                    hoyoAnimationListner.onAnimationStart(animation);
                }

            }

            @Override
            public void onAnimationEnd(android.view.animation.Animation animation) {
                animation.cancel();
                if (hoyoAnimationListner != null) {
                    hoyoAnimationListner.onAnimationEnd(animation);
                }

            }

            @Override
            public void onAnimationRepeat(android.view.animation.Animation animation) {
                if (hoyoAnimationListner != null) {
                    hoyoAnimationListner.onAnimationRepeat(animation);
                }
            }
        });


    }


    private void playSound(int id) {
        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), id);
        mediaPlayer.start();
    }


    public void setHoyoClickListner(HoyoClickListner hoyoClickListner) {
        this.setOnClickListener(this);
        this.hoyoClickListner = hoyoClickListner;
    }


    public void setShape(Shape shape, int shape_color, int width, int stroke_color) {

        switch (shape) {
            case CIRCLE:
                mShape = 1;
                break;
            case RECT:
                mShape = 2;
                break;
        }

        this.uShapeColor = shape_color;
        this.uStrokeColor = stroke_color;
        this.uStrokeWidth = width;
        updateBackground();
    }


    public void setAnimationDuration(int duration) {
        this.manimationDuration = duration;
    }


   /* public void SetonClickSound(onClickSound onClickSound) {

        switch (onClickSound) {

            case FIRST:
                mClickSound = 1;
                break;


            case SECOUND:
                mClickSound = 2;

                break;


        }
    }*/


    public void Setcategory(Category category) {

        switch (category) {

            case Action:
                mCatgory = 1;
                break;


            case Menu:
                mCatgory = 2;
                break;


        }
    }


    private void performSound(View view) {
        int sound_id = 0;
        if (hoyoClickListner != null) {
            hoyoClickListner.onClick(view);
        }
        if (mCatgory == 0) {
            return;
        } else if (mCatgory == 1) {
            sound_id = R.raw.first;

        } else if (mCatgory == 2) {
            sound_id = R.raw.secound;
        }
        if (sound_id != 0)
            playSound(sound_id);
    }


   /* public enum onClickSound {
        FIRST, SECOUND
    }
*/

    public enum Shape {
        CIRCLE, RECT
    }


    public enum Animation {
        FADEIN, FADEOUT
    }


    public enum Category {
        Action, Menu
    }


}
