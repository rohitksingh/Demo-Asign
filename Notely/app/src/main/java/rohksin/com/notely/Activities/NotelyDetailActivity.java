package rohksin.com.notely.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import rohksin.com.notely.Adapters.NotelyListAdapter;
import rohksin.com.notely.Models.Note;
import rohksin.com.notely.R;

/**
 * Created by Illuminati on 11/17/2017.
 */

public class NotelyDetailActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private TextView gist;

    private Note note;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notely_detail_actiivty);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        gist = (TextView)findViewById(R.id.mainGist);

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.title);
        note = (Note)getIntent().getSerializableExtra(NotelyListAdapter.LIST_NOTE);
        collapsingToolbarLayout.setTitle(note.getTitle());
        gist.setText(note.getGist()+",mzx,mz,mx,zm,xm,zm");
        Log.d("NOTE", note.toString());
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.detail_note_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();


        if(id == android.R.id.home)
        {
            onBackPressed();
        }
        else if(id == R.id.edit)
        {
            Intent intent = new Intent(NotelyDetailActivity.this, AddNewNoteActivity.class);
            intent.setAction(AppUtility.NOTE_ACTION);
            intent.putExtra(AppUtility.NOTE_ITEM,note);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }






}
