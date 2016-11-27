package owo.owopm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class ViewResults extends AppCompatActivity {
    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_results2);
        mTextView = (TextView) findViewById(R.id.Results);
        Intent intent = getIntent();
        String message = intent.getStringExtra(Loading.EXTRA_MESSAGE);

        mTextView.setTextSize(40);
        mTextView.setText(message);
    }

    //we want a way to view results-> need to push through from other thing in this case

    //On button click
    public void GoBack(View view) {
        //want to give option to go back

        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);



    }


}
