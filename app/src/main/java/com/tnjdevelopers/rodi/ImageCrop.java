package com.tnjdevelopers.rodi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.view.MotionEvent.INVALID_POINTER_ID;

public class ImageCrop extends AppCompatActivity {

    ImageView iv[]=new ImageView[4];
    static ImageView imdoc;
    String TAG="ImageCrop";
    private int mActivePointerId = INVALID_POINTER_ID;
    public float mLastTouchX[]=new float[4],mLastTouchY[]=new float[4],mPosX[]=new float[4],mPosY[]=new float[4];
    public int xwhole,ywhole,counter=2;
    static Bitmap bitmap,bmp1;

    private static final int OPEN_REQUEST_CODE_RESULT=0;
    ImageView imageToCrop=null;
    public int xim=0,yim=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_crop);
        imageToCrop=(ImageView)findViewById(R.id.imageToCrop);
        xwhole=imageToCrop.getMaxWidth();
        ywhole=imageToCrop.getMaxHeight();

        if(ChooseImage.choice==1)
        {
            Intent intent=new Intent(Intent.ACTION_OPEN_DOCUMENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent,OPEN_REQUEST_CODE_RESULT);
        }
        else
        {

        }


        iv[0] = (ImageView) findViewById(R.id.imageCircleTopLeft);
        iv[1] = (ImageView) findViewById(R.id.imageCircleTopRight);
        iv[2] = (ImageView) findViewById(R.id.imageCircleBottomLeft);
        iv[3] = (ImageView) findViewById(R.id.imageCircleBottomRight);
        imdoc=(ImageView)findViewById(R.id.imageToCrop);

        mPosX[0]=73;mPosX[1]=344;mPosX[2]=73;mPosX[3]=344;
        mPosY[0]=49;mPosY[1]=49;mPosY[2]=541;mPosY[3]=541;
        mLastTouchX[0]=73;mLastTouchX[1]=344;mLastTouchX[2]=73;mLastTouchX[3]=344;
        mLastTouchY[0]=49;mLastTouchY[1]=49;mLastTouchY[2]=541;mLastTouchY[3]=541;

        /*mLastTouchX[1]=imdoc.getWidth()+((xwhole-imdoc.getWidth())/2);
        mLastTouchX[3]=imdoc.getWidth()+((xwhole-imdoc.getWidth())/2);
        mLastTouchY[2]=imdoc.getHeight()+((ywhole-imdoc.getHeight())/2);
        mLastTouchY[3]=imdoc.getHeight()+((ywhole-imdoc.getHeight())/2);

        mPosX[1]=mLastTouchX[1];
        mPosX[3]=mLastTouchX[3];
        mPosY[2]=mLastTouchY[2];
        mPosY[3]=mLastTouchY[3];*/

        iv[0].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent ev) {


                final int action = MotionEventCompat.getActionMasked(ev);

                switch (action) {
                    case MotionEvent.ACTION_DOWN: {
                        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                        final float x = MotionEventCompat.getX(ev, pointerIndex);
                        final float y = MotionEventCompat.getY(ev, pointerIndex);

                        // Remember where we started (for dragging)
                        mLastTouchX[0] = x;
                        mLastTouchY[0] = y;
                        // Save the ID of this pointer (for dragging)
                        mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                        break;
                    }

                    case MotionEvent.ACTION_MOVE: {
                        // Find the index of the active pointer and fetch its position
                        final int pointerIndex =
                                MotionEventCompat.findPointerIndex(ev, mActivePointerId);

                        final float x = MotionEventCompat.getX(ev, pointerIndex);
                        final float y = MotionEventCompat.getY(ev, pointerIndex);

                        // Calculate the distance moved
                        final float dx = x - mLastTouchX[0];
                        final float dy = y - mLastTouchY[0];

                        mPosX[0] += dx;
                        mPosY[0] += dy;

                        iv[0].invalidate();

                        // Remember this touch position for the next move event
                        mLastTouchX[0] = x;
                        mLastTouchY[0] = y;

                        break;
                    }

                    case MotionEvent.ACTION_UP: {
                        mActivePointerId = INVALID_POINTER_ID;
                        break;
                    }

                    case MotionEvent.ACTION_CANCEL: {
                        mActivePointerId = INVALID_POINTER_ID;
                        break;
                    }

                    case MotionEvent.ACTION_POINTER_UP: {

                        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);

                        if (pointerId == mActivePointerId) {
                            // This was our active pointer going up. Choose a new
                            // active pointer and adjust accordingly.
                            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                            mLastTouchX[0] = MotionEventCompat.getX(ev, newPointerIndex);
                            mLastTouchY[0] = MotionEventCompat.getY(ev, newPointerIndex);
                            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
                        }
                        break;
                    }
                }
                iv[0].setX(mPosX[0]);
                iv[0].setY(mPosY[0]);
                System.out.println("x: "+mPosX[0]+"  y: "+mPosY[0]);


                return true;
            }
        });

        iv[1].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent ev) {


                final int action = MotionEventCompat.getActionMasked(ev);

                switch (action) {
                    case MotionEvent.ACTION_DOWN: {
                        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                        final float x = MotionEventCompat.getX(ev, pointerIndex);
                        final float y = MotionEventCompat.getY(ev, pointerIndex);

                        // Remember where we started (for dragging)
                        mLastTouchX[1] = x;
                        mLastTouchY[1] = y;
                        // Save the ID of this pointer (for dragging)
                        mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                        break;
                    }

                    case MotionEvent.ACTION_MOVE: {
                        // Find the index of the active pointer and fetch its position
                        final int pointerIndex =
                                MotionEventCompat.findPointerIndex(ev, mActivePointerId);

                        final float x = MotionEventCompat.getX(ev, pointerIndex);
                        final float y = MotionEventCompat.getY(ev, pointerIndex);

                        // Calculate the distance moved
                        final float dx = x - mLastTouchX[1];
                        final float dy = y - mLastTouchY[1];

                        mPosX[1] += dx;
                        mPosY[1] += dy;

                        iv[1].invalidate();

                        // Remember this touch position for the next move event
                        mLastTouchX[1] = x;
                        mLastTouchY[1] = y;

                        break;
                    }

                    case MotionEvent.ACTION_UP: {
                        mActivePointerId = INVALID_POINTER_ID;
                        break;
                    }

                    case MotionEvent.ACTION_CANCEL: {
                        mActivePointerId = INVALID_POINTER_ID;
                        break;
                    }

                    case MotionEvent.ACTION_POINTER_UP: {

                        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);

                        if (pointerId == mActivePointerId) {
                            // This was our active pointer going up. Choose a new
                            // active pointer and adjust accordingly.
                            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                            mLastTouchX[1] = MotionEventCompat.getX(ev, newPointerIndex);
                            mLastTouchY[1] = MotionEventCompat.getY(ev, newPointerIndex);
                            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
                        }
                        break;
                    }
                }
                iv[1].setX(mPosX[1]);
                iv[1].setY(mPosY[1]);
                System.out.println("x: "+mPosX[1]+"  y: "+mPosY[1]);
                return true;
            }
        });

        iv[2].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent ev) {


                final int action = MotionEventCompat.getActionMasked(ev);

                switch (action) {
                    case MotionEvent.ACTION_DOWN: {
                        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                        final float x = MotionEventCompat.getX(ev, pointerIndex);
                        final float y = MotionEventCompat.getY(ev, pointerIndex);

                        // Remember where we started (for dragging)
                        mLastTouchX[2] = x;
                        mLastTouchY[2] = y;
                        // Save the ID of this pointer (for dragging)
                        mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                        break;
                    }

                    case MotionEvent.ACTION_MOVE: {
                        // Find the index of the active pointer and fetch its position
                        final int pointerIndex =
                                MotionEventCompat.findPointerIndex(ev, mActivePointerId);

                        final float x = MotionEventCompat.getX(ev, pointerIndex);
                        final float y = MotionEventCompat.getY(ev, pointerIndex);

                        // Calculate the distance moved
                        final float dx = x - mLastTouchX[2];
                        final float dy = y - mLastTouchY[2];

                        mPosX[2] += dx;
                        mPosY[2] += dy;

                        iv[2].invalidate();

                        // Remember this touch position for the next move event
                        mLastTouchX[2] = x;
                        mLastTouchY[2] = y;

                        break;
                    }

                    case MotionEvent.ACTION_UP: {
                        mActivePointerId = INVALID_POINTER_ID;
                        break;
                    }

                    case MotionEvent.ACTION_CANCEL: {
                        mActivePointerId = INVALID_POINTER_ID;
                        break;
                    }

                    case MotionEvent.ACTION_POINTER_UP: {

                        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);

                        if (pointerId == mActivePointerId) {
                            // This was our active pointer going up. Choose a new
                            // active pointer and adjust accordingly.
                            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                            mLastTouchX[2] = MotionEventCompat.getX(ev, newPointerIndex);
                            mLastTouchY[2] = MotionEventCompat.getY(ev, newPointerIndex);
                            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
                        }
                        break;
                    }
                }
                iv[2].setX(mPosX[2]);
                iv[2].setY(mPosY[2]);
                System.out.println("x: "+mPosX[2]+"  y: "+mPosY[2]);

                return true;
            }
        });

        iv[3].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent ev) {


                final int action = MotionEventCompat.getActionMasked(ev);

                switch (action) {
                    case MotionEvent.ACTION_DOWN: {
                        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                        final float x = MotionEventCompat.getX(ev, pointerIndex);
                        final float y = MotionEventCompat.getY(ev, pointerIndex);

                        // Remember where we started (for dragging)
                        mLastTouchX[3] = x;
                        mLastTouchY[3] = y;
                        // Save the ID of this pointer (for dragging)
                        mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                        break;
                    }

                    case MotionEvent.ACTION_MOVE: {
                        // Find the index of the active pointer and fetch its position
                        final int pointerIndex =
                                MotionEventCompat.findPointerIndex(ev, mActivePointerId);

                        final float x = MotionEventCompat.getX(ev, pointerIndex);
                        final float y = MotionEventCompat.getY(ev, pointerIndex);

                        // Calculate the distance moved
                        final float dx = x - mLastTouchX[3];
                        final float dy = y - mLastTouchY[3];

                        mPosX[3] += dx;
                        mPosY[3] += dy;

                        iv[3].invalidate();

                        // Remember this touch position for the next move event
                        mLastTouchX[3] = x;
                        mLastTouchY[3] = y;

                        break;
                    }

                    case MotionEvent.ACTION_UP: {
                        mActivePointerId = INVALID_POINTER_ID;
                        break;
                    }

                    case MotionEvent.ACTION_CANCEL: {
                        mActivePointerId = INVALID_POINTER_ID;
                        break;
                    }

                    case MotionEvent.ACTION_POINTER_UP: {

                        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);

                        if (pointerId == mActivePointerId) {
                            // This was our active pointer going up. Choose a new
                            // active pointer and adjust accordingly.
                            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                            mLastTouchX[3] = MotionEventCompat.getX(ev, newPointerIndex);
                            mLastTouchY[3] = MotionEventCompat.getY(ev, newPointerIndex);
                            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
                        }
                        break;
                    }
                }
                iv[3].setX(mPosX[3]);
                iv[3].setY(mPosY[3]);
                System.out.println("x: "+mPosX[3]+"  y: "+mPosY[3]);

                return true;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent resultData)
    {
        super.onActivityResult(requestCode, resultCode, resultData);
        imageToCrop.setAdjustViewBounds(true);


        if(requestCode==OPEN_REQUEST_CODE_RESULT && resultCode==RESULT_OK)
        {
            Uri uri=null;
            if(resultData!=null)
            {
                uri=resultData.getData();
                try
                {
                    Bitmap bitmap= getBitmapFromUri(uri);
                    yim=bitmap.getHeight();
                    xim=bitmap.getWidth();

                    Bitmap bmpGray = Bitmap.createBitmap(xim, yim, Bitmap.Config.ARGB_8888);
                    Canvas c = new Canvas(bmpGray);
                    Paint paint = new Paint();
                    ColorMatrix cm = new ColorMatrix();
                    cm.setSaturation(1);
                    ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
                    paint.setColorFilter(f);
                    c.drawBitmap(bitmap, 0, 0, paint);

                    imageToCrop.setImageBitmap(bmpGray);

                    /*imageView.setColorFilter(255, PorterDuff.Mode.DARKEN);*/


                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    private Bitmap getBitmapFromUri(Uri uri) throws IOException
    {
        ParcelFileDescriptor parcelFileDescriptor=getContentResolver().openFileDescriptor(uri,"r");
        FileDescriptor fileDescriptor=parcelFileDescriptor.getFileDescriptor();
        Bitmap bitmap= BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return bitmap;
    }
    public void onRectify(View view)
    {
        int x=2000,y=2000,xmax=0,ymax=0,width=0,height=0;
        imdoc.buildDrawingCache(true);
        imdoc.buildDrawingCache();


        bitmap=imdoc.getDrawingCache();
        for(int i=0;i<4;i++)
        {
            if(x>mPosX[i])
            {
                x=(int)mPosX[i];
            }
            if(y>mPosY[i])
            {
                y=(int)mPosY[i];
            }
            if(xmax<mPosX[i])
            {
                xmax=(int)mPosX[i];
            }
            if(ymax<mPosY[i])
            {
                ymax=(int)mPosY[i];
            }
        }
        width=xmax-x;
        height=ymax-y;


        bmp1=Bitmap.createBitmap(bitmap,x,y,width,height);

        Matrix matrix = new Matrix();

        matrix.postRotate(90);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bmp1,width,height,true);

        if(width>height)
        {
            bmp1 = Bitmap.createBitmap(scaledBitmap , 0, 0, scaledBitmap .getWidth(), scaledBitmap .getHeight(), matrix, true);
        }
        imdoc.setImageBitmap(bmp1);
        iv[0].setVisibility(View.INVISIBLE);
        iv[1].setVisibility(View.INVISIBLE);
        iv[2].setVisibility(View.INVISIBLE);
        iv[3].setVisibility(View.INVISIBLE);

    }

    public void onSaveImage(View view)
    {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d(TAG,
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            bmp1.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        }
        catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        }
        catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
    }

    /** Create a File for saving an image or video */
    private  File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getRootDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    public void onConvertGrayscale(View view)
    {
        Bitmap bmpGray = Bitmap.createBitmap(bmp1.getWidth(),bmp1.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGray);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmp1, 0, 0, paint);

        imageToCrop.setImageBitmap(bmpGray);
    }
}
