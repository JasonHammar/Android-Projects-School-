package edu.sjsu.android.zoodirectory;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AnimalFacts extends AppCompatActivity {
    public TextView txtHeader;
    public TextView description;
    public ImageView animal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_detail);
        Bundle animalDetail = this.getIntent().getExtras();
        txtHeader = (TextView)findViewById(R.id.textView);


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtHeader.setText(animalDetail.getString("name"));
        animal = (ImageView)findViewById(R.id.imageView2);
        description = (TextView)findViewById(R.id.textView2);

        if(animalDetail.getString("name").toString().contains("Lion")){
            description.setText(getString(R.string.lion_facts));
            animal.setImageResource(R.drawable.lion);
            getSupportActionBar().setTitle("Lion Facts");
        }else if(animalDetail.getString("name").toString().contains("Crocodile")){
            description.setText(getString(R.string.croc_facts));
            animal.setImageResource(R.drawable.crocodile);
            getSupportActionBar().setTitle("Crocodile Facts");
        }else if(animalDetail.getString("name").toString().contains("Koala")){
            description.setText(getString(R.string.koala_facts));
            animal.setImageResource(R.drawable.koala);
            getSupportActionBar().setTitle("Koala Facts");
        }else if(animalDetail.getString("name").toString().contains("Fruit Bat")){
            description.setText(getString(R.string.fruit_facts));
            animal.setImageResource(R.drawable.fruitbat);
            getSupportActionBar().setTitle("Fruit Bat Facts");
        }else if(animalDetail.getString("name").toString().contains("Silverback Gorilla")){
            description.setText(getString(R.string.silverback_facts));
            animal.setImageResource(R.drawable.silverback);
            getSupportActionBar().setTitle("Silverback Facts");
        }







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
