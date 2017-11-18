package rohksin.com.notely.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import rohksin.com.notely.Activities.NotelyDetailActivity;
import rohksin.com.notely.Models.Note;
import rohksin.com.notely.R;
import rohksin.com.notely.Utilities.AppUtility;

/**
 * Created by Illuminati on 11/17/2017.
 */

public class NotelyListAdapter extends RecyclerView.Adapter<NotelyListAdapter.NotelyViewHolder> {

    private Context context;
    private List<Note> notes;

    public static final String LIST_NOTE = "rohksin.com.notely.Adapters.LIST_NOTE";


    public NotelyListAdapter(Context context, List<Note> notes)
    {
        this.context = context;
        this.notes = notes;
    }

    @Override
    public NotelyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.notely_list_item,parent,false);
        return new NotelyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NotelyViewHolder holder, int position) {

        final Note note = notes.get(position);
        holder.title.setText(note.getTitle());
        holder.gist.setText(note.getGist());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, NotelyDetailActivity.class);
                intent.putExtra(LIST_NOTE,note);
                //context.startActivity(intent);
                AppCompatActivity appCompatActivity = (AppCompatActivity)context;
                appCompatActivity.startActivityForResult(intent, AppUtility.EDIT_NOTE_REQ_CODE);
            }
        });

        holder.hearted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.hearted.setImageResource(R.drawable.heart_pressed);
            }
        });

        holder.starred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.starred.setImageResource(R.drawable.star_pressed);
            }
        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class NotelyViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView gist;
        private ImageView starred;
        private ImageView hearted;

        public NotelyViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            gist = (TextView)itemView.findViewById(R.id.gist);
            starred = (ImageView)itemView.findViewById(R.id.stared);
            hearted = (ImageView)itemView.findViewById(R.id.hearted);

        }
    }

}
