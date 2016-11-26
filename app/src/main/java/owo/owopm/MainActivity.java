package owo.owopm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private ImageView mPhotoCapturedImageView;
    private Button mButton;
    private Button mGetResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mPhotoCapturedImageView= (ImageView) findViewById(R.id.capturePhotoImageView);
        mButton = (Button) findViewById(R.id.photoButton);
        mGetResults = (Button) findViewById(R.id.viewResults);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void takePicture(View view) {
        /*Intent callCameraApplicationIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (callCameraApplicationIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(callCameraApplicationIntent, ACTIVITY_START_CAMERA_APP);
        }*/
        Toast.makeText(this, "Notices Bulge", Toast.LENGTH_SHORT).show();

        Intent callCameraApplicationIntent = new Intent();
        callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(callCameraApplicationIntent, ACTIVITY_START_CAMERA_APP);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK) {
            //Toast.makeText(this, "OwO What's this?", Toast.LENGTH_SHORT).show();
            Bundle extras = data.getExtras();
            Bitmap photoCapturedBitmap = (Bitmap) extras.get("data");
            mPhotoCapturedImageView.setImageBitmap(photoCapturedBitmap);

            Toast.makeText(this, "OwO What's this?", Toast.LENGTH_SHORT).show();

            //Want to load the new screen/activity with our loading screen

        }
        mButton.setVisibility(View.GONE);
        mGetResults.setVisibility(View.VISIBLE);








    }
    public void ViewResults(View view) {

        Intent intent = new Intent(this, ViewResults.class);

        startActivity(intent);

    }





}
