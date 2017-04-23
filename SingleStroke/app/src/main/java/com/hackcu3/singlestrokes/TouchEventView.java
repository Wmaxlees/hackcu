package com.hackcu3.singlestrokes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nivinantonalexislawrence on 4/22/17.
 */

public class TouchEventView extends View {
    private Paint paint = new Paint();
    private Path path = new Path();
    private Path path_add = new Path();
    public List<Float> listOfPointX = new ArrayList<>();
    public List<Float> listOfPointY = new ArrayList<>();

    Context context;

    GestureDetector gestureDetector;

    public TouchEventView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gestureDetector = new GestureDetector(context, new GestureListener());
        this.context = context;

        paint.setAntiAlias(true); // Smoother lines
        paint.setStrokeWidth(6f);  // Strokes Width
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        this.setDrawingCacheEnabled(true);
        //Log.e("Hello", "Test");
    }

    public void setColor(int r, int g, int b) {
        int rgb = Color.rgb(r, g, b);
        paint.setColor(rgb);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            float x = e.getX();
            float y = e.getY();

            // clean drawing area on double tap
            path.reset();
            Log.d("Double Tap", "Tapped at: (" + x + "," + y + ")");

            return true;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // start position
                path.moveTo(eventX, eventY);
                listOfPointX.add(eventX);
                listOfPointY.add(eventY);
                path_add.moveTo(eventX, eventY);
                return true;
            case MotionEvent.ACTION_MOVE:

                path.lineTo(eventX, eventY);
                listOfPointX.add(eventX);
                listOfPointY.add(eventY);
                path_add.lineTo(eventX, eventY);

                break;
            case MotionEvent.ACTION_UP:
                PointsDraw pointsDraw = convertToData(listOfPointX, listOfPointY);
                String data = new Gson().toJson(pointsDraw).toString();
                //Log.e(TouchEventView.class.getName(), data);
                CallAPI callAPI = new CallAPI();
                callAPI.execute(pointsDraw);
                listOfPointX.clear();
                listOfPointY.clear();
                path.reset();

                break;
            default:
                return false;
        }

        // for demostraction purposes
        gestureDetector.onTouchEvent(event);
        // Schedules a repaint.
        invalidate();
        return true;
    }

    public PointsDraw convertToData(List<Float> listOfPointX, List<Float> listOfPointY) {
        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE;
        float maxY = Float.MIN_VALUE;
        int argminX = 0;
        int argminY = 0;
        int argmaxX = 0;
        int argmaxY = 0;
        for (int i = 0; i < listOfPointX.size(); ++i) {
            if (listOfPointX.get(i) < minX) {
                minX = listOfPointX.get(i);
                argminX = i;
            }

            if (listOfPointX.get(i) > maxX) {
                maxX = listOfPointX.get(i);
                argmaxX = i;
            }
        }

        for (int i = 0; i < listOfPointY.size(); ++i) {
            if (listOfPointY.get(i) < minY) {
                minY = listOfPointY.get(i);
                argminY = i;
            }

            if (listOfPointY.get(i) > maxY) {
                maxY = listOfPointY.get(i);
                argmaxY = i;
            }
        }

        float scaleRatio = 1;
        if ((maxY - minY) > (maxX - minX)) {
            scaleRatio = 27 / (maxY - minY);
        } else {
            scaleRatio = 27 / (maxX - minX);
        }

        List<Byte> bytesX = new ArrayList<>();
        List<Byte> bytesY = new ArrayList<>();
        int prevx=0, prevy=0;
        for (int i = 0; i < listOfPointX.size(); ++i) {

            int currX, currY;
            listOfPointX.set(i, (listOfPointX.get(i) - minX) * scaleRatio);
            listOfPointY.set(i, (listOfPointY.get(i) - minY) * scaleRatio);
            currX = (byte) (int) Math.floor(listOfPointX.get(i));
            currY = (byte) (int) Math.floor(listOfPointY.get(i));
            if (i > 0 && prevx != currX && prevy != currY) {
                bytesX.add((byte) currX);
                bytesY.add((byte) currY);
            }
            prevx = currX;
            prevy = currY;
            //listOfPointX.set(i, (listOfPointX.get(i) - minX) * scaleRatio);
            //bytesX.add((byte) (int) Math.floor(listOfPointX.get(i)));
            //listOfPointY.set(i, (listOfPointY.get(i) - minY) * scaleRatio);
            //bytesY.add((byte) (int) Math.floor(listOfPointY.get(i)));
        }


        PointsDraw pointsDraw = new PointsDraw(bytesX, bytesY, "circle");

        return pointsDraw;


    }


    public class CallAPI extends AsyncTask<PointsDraw, PointsDraw, PointsDraw> {

        public CallAPI() {
            //set context variables if required
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected PointsDraw doInBackground(PointsDraw... params) {
            String resultToDisplay = "";

            InputStream in = null;
            try {

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://ec2-52-43-9-170.us-west-2.compute.amazonaws.com/upload");

                try {
                    // Add your data
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);

                    //nameValuePairs.add(new BasicNameValuePair("data", params[0]));
                    nameValuePairs.add(new BasicNameValuePair("x", params[0].getPointsX().toString()));
                    nameValuePairs.add(new BasicNameValuePair("y", params[0].getPointsY().toString()));
                    nameValuePairs.add(new BasicNameValuePair("shape", "square"));

                    Log.e(TouchEventView.class.getName(), params[0].getPointsX().toString());
                    Log.e(TouchEventView.class.getName(), params[0].getPointsY().toString());
                    //nameValuePairs.add(new BasicNameValuePair("datae", params[0]));
                    //nameValuePairs.add(new )

                    //nameValuePairs.add(new BasicNameValuePair("shape", "circle"));


                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    // Execute HTTP Post Request
                    HttpResponse response = httpclient.execute(httppost);

                    Log.e(TouchEventView.class.getName(), response.toString());


                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                }


            } catch (Exception e) {
                System.out.println(e.getMessage());
                return null;
                //return e.getMessage();

            }
            return null;
        }


        @Override
        protected void onPostExecute(PointsDraw result) {
            //Update the UI
        }


    }
}
