package rohksin.com.notely.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import rohksin.com.notely.Models.Note;
import rohksin.com.notely.R;

/**
 * Created by Illuminati on 11/17/2017.
 */

public class AddNewNoteActivity extends AppCompatActivity {

    private EditText title;
    private EditText gist;
    private Note note;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_note_activity);

        title = (EditText)findViewById(R.id.title);
        gist = (EditText)findViewById(R.id.gist);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();

        if(intent.getAction()!=null) {
            if (intent.getAction().equals(AppUtility.NOTE_ACTION)) {
                note = (Note) intent.getSerializableExtra(AppUtility.NOTE_ITEM);
                title.setText(note.getTitle());
                gist.setText(note.getGist());
            }
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
        }
        else if(id == R.id.undo)
        {
            Toast.makeText(AddNewNoteActivity.this,"Undo",Toast.LENGTH_SHORT).show();
        }



        return super.onOptionsItemSelected(item);
    }


}
