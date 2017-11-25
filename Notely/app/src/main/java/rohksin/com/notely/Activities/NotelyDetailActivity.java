package rohksin.com.notely.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import rohksin.com.notely.Adapters.NotelyListAdapter;
import rohksin.com.notely.Models.Note;
import rohksin.com.notely.R;
import rohksin.com.notely.Utilities.AppUtility;

/**
 * Created by Illuminati on 11/17/2017.
 */

public class NotelyDetailActivity extends AppCompatActivity {

    @BindView(R.id.title)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.mainGist)
    TextView gist;

    private Note note;
    private boolean isEdited;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notely_detail_actiivty);
        ButterKnife.bind(this);
        setUpUi();
    }


    //*********************************************************************
    // Menu related
    //*********************************************************************

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.detail_note_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case android.R.id.home:
                if(isEdited)
                {
                    setResult(Activity.RESULT_OK);
                }
                else
                {
                    setResult(Activity.RESULT_CANCELED);
                }
                onBackPressed();
                return true;

            case R.id.edit:
                Intent intent = new Intent(NotelyDetailActivity.this, AddNewNoteActivity.class);
                intent.setAction(AppUtility.NOTE_ACTION);
                intent.putExtra(AppUtility.NOTE_ITEM,note);
                startActivityForResult(intent,AppUtility.EDIT_NOTE_REQ_CODE);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }


    //*********************************************************************
    // System callback methods
    //*********************************************************************


    @Override
    public void onActivityResult(int reqCode,int resCode, Intent intent)
    {
        if(reqCode== AppUtility.EDIT_NOTE_REQ_CODE)
        {
            if(resCode == Activity.RESULT_OK)
            {
                note = (Note)intent.getSerializableExtra(AppUtility.NOTE_ITEM);
                isEdited = true;
                updateUI(note);
            }
            else if(resCode == Activity.RESULT_CANCELED)
            {
                Toast.makeText(NotelyDetailActivity.this,"Not edits",Toast.LENGTH_SHORT).show();
            }
        }
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


    //*********************************************************************
    // Private methods
    //*********************************************************************

    private void updateUI(Note note)
    {
        collapsingToolbarLayout.setTitle(note.getTitle());
        gist.setText(note.getGist());
    }

    private void setUpUi()
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK);
        collapsingToolbarLayout.setExpandedTitleColor(Color.BLACK);
        note = (Note)getIntent().getSerializableExtra(NotelyListAdapter.LIST_NOTE);
        updateUI(note);

    }

}
