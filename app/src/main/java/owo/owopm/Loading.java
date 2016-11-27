package owo.owopm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;
import android.widget.Button;
import com.google.gson.Gson;
import com.microsoft.projectoxford.vision.VisionServiceClient;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.contract.AnalysisResult;
import com.microsoft.projectoxford.vision.rest.VisionServiceException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import android.util.Log;
import android.content.Intent;

public class Loading extends AppCompatActivity {
    private VisionServiceClient client;
    private VideoView mVideoView;
    private String apiKey;
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_view_results);
        Bitmap myPackage = (Bitmap) getIntent().getParcelableExtra("Image");
        mVideoView = (VideoView) findViewById(R.id.videoView);

        mVideoView.setVisibility(View.VISIBLE);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.elephanttrunk);
        mVideoView.setVideoURI(uri);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                mVideoView.start();


            }


        });
        apiKey = getResources().getString(R.string.api_key);

        //need to implement API here
        if (client == null) {
            client = new VisionServiceRestClient(apiKey);
        }

        // grab from image we take picture of


        TagsRequest tr = null;
        try {
            tr = new TagsRequest(myPackage);
            tr.execute();
        } catch (Exception e) {
            Log.w("apiCall", e.getMessage());
        }
        AnalysisResult result = null;
        if(tr != null) {
            result = tr.result;
        }



    }

    private String process(Bitmap myBitmap) throws VisionServiceException, IOException {
        Gson gson = new Gson();
        String[] features = {"Tags"};

        // Put the image into an input stream for detection.
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());

        AnalysisResult v = this.client.analyzeImage(inputStream, features, null);

        String result = gson.toJson(v);
        Log.d("result", result);

        return result;
    }

    private void finishCall(AnalysisResult result) {
        Log.w("finishCall", result.toString());
        Intent intent = new Intent(this, ViewResults.class);
        String message = "";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }

    private class TagsRequest extends AsyncTask<String, String, String> {
        // Store error message
        private Exception e = null;
        private Bitmap myBitmap;
        public AnalysisResult result;
        public TagsRequest(Bitmap myBitmap){
            this.myBitmap = myBitmap;
        }

        public AnalysisResult getResult() {
            return result;
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                return process(myBitmap);
            } catch (Exception e) {
                Log.w("doRequest", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);

            if (e != null) {
                this.e = null;
            } else {
                Gson gson = new Gson();
                finishCall(gson.fromJson(data, AnalysisResult.class));
            }
        }
    }





}
