package com.example.headerandfooter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SampleForGit extends AppCompatActivity {

    TextView txt,txtSample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_for_git);


        txt = findViewById(R.id.textView1);
        txtSample = findViewById(R.id.textViewSample);

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt.setText("Hi ");
            }
        });


    }
}
