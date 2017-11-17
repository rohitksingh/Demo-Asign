package rohksin.com.notely;

import java.util.ArrayList;
import java.util.List;

import rohksin.com.notely.Models.Note;

/**
 * Created by Illuminati on 11/17/2017.
 */

public class Dummy {


    private static List<Note> notes;


    public static List<Note> getDummyNotes()
    {
        notes = new ArrayList<>();

        for(int i=0;i<20;i++)
        {
            Note note = new Note();
            note.setTitle("Note"+i);
            note.setGist("Gist"+i);
            notes.add(note);
        }

        return notes;
    }


}
