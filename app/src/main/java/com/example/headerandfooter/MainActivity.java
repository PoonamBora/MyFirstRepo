package com.example.headerandfooter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;


import static android.content.Intent.ACTION_PICK;

public class MainActivity extends AppCompatActivity
{
    Button nextBtn,logoBtn;
    EditText editHeader,editFooter;
    private static int RESULT_LOAD_IMAGE = 1;
    Bitmap bitmap;
    ImageView imageLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editHeader = (EditText) findViewById(R.id.edit_header) ;
        editFooter = (EditText) findViewById(R.id.edit_footer) ;

        imageLogo = (ImageView) findViewById(R.id.imageView2) ;

        nextBtn = (Button) findViewById(R.id.btnNext);
        nextBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String str_header =  editHeader.getText().toString();

                //For Footer
                String str_footer =  editFooter.getText().toString();

                Intent intent = new Intent(getApplicationContext(),Main2Activity.class);

                intent.putExtra("header",str_header);
                intent.putExtra("footer",str_footer);

                //For Image

                BitmapDrawable drawable = (BitmapDrawable) imageLogo.getDrawable();
                bitmap = drawable.getBitmap();

                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                byte[] byteArray=stream.toByteArray();
                intent.putExtra("Image", byteArray);

                startActivity(intent);
            }
        });

        logoBtn = (Button) findViewById(R.id.btnLogo);
        logoBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                if (MainActivity.this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(MainActivity.this, "You Have" +
                            "Already Granted This Permission" , Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent,RESULT_LOAD_IMAGE);

                }else
                {
                    requestStoragePermission();
                }



            }
        });
    }

    private void requestStoragePermission()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            new AlertDialog.Builder(this).setTitle("Permission Needed")
                    .setMessage("This permission is needed")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    RESULT_LOAD_IMAGE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    RESULT_LOAD_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // to check whether the given request code is equal to the load image and
        // result code is equal to the result is ok or not  and
        // null value is not equal to data
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data)
        {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn,
                    null,null,null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = findViewById(R.id.imageView2);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        if (requestCode == RESULT_LOAD_IMAGE)
        {
            if (grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "PERMISSION GRANTED",
                        Toast.LENGTH_SHORT).show();
            } else
            {
                Toast.makeText(this, "PERMISSION DENIED",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}
