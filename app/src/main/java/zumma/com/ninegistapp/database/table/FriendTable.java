package zumma.com.ninegistapp.database.table;

import android.net.Uri;
import android.provider.BaseColumns;

import java.util.HashMap;

import zumma.com.ninegistapp.database.DataBaseProvider;

/**
 * Created by Okafor on 31/10/2014.
 */
public class FriendTable {

    public static final String TABLE_NAME = "friend_tb";

    public static final Uri CONTENT_URI = Uri.parse(DataBaseProvider.getContentURIString() + "/" + TABLE_NAME);

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + DataBaseProvider.getVndPath() + "." + TABLE_NAME;

    public static HashMap<String, String> sProjectionMap;

    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_COUNTRY_NAME = "country_name";
    public static final String COLUMN_COUNTRY_CODE = "country_code";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_STATUS_ICON = "status_icon";
    public static final String COLUMN_PROFILE_PICTURE = "profile_pics";
    public static final String COLUMN_LAST_CHAT_TIME = "last_chat_time";
    public static final String COLUMN_MSG_COUNT = "message_count";
    public static final String COLUMN_LOCKED = "locked";
    public static final String COLUMN_UPDATED_AT= "updatedAt";
    public static final String COLUMN_CREATED_AT= "createdAt";
    public static final String COLUMN_USER_ID = "user_id";

    // Database creation sql statement
    public static final String CREATE_TABLE = "create table " + TABLE_NAME
            + "(" + COLUMN_ID + " TEXT PRIMARY KEY,"
            + COLUMN_USERNAME + " TEXT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PHONE_NUMBER + " INTEGER,"
            + COLUMN_COUNTRY_CODE + " TEXT,"
            + COLUMN_COUNTRY_NAME + " TEXT,"
            + COLUMN_MSG_COUNT + " INTEGER,"
            + COLUMN_STATUS + " TEXT,"
            + COLUMN_STATUS_ICON + " INTEGER,"
            + COLUMN_PROFILE_PICTURE + " BLOB,"
            + COLUMN_LOCKED + " INTEGER,"
            + COLUMN_LAST_CHAT_TIME + " TEXT,"
            + COLUMN_USER_ID + " TEXT,"
            + COLUMN_UPDATED_AT + " TEXT,"
            + COLUMN_CREATED_AT + " TEXT"
            + " );";


    public static final String DROP_TABLE = "DROP " + TABLE_NAME;
}
