package com.example.jishorough2;

import android.content.Context;
import android.gesture.Gesture;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.MotionEventCompat;

public class EntryView extends androidx.appcompat.widget.AppCompatTextView
    implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private static final String DEBUG_TAG = "Entry View Gestures";
    private GestureDetectorCompat mDetector;
    private EntryGroup mEntryGroup;
    private Entry mEntry;

    public EntryView(Context context) {
        super(context);
        mDetector = new GestureDetectorCompat(this.getContext(), this);
        mDetector.setOnDoubleTapListener(this);
    }

    public EntryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDetector = new GestureDetectorCompat(this.getContext(), this);
        mDetector.setOnDoubleTapListener(this);
    }

    public EntryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDetector = new GestureDetectorCompat(this.getContext(), this);
        mDetector.setOnDoubleTapListener(this);
    }

    public EntryGroup getEntryGroup() {
        return mEntryGroup;
    }

    public boolean hasEntryGroup() {
        return mEntryGroup != null;
    }

    public void setEntryGroup(EntryGroup mEntryGroup) {
        this.mEntryGroup = mEntryGroup;
    }

    public Entry getEntry() {
        return mEntry;
    }

    public void setEntry(Entry mEntry) {
        this.mEntry = mEntry;
    }
    public boolean hasEntry() {
        return mEntry != null;
    }

    // Called when the activity is first created.
    @Override
    public boolean onTouchEvent(MotionEvent event){
        Log.d(DEBUG_TAG, "touched "+this.toString());
        if (this.mDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }




    @Override
    public boolean onDown(MotionEvent event) {
        Log.d(DEBUG_TAG,"onDown: " + event.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX,
                            float distanceY) {
        Log.d(DEBUG_TAG, "onScroll: " + event1.toString() + event2.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());
        return true;
    }

    @Override
    public String toString() {
        return "EntryView{" +
                "mEntryGroup=" + mEntryGroup +
                ", mEntry=" + mEntry +
                '}';
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapConfirmed: " + event.toString());
        return true;
    }
}


