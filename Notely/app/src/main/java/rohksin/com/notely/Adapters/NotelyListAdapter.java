package rohksin.com.notely.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rohksin.com.notely.Models.Note;
import rohksin.com.notely.R;

/**
 * Created by Illuminati on 11/17/2017.
 */

public class NotelyListAdapter extends RecyclerView.Adapter<NotelyListAdapter.NotelyViewHolder> {

    private Context context;
    private List<Note> notes;


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
    public void onBindViewHolder(NotelyViewHolder holder, int position) {

        Note note = notes.get(position);
        holder.title.setText(note.getTitle());
        holder.gist.setText(note.getGist());

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class NotelyViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView gist;

        public NotelyViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            gist = (TextView)itemView.findViewById(R.id.gist);
        }
    }

}
