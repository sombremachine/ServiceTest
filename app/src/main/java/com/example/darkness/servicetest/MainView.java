package com.example.darkness.servicetest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MainView extends View {
    private final String TAG = "MainView";
    private WeatherSnapshot snapShot;
    private ViewPager viewPager;
    private int width;
    private int height;
    private int position = 0;
    private HashMap<WeatherHelpers.WeatherType,Drawable> icons = new HashMap<>();

    public WeatherSnapshot getSnapShot() {
        return snapShot;
    }

    public void setSnapShot(WeatherSnapshot snapShot) {
        this.snapShot = snapShot;
    }

    private void loadIcons(){
        icons.put(WeatherHelpers.WeatherType.clear, ContextCompat.getDrawable(getContext(), R.drawable.ic_weather_clear));
        icons.put(WeatherHelpers.WeatherType.few_clouds,ContextCompat.getDrawable(getContext(), R.drawable.ic_weather_few_clouds));
        icons.put(WeatherHelpers.WeatherType.storm,ContextCompat.getDrawable(getContext(), R.drawable.ic_weather_storm));
        icons.put(WeatherHelpers.WeatherType.snow,ContextCompat.getDrawable(getContext(), R.drawable.ic_weather_snow));
    }

    public MainView(Context context) {
        super(context);
        loadIcons();
    }

    public MainView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        loadIcons();
    }

    public MainView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadIcons();
    }

    public MainView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        loadIcons();
    }

    private void setTextSizeForWidth(Paint paint, float desiredWidth, String text) {

        // Pick a reasonably large value for the test. Larger values produce
        // more accurate results, but may cause problems with hardware
        // acceleration. But there are workarounds for that, too; refer to
        // http://stackoverflow.com/questions/6253528/font-size-too-large-to-fit-in-cache
        final float testTextSize = 48f;

        // Get the bounds of the text, using our testTextSize.
        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        // Calculate the desired size as a proportion of our testTextSize.
        float desiredTextSize = testTextSize * desiredWidth / bounds.width();

        // Set the paint for that size.
        paint.setTextSize(desiredTextSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = canvas.getWidth();
        height = canvas.getHeight();

        Paint paint = new Paint();

        paint.setColor(0xFF006400);
        canvas.drawRect(0,0,width, height, paint);

//        paint.setColor(0xFF7CFC00);
//        canvas.drawRect((width / 7) * (position),0,(width / 7) * (position + 1),height - 10, paint);
        Resources res = getResources();

        Drawable dr = icons.get(WeatherHelpers.WeatherType.clear);
        if (snapShot != null) {
            if (snapShot.isSnowing) {
                dr = icons.get(WeatherHelpers.WeatherType.snow);
            }
            if (snapShot.isRaining) {
                dr = icons.get(WeatherHelpers.WeatherType.storm);
            }

            dr.setBounds((width / 4), (width / 6), (3 * width / 4), (width / 2)+(width / 6));
            dr.draw(canvas);
        }

        paint.setColor(0xFFFFFFFF);
        setTextSizeForWidth(paint, (width / 5), "12");

        canvas.drawText("" + snapShot.temperature, (width / 2) - (width / 10), (float) (width * 0.9), paint);
    }

    private int measureDimension(int desiredSize, int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = desiredSize;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        if (result < desiredSize){
            Log.e("ChartView", "The view is too small, the content might get cut");
        }
        return result;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG,"width : ");
        Log.v("Chart onMeasure w", MeasureSpec.toString(widthMeasureSpec));
        Log.v("Chart onMeasure h", MeasureSpec.toString(heightMeasureSpec));

        int desiredWidth = getSuggestedMinimumWidth() + getPaddingLeft() + getPaddingRight();
        int desiredHeight = getSuggestedMinimumHeight() + getPaddingTop() + getPaddingBottom();

        setMeasuredDimension(measureDimension(desiredWidth, widthMeasureSpec),
                measureDimension(desiredHeight, heightMeasureSpec));
    }
}
