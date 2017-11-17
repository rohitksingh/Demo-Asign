package rohksin.com.notely.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rohksin.com.notely.Adapters.NotelyListAdapter;
import rohksin.com.notely.Models.Note;
import rohksin.com.notely.R;

/**
 * Created by Illuminati on 11/17/2017.
 */

public class NotelyDetailActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notely_detail_actiivty);

        Note note = (Note)getIntent().getSerializableExtra(NotelyListAdapter.LIST_NOTE);

        Log.d("NOTE", note.toString());
    }
}
