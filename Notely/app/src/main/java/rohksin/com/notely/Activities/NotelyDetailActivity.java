package rohksin.com.notely.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import rohksin.com.notely.Adapters.NotelyListAdapter;
import rohksin.com.notely.Models.Note;
import rohksin.com.notely.R;
import rohksin.com.notely.Utilities.AppUtility;

/**
 * Created by Illuminati on 11/17/2017.
 */

public class NotelyDetailActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private TextView gist;
    private Note note;

    private boolean isEdited;

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
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK);
        collapsingToolbarLayout.setExpandedTitleColor(Color.BLACK);
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
            if(isEdited)
            {
                setResult(Activity.RESULT_OK);
            }
            else
            {
                setResult(Activity.RESULT_CANCELED);
            }
            onBackPressed();

        }
        else if(id == R.id.edit)
        {
            Intent intent = new Intent(NotelyDetailActivity.this, AddNewNoteActivity.class);
            intent.setAction(AppUtility.NOTE_ACTION);
            intent.putExtra(AppUtility.NOTE_ITEM,note);
            //startActivity(intent);
            startActivityForResult(intent,AppUtility.EDIT_NOTE_REQ_CODE);
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int reqCode,int resCode, Intent intent)
    {
        if(reqCode== AppUtility.EDIT_NOTE_REQ_CODE)
        {
            if(resCode == Activity.RESULT_OK)
            {
                Toast.makeText(NotelyDetailActivity.this,"Edites",Toast.LENGTH_SHORT).show();

                Note note = (Note)intent.getSerializableExtra(AppUtility.NOTE_ITEM);

                Log.d("NOTE IS NULL",(note==null)+"");

                isEdited = true;

                // Get Node to update UI
                updateUI(note);
            }
            else if(resCode == Activity.RESULT_CANCELED)
            {
                Toast.makeText(NotelyDetailActivity.this,"Not edites",Toast.LENGTH_SHORT).show();
            }
        }
    }



    public void updateUI(Note note)
    {
        collapsingToolbarLayout.setTitle(note.getTitle());
        gist.setText(note.getGist());
    }




    @Override
    public void onBackPressed()
    {
        if(isEdited)
        {
            setResult(Activity.RESULT_OK);
        }
        else
        {
            setResult(Activity.RESULT_CANCELED);
        }
        finish();

        super.onBackPressed();
    }





}
