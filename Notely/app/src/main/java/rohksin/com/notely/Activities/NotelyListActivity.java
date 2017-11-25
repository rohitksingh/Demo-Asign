package rohksin.com.notely.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import rohksin.com.notely.Adapters.NotelyListAdapter;
import rohksin.com.notely.Dummy;
import rohksin.com.notely.Models.Note;
import rohksin.com.notely.R;
import rohksin.com.notely.Utilities.AppUtility;
import rohksin.com.notely.Utilities.FileUtility;
import rohksin.com.notely.Utilities.FilterUtility;

/**
 * Created by Illuminati on 11/17/2017.
 */

public class NotelyListActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{

    @BindView(R.id.notely_toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.filterDrawer)
    RelativeLayout filterDrawer;

    @BindView(R.id.notely_list)
    RecyclerView notelyList;

    private ActionBarDrawerToggle drawerToggle;
    private LinearLayoutManager llm;
    private NotelyListAdapter adapter;


    private boolean filtersApplied ;          // Decides to call whether a filter method or normal execution
    private int filterEnabled =0;
    private boolean doubleTapped = false;
    private float lastTranslate = 0.0f;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notely_list_activity);
        ButterKnife.bind(this);
        setUpUi();
    }


    //*********************************************************************
    // Menu Related
    //*********************************************************************

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.notely_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.filters:
                Toast.makeText(NotelyListActivity.this,"This is filter",Toast.LENGTH_SHORT).show();
                drawerLayout.openDrawer(Gravity.END);
                return true;

            case R.id.addNote:
                Intent intent = new Intent(NotelyListActivity.this, AddNewNoteActivity.class);
                intent.setAction(AppUtility.CREATE_NEW_FILE);
                startActivityForResult(intent, AppUtility.NEW_NOTE_REQ_CODE);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    //*********************************************************************
    // Activity Callback methods (Overriden)
    //*********************************************************************

    @Override
    public void onActivityResult(int reqCode, int resCode, Intent intent)
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


    // Double Tap to Exit
    @Override
    public void onBackPressed()
    {

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                doubleTapped = false;

            }
        }, 2000);

        if(doubleTapped)
        {
            super.onBackPressed();
        }
        else {
            Toast.makeText(NotelyListActivity.this,"Press again to exit",Toast.LENGTH_SHORT).show();
            doubleTapped = true;
        }
    }


    //*********************************************************************
    // Private Methods
    //*********************************************************************


    private void setUpUi()
    {
        setSupportActionBar(toolbar);
        llm = new LinearLayoutManager(NotelyListActivity.this);
        notelyList.setLayoutManager(llm);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(notelyList);
        setUpList();
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        setUpFilters();
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


    private void setUpFilters()
    {

        setUPCancelFilter();

        setUpFilter(R.id.favorite, "Favorite");
        setUpFilter(R.id.poem,"Poem");
        setUpFilter(R.id.hearted,"Hearted");
        setUpFilter(R.id.story,"Story");

        TextView applyFilterButton = (TextView) findViewById(R.id.applyFilter);
        applyFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Note filterNote = FilterUtility.getAllEnabledFilter();

                List<Note> filteredList = null;

                if(filterEnabled!=0)
                {
                    filteredList = FilterUtility.getFilteredList(FileUtility.getAllNotes(),filterNote);
                }
                else
                {
                    filteredList = FileUtility.getAllNotes();
                }

                drawerLayout.closeDrawer(Gravity.END);
                adapter = new NotelyListAdapter(NotelyListActivity.this,filteredList);
                notelyList.setAdapter(adapter);

            }
        });


    }


    private void setUPCancelFilter()
    {
        RelativeLayout allfilters = (RelativeLayout) findViewById(R.id.all_filters);
        final TextView textView = (TextView)allfilters.findViewById(R.id.filterText);
        textView.setText("CLEAR FILTER");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(20);
        ImageView imageView = (ImageView)allfilters.findViewById(R.id.filterIcon);
        imageView.setImageResource(R.drawable.clear);
        imageView.setVisibility(View.GONE);

        allfilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterUtility.initialze();
                disabledFilter(R.id.poem);
                disabledFilter(R.id.story);
                disabledFilter(R.id.hearted);
                disabledFilter(R.id.favorite);
                filtersApplied = false;
                filterEnabled =0;                // resetting
            }
        });


    }

    private void setUpFilter(final int id, String name)
    {
        Log.d("ID inside",id+"");
        final RelativeLayout allfilters = (RelativeLayout) findViewById(id);
        final TextView textView = (TextView)allfilters.findViewById(R.id.filterText);
        textView.setText(name);

        allfilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!FilterUtility.isEnabled(id)) {
                    //textView.setTextColor(Color.BLUE);
                    allfilters.setBackgroundColor(getResources().getColor(R.color.filterEnable));
                    FilterUtility.toggele(id);
                    filtersApplied = true;
                    filterEnabled++;
                }
                else
                {
                    //textView.setTextColor(Color.RED);
                    allfilters.setBackgroundColor(Color.TRANSPARENT);
                    FilterUtility.toggele(id);
                    filtersApplied = false;
                    filterEnabled--;
                }
            }
        });
    }


    private void disabledFilter(int id)
    {
        RelativeLayout allfilters = (RelativeLayout) findViewById(id);
        final TextView textView = (TextView)allfilters.findViewById(R.id.filterText);
        //textView.setTextColor(Color.WHITE);
        allfilters.setBackgroundColor(Color.TRANSPARENT);
        // This is disbaled color Change to in Color Source
    }


    private void setUpList()
    {
        List<Note> notes =  FileUtility.getAllNotes();          //Dummy.getDummyNotes();

        if(notes==null)
        {
            ///// Write logic to show no item available
            Log.d("FILE","Empty");
            notes = Dummy.getDummyNotes();
        }

        adapter = new NotelyListAdapter(NotelyListActivity.this,notes);
        notelyList.setAdapter(adapter);

    }



    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        adapter.removeItem(position);
    }
}
