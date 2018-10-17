package com.imoroney.barcodedetect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private ImageView myImageView;
    private Paint myRectPaint;
    private Bitmap myBitmap;
    private FaceDetector faceDetector;
    private Frame frame ;
    private SparseArray<Face> faces ;
    private String msg_err="Could not set up the face detector!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadTheImage();
                createPaintObject();
                createCanvasObject(myBitmap);
                createFaceDetector(v);
            }
        });
    }

    public void loadTheImage() {
        myImageView = (ImageView) findViewById(R.id.imgview);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        myBitmap = BitmapFactory.decodeResource(
                getApplicationContext().getResources(),
                R.drawable.test1,
                options);

    }

    public void createPaintObject() {
        myRectPaint = new Paint();
        myRectPaint.setStrokeWidth(5);
        myRectPaint.setColor(Color.RED);
        myRectPaint.setStyle(Paint.Style.STROKE);
    }

    public void createCanvasObject(Bitmap myBitmap) {
        Bitmap tempBitmap = Bitmap.createBitmap(myBitmap.getWidth(),
                                                myBitmap.getHeight(),
                                                Bitmap.Config.RGB_565);
        Canvas tempCanvas = new Canvas(tempBitmap);
        tempCanvas.drawBitmap(myBitmap, 0, 0, null);
    }

    public void createFaceDetector(View v) {
        faceDetector = new FaceDetector.Builder(getApplicationContext()).setTrackingEnabled(false)
                .build();
        if (!faceDetector.isOperational()) {
            new AlertDialog.Builder(v.getContext()).setMessage(msg_err).show();
            return;
        }
    }
    public void detectFace() {
        frame = new Frame.Builder().setBitmap(myBitmap).build();
        faces = faceDetector.detect(frame);
    }

}
