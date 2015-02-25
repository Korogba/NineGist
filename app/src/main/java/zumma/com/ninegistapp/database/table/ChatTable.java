package zumma.com.ninegistapp.database.table;

import android.net.Uri;
import android.provider.BaseColumns;
import java.util.HashMap;

import zumma.com.ninegistapp.database.DataBaseProvider;

/**
 * Created by Okafor on 08/12/2014.
 */

public class ChatTable {

    public static final String TABLE_NAME = "chat_tb";
    public static final Uri CONTENT_URI = Uri.parse(DataBaseProvider.getContentURIString() + "/" + TABLE_NAME);
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + DataBaseProvider.getVndPath() + "." + TABLE_NAME;
    public static HashMap<String, String> sProjectionMap;


    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_TO = "toUser";
    public static final String COLUMN_FROM = "fromUser";
    public static final String COLUMN_UNIQ_ID = "uniq_id";
    public static final String COLUMN_REPORT = "report";
    public static final String COLUMN_SENT = "sent";
    public static final String COLUMN_PRIVATE = "private";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_CREATED_AT = "createdAt";

    // Database creation sql statement
    public static final String CREATE_TABLE = "create table " + TABLE_NAME
                + "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_TO + " TEXT,"
            + COLUMN_FROM + " TEXT,"
            + COLUMN_UNIQ_ID + " TEXT,"
            + COLUMN_REPORT + " INTEGER,"
            + COLUMN_SENT + " INTEGER,"
            + COLUMN_PRIVATE + " INTEGER,"
            + COLUMN_MESSAGE + " TEXT,"
            + COLUMN_TIME + " TEXT,"
            + COLUMN_CREATED_AT + " TEXT"
            + " );";


    public static final String DROP_TABLE = "DROP " + TABLE_NAME;


}
