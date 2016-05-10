package com.gr.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by gr on 16-5-9.
 */
public class TabBottom extends View {
    private int color;
    private Bitmap icon;
    private String text;
    private float textSize;

    private float alpha;

    private Bitmap bitmap;
    private Canvas canvas;
    private Paint iconPaint;

    private Paint textPaint;

    private Rect iconRect;
    private Rect textBounds;

    public TabBottom(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TabBottom);
        icon = ((BitmapDrawable) array.getDrawable(R.styleable.TabBottom_tabIcon)).getBitmap();
        color = array.getColor(R.styleable.TabBottom_tabColor, 0x45c01a);
        text = array.getString(R.styleable.TabBottom_text);
        textSize = array.getDimension(R.styleable.TabBottom_textSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,12,getResources().getDisplayMetrics()));
        array.recycle();

        textBounds = new Rect();
        textPaint = new Paint();
        textPaint.setTextSize(textSize);
        textPaint.getTextBounds(text, 0, text.length(), textBounds);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight()
                , getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - textBounds.height());
        int left = getMeasuredWidth() / 2 - iconWidth / 2;
        int top = getMeasuredHeight() / 2 - iconWidth / 2 - textBounds.height() / 2;

        iconRect = new Rect(left, top, left + iconWidth, top + iconWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(icon, null, iconRect, null);

        bitmap=Bitmap.createBitmap(getMeasuredWidth(),getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        this.canvas=new Canvas(bitmap);
        iconPaint=new Paint();
        iconPaint.setColor(color);
        iconPaint.setAntiAlias(true);
        iconPaint.setDither(true);
        iconPaint.setAlpha(Math.round(alpha*255));
        this.canvas.drawRect(iconRect,iconPaint);
        iconPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        iconPaint.setAlpha(255);
        this.canvas.drawBitmap(icon,null,iconRect,iconPaint);

        textPaint.setColor(0x9e9e9e);
        textPaint.setAlpha(255-Math.round(alpha*255));
        canvas.drawText(text,getMeasuredWidth()/2-textBounds.width()/2,iconRect.bottom+textBounds.height(),textPaint);

        textPaint.setColor(color);
        textPaint.setAlpha(Math.round(alpha*255));
        canvas.drawText(text,getMeasuredWidth()/2-textBounds.width()/2,iconRect.bottom+textBounds.height(),textPaint);

        canvas.drawBitmap(bitmap,0,0,null);
    }

    public void setIconAlpha(float alpha){
        this.alpha=alpha;
        if(Looper.getMainLooper()==Looper.myLooper()){
            invalidate();
        }else {
            postInvalidate();
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle=new Bundle();
        bundle.putParcelable("instance_state",super.onSaveInstanceState());
        bundle.putFloat("state_alpha",alpha);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle){
            Bundle bundle= (Bundle) state;
            alpha=bundle.getFloat("state_alpha");
            super.onRestoreInstanceState(bundle.getParcelable("instance_state"));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
