package com.example.headerandfooter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;


public class Main2Activity extends AppCompatActivity {
    EditText header, footer;
    ImageView image1, image2;
    Button btnSave;
    public static final String TAG = "TAG";
    RelativeLayout layout,relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        layout = (RelativeLayout) findViewById(R.id.relativeLayout);
        relativeLayout = (RelativeLayout) findViewById(R.id.layout);
        header = (EditText) findViewById(R.id.text_header);

        Intent header_intent = getIntent();
        String string_header = header_intent.getStringExtra("header");
        header.setText(string_header);

        footer = (EditText) findViewById(R.id.text_footer);


        String string_footer = header_intent.getStringExtra("footer");
        footer.setText(string_footer);

        image1 = (ImageView) findViewById(R.id.imageView);
        image2 = (ImageView) findViewById(R.id.imageView3);


        byte[] byteArray = getIntent().getByteArrayExtra("Image");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        image2.setImageBitmap(bmp);

        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNewImage();
            }
        });
    }

    private void saveNewImage() {
        Bitmap bitmap = loadBitmapFromView(relativeLayout);

        String path = Environment.getExternalStorageDirectory() + "/Pooh/";

        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fullName = path + "test.png";
        File file = new File(fullName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            image1.setImageBitmap(bitmap);
        } catch (Exception e) {
            Log.e("ExpressionEditImage", "Error, " + e.getMessage());
        }
    }



    public Bitmap loadBitmapFromView(View view)
    {
        Bitmap b = Bitmap.createBitmap(view.getWidth(),view.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);
        view.layout(view.getLeft(),view.getTop(),view.getBottom(),view.getRight());
        view.draw(canvas);
        return b;
    }

}

