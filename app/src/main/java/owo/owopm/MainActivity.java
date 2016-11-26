package owo.owopm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.microsoft.projectoxford.vision.VisionServiceClient;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.contract.AnalysisResult;
import com.microsoft.projectoxford.vision.contract.Category;
import com.microsoft.projectoxford.vision.contract.Face;
import com.microsoft.projectoxford.vision.rest.VisionServiceException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private VisionServiceClient client;
    private String apiKey;

    public MainActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Admiral Snackbar....", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        apiKey = getResources().getString(R.string.api_key);

        if (client == null) {
            client = new VisionServiceRestClient(apiKey);
        }

        // grab from image we take picture of
        Bitmap myPackage = BitmapFactory.decodeResource(getResources(), R.drawable.owo);

        try {
            new TagsRequest(myPackage).execute();
        } catch (Exception e) {
            Log.w("apiCall", e.getMessage());
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
    }

    private class TagsRequest extends AsyncTask<String, String, String> {
            // Store error message
            private Exception e = null;
            private Bitmap myBitmap;
            private AnalysisResult result;
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
