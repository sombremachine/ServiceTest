package com.example.darkness.servicetest.Activities.CustomViews;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.darkness.servicetest.R;
import com.example.darkness.servicetest.WeatherHelpers;
import com.example.darkness.servicetest.WeatherSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class MainView extends View {
    private final String TAG = "MainView";
    private WeatherSnapshot snapShot;
    private ViewPager viewPager;
    private int width;
    private int height;
    private int position = 0;
    private HashMap<WeatherHelpers.WeatherType,Drawable> icons = new HashMap<>();
    private Paint paintNormal = new Paint();
    private Paint paintBlur = new Paint();

    private Random random = new Random();

    public WeatherSnapshot getSnapShot() {
        return snapShot;
    }

    public void setSnapShot(WeatherSnapshot snapShot) {
        this.snapShot = snapShot;
    }

    private void loadIcons(){
        icons.put(WeatherHelpers.WeatherType.clear, ContextCompat.getDrawable(getContext(), R.drawable.ic_large_sunny));
        icons.put(WeatherHelpers.WeatherType.few_clouds,ContextCompat.getDrawable(getContext(), R.drawable.ic_large_sunny_to_cloudy));
        icons.put(WeatherHelpers.WeatherType.storm,ContextCompat.getDrawable(getContext(), R.drawable.ic_large_heavy_rain));
        icons.put(WeatherHelpers.WeatherType.snow,ContextCompat.getDrawable(getContext(), R.drawable.ic_large_snowy));

//        paintBlur.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.SOLID));
        paintBlur.setShadowLayer(12, 1, 1, Color.BLACK);

        // Important for certain APIs
        setLayerType(LAYER_TYPE_SOFTWARE, paintBlur);
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
    private int setTextSizeForHeight(Paint paint, float desiredHeight, String text) {

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
        float desiredTextSize = testTextSize * desiredHeight / bounds.height();

        // Set the paint for that size.
        paint.setTextSize(desiredTextSize);
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.width();
    }
    private int getRandomColor(){
        return Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = canvas.getWidth();
        height = canvas.getHeight();

        paintNormal.setColor(0xFF006400);
        paintNormal.setColor(getRandomColor());
        canvas.drawRect(0,0,width, height, paintNormal);

//        paint.setColor(0xFF7CFC00);
//        canvas.drawRect((width / 7) * (position),0,(width / 7) * (position + 1),height - 10, paint);
        Drawable dr = icons.get(WeatherHelpers.WeatherType.clear);
        if (snapShot != null) {
            if (snapShot.isSnowing()) {
                dr = icons.get(WeatherHelpers.WeatherType.snow);
            }
            if (snapShot.isRaining()) {
                dr = icons.get(WeatherHelpers.WeatherType.storm);
            }

            dr.setBounds((width / 4), (width / 20), (3 * width / 4), (width / 2) + (width / 20));
            dr.draw(canvas);

            paintBlur.setColor(0xFF000000);
            int textwidth = setTextSizeForHeight(paintBlur, (float) (width * 0.2), "" + snapShot.getTemperature() + "°С");
            canvas.drawText("" + snapShot.getTemperature() + "°С", (float) ((width / 2) - (textwidth / 2)), (float) (width * 0.7), paintBlur);

//        paint.reset();
//        paint.setMaskFilter(null);
            paintNormal.setColor(0xFFFFFFFF);
            textwidth = setTextSizeForHeight(paintNormal, (float) (width * 0.2), "" + snapShot.getTemperature() + "°С");
            canvas.drawText("" + snapShot.getTemperature() + "°С", (float) ((width / 2) - (textwidth / 2)), (float) (width * 0.7), paintNormal);
        }
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
