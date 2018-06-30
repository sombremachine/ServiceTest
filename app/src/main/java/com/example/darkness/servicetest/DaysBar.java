package com.example.darkness.servicetest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class DaysBar extends View {
    private final String TAG = "DaysBar";
    private ViewPager viewPager;
    private int width;
    private int height;
    private int position = 0;
    private HashMap<WeatherHelpers.WeatherType,Drawable> smallIcons = new HashMap<>();
    private ArrayList<WeatherSnapshot> weatherDtata;

    public ArrayList<WeatherSnapshot> getWeatherDtata() {
        return weatherDtata;
    }

    public void setWeatherDtata(ArrayList<WeatherSnapshot> weatherDtata) {
        this.weatherDtata = weatherDtata;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }


    public void setPosition(int position) {
        this.position = position;
        this.invalidate();
        Log.d(TAG,"position : " + position);
    }

    private void loadIcons(){
        smallIcons.put(WeatherHelpers.WeatherType.clear,ContextCompat.getDrawable(getContext(), R.drawable.ic_weather_clear));
        smallIcons.put(WeatherHelpers.WeatherType.few_clouds,ContextCompat.getDrawable(getContext(), R.drawable.ic_weather_few_clouds));
        smallIcons.put(WeatherHelpers.WeatherType.storm,ContextCompat.getDrawable(getContext(), R.drawable.ic_weather_storm));
        smallIcons.put(WeatherHelpers.WeatherType.snow,ContextCompat.getDrawable(getContext(), R.drawable.ic_weather_snow));
    }

    public DaysBar(Context context) {
        super(context);
        loadIcons();
    }

    public DaysBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        loadIcons();
    }

    public DaysBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

        paint.setColor(0xFF7CFC00);
        canvas.drawRect((width / 7) * (position),0,(width / 7) * (position + 1),height - 10, paint);
        Resources res = getResources();

        for (int i = 0; i < 7; i++) {
            WeatherSnapshot snap = weatherDtata.get(i);
            if (snap != null) {
                Drawable dr = smallIcons.get(WeatherHelpers.WeatherType.clear);

                if (snap.isSnowing) {
                    dr = smallIcons.get(WeatherHelpers.WeatherType.snow);
                }
                if (snap.isRaining) {
                    dr = smallIcons.get(WeatherHelpers.WeatherType.storm);
                }

                dr.setBounds((width / 7) * (i), 0, (width / 7) * (i + 1), 100);
                dr.draw(canvas);


                Calendar c = Calendar.getInstance();
                c.setTime(snap.date);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
                int date = c.getTime().getDate();

                paint.setColor(0xFFFFFFFF);
                setTextSizeForWidth(paint, (width / 14), "12");

                canvas.drawText("" + snap.temperature, (width / 7) * (i) + 25, (float) (height * 0.35), paint);

                canvas.drawText("" + WeatherHelpers.days[dayOfWeek], (width / 7) * (i) + (width / 28), (float) (height * 0.5), paint);

                canvas.drawText("" + date, (width / 7) * (i) + 25, (float) (height * 0.66), paint);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        this.width = widthMeasureSpec;
//        this.height = 100;
        Log.d(TAG,"width : " + width);
        setMeasuredDimension(widthMeasureSpec,400);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float pointX = event.getX();
        float pointY = event.getY();
        int action = MotionEventCompat.getActionMasked(event);
        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                Log.d("s", "Action was DOWN");
                int screenNumber = (int) pointX / (width / 7);
                viewPager.setCurrentItem(screenNumber);
                break;
            case (MotionEvent.ACTION_MOVE) :
                Log.d("s","Action was MOVE");
                break;
            case (MotionEvent.ACTION_UP) :
                Log.d("s", "Action was UP");
                break;
            case (MotionEvent.ACTION_CANCEL) :
                Log.d("s","Action was CANCEL");
                break;
            case (MotionEvent.ACTION_OUTSIDE) :
                Log.d("s","Movement occurred outside bounds " +
                        "of current screen element");
                break;
        }

        return true;
    }

}
