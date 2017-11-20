package rohksin.com.notely.Utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Illuminati on 11/18/2017.
 */

public class AppUtility {


    public static final String NOTE_ACTION = "rohksin.com.notely.Activities.NOTE_ACTION";
    public static final String NOTE_ITEM = "rohksin.com.notely.Activities.NOTE_ITEM";
    public static final int NEW_NOTE_REQ_CODE = 34;
    public static final int EDIT_NOTE_REQ_CODE = 36;

    public static final String EDIT_EXISTING_FILE = "rohksin.com.notely.Activities.EDIT_EXISTING_FILE";
    public static final String CREATE_NEW_FILE = "rohksin.com.notely.Activities.CREATE_NEW_FILE";

    private static final String LAST_VISITED_DATE_FORMAT = "h:m a (d MMM)";


    // Make it return date in different Format
    public static String getCurrentTime()
    {
        SimpleDateFormat sdf = new SimpleDateFormat(LAST_VISITED_DATE_FORMAT);
        String currentDate = sdf.format(new Date());
        return "Last updated at "+currentDate;

    }


}
