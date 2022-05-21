package edu.sjsu.android.intentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class ActivityLoaderActivity extends AppCompatActivity {


    public void browser1(View view){
        openUrl("http://www.amazon.com");
    }

    public void openUrl(String url){
        Uri webpage = Uri.parse(url);
        Intent launchWeb = new Intent(Intent.ACTION_VIEW, webpage);


        Intent chooser = Intent.createChooser(launchWeb, "Choose browser");

        try {// Allows user to choose to use default browser or myBrowser
            startActivity(chooser);
        } catch(ActivityNotFoundException e) {
            Log.d("error", "activity not found error");
        }
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonCall = (Button) findViewById(R.id.button2);

        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {// Brings up dial function
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:194912344444"));
                startActivity(intent);
            }
        });

    }
}
