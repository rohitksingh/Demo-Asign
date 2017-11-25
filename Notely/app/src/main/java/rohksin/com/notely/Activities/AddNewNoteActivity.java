package rohksin.com.notely.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import rohksin.com.notely.Models.Note;
import rohksin.com.notely.R;
import rohksin.com.notely.Utilities.AppUtility;
import rohksin.com.notely.Utilities.FileUtility;

/**
 * Created by Illuminati on 11/17/2017.
 */

public class AddNewNoteActivity extends AppCompatActivity {

    @BindView(R.id.title)
    EditText title;

    @BindView(R.id.gist)
    EditText gist;

    @BindView(R.id.notely_toolbar)
    Toolbar toolbar;

    private Note note;
    private String WRITE_MODE;
    private Note oldNote;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_note_activity);
        ButterKnife.bind(this);
        setUpUI();

    }

    //*********************************************************************
    // Menu Related
    //*********************************************************************


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.add_note_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.save:
                note.setTitle(title.getText()+"");
                note.setGist(gist.getText()+"");
                FileUtility.createFile(note,WRITE_MODE);
                Intent resultIntent = new Intent();
                resultIntent.putExtra(AppUtility.NOTE_ITEM,note);
                setResult(Activity.RESULT_OK,resultIntent);
                finish();
                return true;

            case R.id.undo:
                Toast.makeText(AddNewNoteActivity.this,"Undo",Toast.LENGTH_SHORT).show();
                setUpUI();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }



    //*********************************************************************
    // Private methods
    //*********************************************************************

    public void setUpUI()
    {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        if (intent.getAction().equals(AppUtility.NOTE_ACTION)) {

            note = (Note) intent.getSerializableExtra(AppUtility.NOTE_ITEM);
            title.setText(note.getTitle());
            gist.setText(note.getGist());
            title.setSelection(title.getText().length());
            gist.setSelection(gist.getText().length());
            WRITE_MODE = AppUtility.EDIT_EXISTING_FILE;
        }
        else if(intent.getAction().equals(AppUtility.CREATE_NEW_FILE)){
            WRITE_MODE = AppUtility.CREATE_NEW_FILE;
            note = new Note();//<----- This fixed the bug
        }
        oldNote = note;
    }

}
