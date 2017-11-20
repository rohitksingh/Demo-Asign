package rohksin.com.notely.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import rohksin.com.notely.Models.Note;
import rohksin.com.notely.R;
import rohksin.com.notely.Utilities.AppUtility;
import rohksin.com.notely.Utilities.FileUtility;

/**
 * Created by Illuminati on 11/17/2017.
 */

public class AddNewNoteActivity extends AppCompatActivity {

    private EditText title;
    private EditText gist;
    private Note note;

    private String WRITE_MODE;


    private Toolbar toolbar;



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_note_activity);

        toolbar = (Toolbar)findViewById(R.id.notely_toolbar);

        setSupportActionBar(toolbar);

        title = (EditText)findViewById(R.id.title);
        gist = (EditText)findViewById(R.id.gist);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Intent intent = getIntent();


        if (intent.getAction().equals(AppUtility.NOTE_ACTION)) {

            Log.d("MODE","EDIT MODE");

            note = (Note) intent.getSerializableExtra(AppUtility.NOTE_ITEM);
            title.setText(note.getTitle());
            gist.setText(note.getGist());
            title.setSelection(title.getText().length());
            gist.setSelection(gist.getText().length());
            Log.d("GIZ","XXcc: "+note);
            WRITE_MODE = AppUtility.EDIT_EXISTING_FILE;
        }
        else if(intent.getAction().equals(AppUtility.CREATE_NEW_FILE)){
            WRITE_MODE = AppUtility.CREATE_NEW_FILE;
            note = new Note();                                //<----- This fixed the bug
            Log.d("MODE","New File MODE");

        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.add_note_menu,menu);
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
        else if(id == R.id.save)
        {
            Toast.makeText(AddNewNoteActivity.this,"Save",Toast.LENGTH_SHORT).show();

            //note = new Note(); /////<------- This is the culprit FOUND IT

            note.setTitle(title.getText()+"");
            note.setGist(gist.getText()+"");

            Log.d("MODE",WRITE_MODE);

            //FileUtility.createFile(title.getText()+"",note);
            /// Create a new file or edit based ont the mode
            FileUtility.createFile(note,WRITE_MODE);

            Intent resultIntent = new Intent();
            resultIntent.putExtra(AppUtility.NOTE_ITEM,note);

            setResult(Activity.RESULT_OK,resultIntent);
            finish();

        }
        else if(id == R.id.undo)
        {
            Toast.makeText(AddNewNoteActivity.this,"Undo",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }


}
