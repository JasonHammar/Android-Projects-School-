package edu.sjsu.android.homework5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    public ImageView urlImage;
    public Bitmap result;
    ProgressDialog mProgressDialog;

    static class ThreadHandler extends Handler {
        private ImageView newImage;

        public ThreadHandler (ImageView img) {
            newImage = img;
        }
        public void handleMessage(Message msg) {
            if((Bitmap)msg.obj!=null) {
                newImage.setImageBitmap((Bitmap)msg.obj);
            }
        }
    }

    private ThreadHandler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button reset = (Button) findViewById(R.id.resetImage);
        Button message = (Button) findViewById(R.id.messages);
        Button async = (Button) findViewById(R.id.async);
        urlImage = (ImageView) findViewById(R.id.imageView);
        EditText URL = (EditText) findViewById(R.id.imageurl);
        handler = new ThreadHandler(urlImage);
        Button runnable = (Button) findViewById(R.id.runnable);

        runnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RunnableDownload runnable = new RunnableDownload();
                runnable.execute(URL.getText().toString());

            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessagesDownload messages = new MessagesDownload();
                messages.execute(URL.getText().toString());

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetImage(urlImage);
            }
        });

        async.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runAsyncTask(URL.getText().toString());

            }
        });




    }

    public void displayWarning(){
        runOnUiThread(new Runnable() {
            public void run()
            {
                Toast.makeText(MainActivity.this,"Error, Could Not Download, Please enter correct URL",Toast.LENGTH_LONG).show();

            }
        });
    }

    Bitmap downloadBitmap (String src) {


        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            return myBitmap;
        } catch (IOException e) {

            displayWarning();

            e.printStackTrace();


            return null;
        }
        //return null;
    }
    public void runRunnable(String view) {
        // ... you fill in here

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                //Test URL to make sure it works "https://www.collinsdictionary.com/images/full/apple_158989157.jpg";
                Bitmap newImage = downloadBitmap(view);


                handler.post(new Runnable()
                {
                    public void run()
                    {
                        if(newImage != null)
                        {
                            urlImage.setImageBitmap(newImage);
                        }
                    }
                });
            }
        }).start();

    }
    public void runMessages(String view) {

        new Thread(new Runnable()
        {
            public void run()
            {

                //Test URL to make sure it works: "https://www.collinsdictionary.com/images/full/apple_158989157.jpg";
                Bitmap newImage = downloadBitmap(view);
                Message msg = handler.obtainMessage(0, newImage);
                handler.sendMessage(msg);
            }
        }).start();



    }
    public void runAsyncTask(String view) {

        AsyncDownload asyncDownload = new AsyncDownload();
        asyncDownload.execute(view);
    }
    public void resetImage(ImageView view) {

        view.setImageResource(R.drawable.apple_logo);
    }

    private class AsyncDownload extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Downloading");
            mProgressDialog.setMessage("Downloading via AsyncTask");
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setProgress(0);
            mProgressDialog.incrementProgressBy(1);
            mProgressDialog.show();

        }

        @Override
        protected Bitmap doInBackground(String... params) {

            return downloadBitmap(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            result = bitmap;

            urlImage.setImageBitmap(bitmap);
            mProgressDialog.dismiss();
        }
    }

    private class RunnableDownload extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Downloading");
            mProgressDialog.setMessage("Downloading via Runnable");
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setProgress(0);
            mProgressDialog.incrementProgressBy(1);
            mProgressDialog.show();

        }

        @Override
        protected Bitmap doInBackground(String... params) {
            //downloadBitmap(params[0]);

            runRunnable(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            mProgressDialog.dismiss();
        }
    }

    private class MessagesDownload extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Downloading");
            mProgressDialog.setMessage("Downloading via Messages");
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setProgress(0);
            mProgressDialog.incrementProgressBy(1);
            mProgressDialog.show();

        }

        @Override
        protected Bitmap doInBackground(String... params) {


            runMessages(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            mProgressDialog.dismiss();
        }
    }


}