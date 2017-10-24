package Model;

import java.util.ArrayList;

/**
 * Created by steven on 10/21/17.
 */

public class Convo {
    private String mIdHash;
    private String mTitle;
    //using String for Messages, but in the future, create a message object (for timestamp, userid, text, etc..)
    private ArrayList<String> mMessages;
    public static String mCurrentConvoHash;


    public Convo(String hashcode, String title){
        this.mIdHash = hashcode;
        this.mTitle = title;
    }
    public Convo(String hashcode, String title, ArrayList<String> msgs){
        this.mIdHash = hashcode;
        this.mTitle = title;
        this.mMessages = msgs;
    }

    public String getmIdHash(){
        return mIdHash;
    }
    public String getmTitle(){
        return mTitle;
    }
    public ArrayList<String> getmMessages(){
        return mMessages;
    }
}
