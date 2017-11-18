package rohksin.com.notely.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rohksin.com.notely.Adapters.NotelyListAdapter;
import rohksin.com.notely.Dummy;
import rohksin.com.notely.Models.Note;
import rohksin.com.notely.R;
import rohksin.com.notely.Utilities.AppUtility;
import rohksin.com.notely.Utilities.FileUtility;

/**
 * Created by Illuminati on 11/17/2017.
 */

public class NotelyListActivity extends AppCompatActivity{

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private RelativeLayout filterDrawer;

    private RecyclerView notelyList;
    private LinearLayoutManager llm;
    private NotelyListAdapter adapter;



    private float lastTranslate = 0.0f;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notely_list_activity);

        Toolbar toolbar = (Toolbar)findViewById(R.id.notely_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.setStatusBarBackgroundColor(Color.TRANSPARENT);


        filterDrawer = (RelativeLayout)findViewById(R.id.filterDrawer);

        setUpFilters();


        // Set up recycler View
        notelyList = (RecyclerView)findViewById(R.id.notely_list);
        llm = new LinearLayoutManager(NotelyListActivity.this);
        notelyList.setLayoutManager(llm);

        setUpList();

        /*

        List<Note> notes =  FileUtility.getAllNotes();          //Dummy.getDummyNotes();

        if(notes==null)
        {
            ///// Write logic to show no item available
            Log.d("FILE","Empty");
            notes = Dummy.getDummyNotes();
        }

        Log.d("FILE","Not Empty"+notes.size());

        adapter = new NotelyListAdapter(NotelyListActivity.this,notes);
        notelyList.setAdapter(adapter);

        */

        //



        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);
                Toast.makeText(NotelyListActivity.this,"Drawer closed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerOpened(View view)
            {
                super.onDrawerOpened(view);
                Toast.makeText(NotelyListActivity.this,"Drawer opened",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerSlide(View view, float slideOffset)
            {
                super.onDrawerSlide(view,slideOffset);
                float slideFactor =  filterDrawer.getWidth()*slideOffset;

                TranslateAnimation anim = new TranslateAnimation( -lastTranslate,-slideFactor, 0.0f, 0.0f);
                anim.setDuration(0);
                anim.setFillAfter(true);
                notelyList.startAnimation(anim);
                lastTranslate = slideFactor;

            }

        };

        drawerLayout.addDrawerListener(drawerToggle);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.notely_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();

        if(id == R.id.filters)
        {
            Toast.makeText(NotelyListActivity.this,"This is filter",Toast.LENGTH_SHORT).show();
            drawerLayout.openDrawer(Gravity.END);
            return true;
        }
        else if(id == R.id.addNote)
        {
           // startActivity(new Intent(NotelyListActivity.this, AddNewNoteActivity.class));

            Intent intent = new Intent(NotelyListActivity.this, AddNewNoteActivity.class);
            intent.setAction(AppUtility.CREATE_NEW_FILE);
            startActivityForResult(intent, AppUtility.NEW_NOTE_REQ_CODE);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int reqCode, int resCode, Intent intent)
    {
        if(reqCode == AppUtility.NEW_NOTE_REQ_CODE)
        {
            if(resCode== Activity.RESULT_OK)
            {
                Toast.makeText(NotelyListActivity.this,"refresh",Toast.LENGTH_SHORT).show();
                setUpList();
            }
            else if(resCode == Activity.RESULT_CANCELED)
            {
                Toast.makeText(NotelyListActivity.this,"Not refresh",Toast.LENGTH_SHORT).show();
            }
        }
        else if(reqCode == AppUtility.EDIT_NOTE_REQ_CODE)
        {
            if(resCode== Activity.RESULT_OK)
            {
                Toast.makeText(NotelyListActivity.this,"refresh",Toast.LENGTH_SHORT).show();
                setUpList();
            }
            else if(resCode == Activity.RESULT_CANCELED)
            {
                Toast.makeText(NotelyListActivity.this,"Not refresh",Toast.LENGTH_SHORT).show();
            }
        }

    }



    public void setUpFilters()
    {

        setUpFilter(R.id.favorite, "Favorite");
        setUpFilter(R.id.all_filters,"FILTERS");
        setUpFilter(R.id.poem,"Poem");
        setUpFilter(R.id.hearted,"Hearted");
        setUpFilter(R.id.story,"Story");

        Button applyFilterButton = (Button)findViewById(R.id.applyFilter);

        applyFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(Gravity.END);
            }
        });


    }

    public void setUpFilter(int id, String name)
    {
        Log.d("ID inside",R.id.favorite+"");
        RelativeLayout allfilters = (RelativeLayout) findViewById(id);
        final TextView textView = (TextView)allfilters.findViewById(R.id.filterText);
        textView.setText(name);

        allfilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setTextColor(Color.BLUE);
            }
        });
    }


    public void setUpList()
    {
        List<Note> notes =  FileUtility.getAllNotes();          //Dummy.getDummyNotes();

        if(notes==null)
        {
            ///// Write logic to show no item available
            Log.d("FILE","Empty");
            notes = Dummy.getDummyNotes();
        }

        Log.d("FILE","Not Empty"+notes.size());

        adapter = new NotelyListAdapter(NotelyListActivity.this,notes);
        notelyList.setAdapter(adapter);
    }

}
