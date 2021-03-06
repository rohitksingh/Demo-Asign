package rohksin.com.notely.Utilities;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import rohksin.com.notely.Models.Note;

/**
 * Created by Illuminati on 11/18/2017.
 */

public class FileUtility {

    private static Context context;

    public FileUtility(Context context)
    {
        this.context = context;
    }

    public static File giveMainFolder()
    {
        return context.getFilesDir();
    }

    public static File[] getAllFiles()
    {
        return context.getFilesDir().listFiles();
    }

    public static String[] getAllFileNames()
    {
        return context.getFilesDir().list();
    }

    public static void  createFile(Note note, String writeMode)
    {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        UUID uuid = null;

        if(writeMode.equals(AppUtility.CREATE_NEW_FILE)) {

            uuid = UUID.randomUUID();
            note.setUuid(uuid);

        }
        else if(writeMode.equals(AppUtility.EDIT_EXISTING_FILE))
        {
            uuid = note.getUuid();
        }

        note.setLastUpdatedTime(AppUtility.getCurrentTime());

        String fileName = "file"+uuid;
        File newFile = new File(giveMainFolder(), fileName);
        note.setFilePath(newFile.getPath());

        try {
            fos = new FileOutputStream(newFile);
        }
        catch (FileNotFoundException e)
        {

        }
        try {
            oos = new ObjectOutputStream(fos);

        } catch (IOException e) {

            e.printStackTrace();
        }
        try {
            oos.writeObject(note );
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    public static List<Note> getAllNotes()
    {
        List<Note> notes = new ArrayList<Note>();
        File[] allfiles = getAllFiles();

        for( File file: allfiles)
        {
            Note note = getNote(file);
            notes.add(note);
        }

        return notes;
    }

    public static Note getNote(File file)
    {
        Note note = null;

        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ois = new ObjectInputStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            note = (Note) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return note;

    }


    public static void deleteNote(String  filePath)
    {
        File file = new File(filePath);
        file.delete();

    }


}
