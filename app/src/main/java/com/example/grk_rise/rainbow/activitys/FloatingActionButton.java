package com.example.grk_rise.rainbow.activitys;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;


/**
 * Created by GrK_Rise on 05.04.2015.
 */
public class FloatingActionButton extends View {
    Context context;
    Paint mButtonPaint;
    Paint mDrawblePaint;
    Bitmap bitmap;
    boolean mHidden = false;




    public FloatingActionButton(Context context) {
        super(context);
        this.context = context;
        init(Color.WHITE);
    }

    public void setFABColor(int fabColor)
    {
        init(fabColor);
    }

    public void setFABDrawble(Drawable fabDrawble){
        bitmap = ((BitmapDrawable) fabDrawble).getBitmap();
        invalidate();
    }

    public void init(int fabColor){
        setWillNotDraw(false);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mButtonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mButtonPaint.setColor(fabColor);
        mButtonPaint.setStyle(Paint.Style.FILL);
        mButtonPaint.setShadowLayer(10.0f, 0.0f, 3.5f, Color.argb(100, 0, 0, 0));
        mDrawblePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        setClickable(true);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, (float) (getWidth() / 2.6), mButtonPaint);
        canvas.drawBitmap(bitmap, (getWidth() - bitmap.getWidth()) / 2,
                (getHeight() - bitmap.getHeight()) / 2, mDrawblePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_UP){
            setAlpha(1.0f);
        }else if(event.getAction() == MotionEvent.ACTION_DOWN){
            setAlpha(0.6f);
        }

        return super.onTouchEvent(event);
    }

    public void hideFAB(){
        if(!mHidden){
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 1, 0);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 1, 0);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(scaleX, scaleY);
            animatorSet.setInterpolator(new AccelerateInterpolator());
            animatorSet.setDuration(200);
            animatorSet.start();
            mHidden = true;
        }
    }

    public void showFAB(){
        if(mHidden){
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 0, 1);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 0, 1);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(scaleX, scaleY);
            animatorSet.setInterpolator(new OvershootInterpolator());
            animatorSet.setDuration(200);
            animatorSet.start();
            mHidden = false;
        }
    }

    public boolean isHidden(){
        return mHidden;
    }

    static public class Builder{
        private FrameLayout.LayoutParams params;
        private final Activity activity;
        int gravity = Gravity.BOTTOM | Gravity.RIGHT;
        boolean visible = true;
        Drawable drawable;
        int color = Color.WHITE;
        int size = 0;
        float scale = 0;


        public Builder(Activity activity) {
            scale = activity.getResources().getDisplayMetrics().density;
            size = (int) (72 * scale + 0.5f);
            params = new FrameLayout.LayoutParams(size, size);
            params.gravity = gravity;
            this.activity = activity;
        }

        public Builder withGravity(int gravity){
            this.gravity = gravity;
            return this;
        }

        public Builder withMargins(int l, int t, int r, int b){
            params.setMargins((int)(l * scale + 0.5f),
                    (int)(t * scale + 0.5f),
                    (int)(r * scale + 0.5f),
                    (int)(b * scale + 0.5f));
            return this;
        }

        public Builder withDrawble(final Drawable drawble){
            this.drawable = drawble;
            return this;
        }

        public Builder withButtonColor(final int color){
            this.color = color;
            return this;
        }

        public Builder withButtonSize(int size){
            size = (int)(size * scale + 0.5f);
            params = new FrameLayout.LayoutParams(size, size);
            return this;
        }

        public FloatingActionButton create(){
            final FloatingActionButton button = new FloatingActionButton(activity);
            button.setFABColor(this.color);
            button.setFABDrawble(this.drawable);
            params.gravity = this.gravity;
            ViewGroup root = (ViewGroup) activity.findViewById(android.R.id.content);
            root.addView(button, params);
            return button;
        }
    }
}