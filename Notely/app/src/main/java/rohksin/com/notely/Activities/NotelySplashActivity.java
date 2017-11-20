package rohksin.com.notely.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.widget.TextView;
import android.widget.Toast;

import rohksin.com.notely.R;
import rohksin.com.notely.Utilities.FileUtility;
import rohksin.com.notely.Utilities.FilterUtility;

/**
 * Created by Illuminati on 11/17/2017.
 */

public class NotelySplashActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notely_splash_activity);

        textView = (TextView)findViewById(R.id.mainText);
        textView.setText("Notely");

        FileUtility fileUtility = new FileUtility(NotelySplashActivity.this);
        FilterUtility.initialze();

        // Splash time
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                startActivity(new Intent(NotelySplashActivity.this,NotelyListActivity.class));
                finish();

            }
        }, 2000);



    }
}
