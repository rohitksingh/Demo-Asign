package rohksin.com.notely.Models;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Illuminati on 11/17/2017.
 */

public class Note implements Serializable{

    private String title;
    private String gist;
    private UUID uuid;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGist() {
        return gist;
    }

    public void setGist(String gist) {
        this.gist = gist;
    }

    @Override
    public String toString()
    {
        return title+" \n"+ gist;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

}
