package edu.sjsu.android.zoodirectory;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Information extends AppCompatActivity implements android.view.View.OnClickListener{
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);

        button = (Button) this.findViewById(R.id.button);
        button.setOnClickListener(this);
        getSupportActionBar().setTitle("Information Phone Number");



    }

    public void onClick(View arg0){
        Intent myIntent = new Intent(Intent.ACTION_DIAL);
        myIntent.setData(Uri.parse("tel:8888888"));
        startActivity(myIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.info:
                Intent information = new Intent(this, Information.class);

                startActivity(information);
                return true;

            case R.id.delete:
                Uri packageURI = Uri.parse("package:edu.sjsu.android.zoodirectory");
                Intent delete = new Intent(Intent.ACTION_DELETE, packageURI);
                startActivity(delete);

                default:
                    return super.onOptionsItemSelected(item);

        }

    }
}
