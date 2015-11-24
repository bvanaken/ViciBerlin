package de.beuth.bva.viciberlin.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;

/**
 * Created by betty on 24/11/15.
 */
public class CustomMapView extends MapView {

    private static final String TAG = "CustomMapView";
    String longitude;
    String latitude;

    public CustomMapView(final Context context, final AttributeSet attrs) {
        super(context, new DefaultResourceProxyImpl(context), null, null, attrs);
    }

    public CustomMapView(final Context context) {
        super(context, new DefaultResourceProxyImpl(context));
    }

    public CustomMapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, new DefaultResourceProxyImpl(context), null, null, attrs);
    }

    @TargetApi(21)
    public CustomMapView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, new DefaultResourceProxyImpl(context), null, null, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int actionType = ev.getAction();
        switch (actionType) {
            case MotionEvent.ACTION_DOWN:
                Projection proj = this.getProjection();
                IGeoPoint loc = proj.fromPixels((int) ev.getX(), (int) ev.getY());
                longitude = Double.toString(((double)loc.getLongitudeE6())/1000000);
                latitude = Double.toString(((double) loc.getLatitudeE6()) / 1000000);
        }
        gestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    final GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
        @Override
        public void onLongPress(MotionEvent e) {
            Toast toast = Toast.makeText(getContext(), "Longitude: "+ longitude +" Latitude: "+ latitude , Toast.LENGTH_LONG);
            toast.show();
            Log.d(TAG, "Longitude: "+ longitude +" Latitude: "+ latitude);
        }
    });

}

