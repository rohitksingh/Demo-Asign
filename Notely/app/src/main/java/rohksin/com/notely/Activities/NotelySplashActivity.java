package rohksin.com.notely.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import rohksin.com.notely.R;
import rohksin.com.notely.Utilities.FileUtility;

/**
 * Created by Illuminati on 11/17/2017.
 */

public class NotelySplashActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notely_splash_activity);

        try {

            FileUtility fileUtility = new FileUtility(NotelySplashActivity.this);

            Thread.sleep(3000);
            startActivity(new Intent(NotelySplashActivity.this,NotelyListActivity.class));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }
}
