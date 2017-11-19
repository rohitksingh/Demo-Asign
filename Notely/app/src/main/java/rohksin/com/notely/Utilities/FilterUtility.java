package rohksin.com.notely.Utilities;

import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import rohksin.com.notely.Models.Note;
import rohksin.com.notely.R;

/**
 * Created by Illuminati on 11/18/2017.
 */

public class FilterUtility {


    private static LinkedHashMap<Integer,Boolean> hashMap;

    public static void initialze()
    {
        hashMap = new LinkedHashMap<Integer, Boolean>();
        hashMap.put(R.id.all_filters,false);
        hashMap.put(R.id.poem,false);
        hashMap.put(R.id.story,false);
        hashMap.put(R.id.favorite,false);
        hashMap.put(R.id.hearted,false);
    }

    public static boolean isEnabled(int id)
    {
        return hashMap.get(id);
    }

    public static void toggele(int id)
    {
        Log.d("ID toggle",id+"");
        if(hashMap.get(id))
        {
            hashMap.put(id,false);
        }
        else {
            hashMap.put(id,true);
        }
    }


    public static Note getAllEnabledFilter()
    {
        Note filterNote = new Note();

        Log.d("WHAt",(filterNote==null)+""+hashMap.size());

        Set<Integer> keySet= hashMap.keySet();
        Iterator<Integer> iterator = keySet.iterator();
        filterNote.setStarred((boolean)hashMap.get(R.id.favorite));
        filterNote.setHearted((boolean)hashMap.get(R.id.hearted));
        filterNote.setPoem((boolean)hashMap.get(R.id.poem));
        filterNote.setStory((boolean)hashMap.get(R.id.story));

        return filterNote;

    }


    public static List<Note> getFilteredList(List<Note> notes, Note filter)
    {
        List<Note> filterdList = new ArrayList<Note>();


        Log.d("FILTERS",filter+"");

        for(Note note: notes)
        {
            if(filter.isStarred()==note.isStarred()&& filter.isHearted()==note.isHearted())
            {
                filterdList.add(note);
                Log.d("ISFILTER","IF");
            }
            else
            {
                Log.d("ISFILTER","ELSE");
            }
        }



        return filterdList;

    }







}
