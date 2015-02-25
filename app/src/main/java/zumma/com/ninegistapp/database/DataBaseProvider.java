package zumma.com.ninegistapp.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

import zumma.com.ninegistapp.database.table.ChatTable;
import zumma.com.ninegistapp.database.table.FriendTable;

/**
 * Created by Okafor on 22/05/2014.
 */

public class DataBaseProvider extends ContentProvider {

    private static final String TAG = "DataBaseProvider";

    public static final String AUTHORITY = "zumma.com.ninegistapp.database.databaseprovider";
    public static final String VND_PATH = "vnd.zumma.com.ninegistapp.databaseprovider";

    public static final String CONTENT_URI_STRING = "content://" + AUTHORITY;
    public static final Uri CONTENT_URI = Uri.parse(CONTENT_URI_STRING);

    public static String getContentURIString() {
        return CONTENT_URI_STRING;
    }

    public static String getVndPath() {
        return VND_PATH;
    }

    private ChatSqlLiteHelper dbHelper;

    private UriMatcher mUriMatcher;

    private static final int FRIEND_TABLE_QUERY_CODE = 1;
    private static final int FRIEND_TABLE_SINGLE_ROW_SELECTION = 2;
    private static final int FRIEND_TABLE_SEARCH_FILTER = 21;

    private static final int CHAT_TABLE_QUERY_CODE = 3;
    private static final int CHAT_TABLE_SINGLE_ROW_SELECTION = 4;


    private UriMatcher getUriMatcher() {
        if (mUriMatcher == null) {
            UriMatcher newMatcher = new UriMatcher(UriMatcher.NO_MATCH);

            newMatcher.addURI(AUTHORITY, FriendTable.TABLE_NAME, FRIEND_TABLE_QUERY_CODE);
            newMatcher.addURI(AUTHORITY, FriendTable.TABLE_NAME+"/#", FRIEND_TABLE_SINGLE_ROW_SELECTION);
            newMatcher.addURI(AUTHORITY, FriendTable.TABLE_NAME+"/*", FRIEND_TABLE_SEARCH_FILTER);

            newMatcher.addURI(AUTHORITY, ChatTable.TABLE_NAME, CHAT_TABLE_QUERY_CODE);
            newMatcher.addURI(AUTHORITY, ChatTable.TABLE_NAME+"/#", CHAT_TABLE_SINGLE_ROW_SELECTION);

            mUriMatcher = newMatcher;
        }
        return mUriMatcher;
    }


    @Override
    public boolean onCreate() {
        dbHelper = new ChatSqlLiteHelper(getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String groupBy = null;
        String having = null;
        List<String> segments = uri.getPathSegments();

        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            int uriCode = getUriMatcher().match(uri);
            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
            Uri notificationUri = uri;

            switch (uriCode) {
                case FRIEND_TABLE_QUERY_CODE:
                    standardQuery(qb, segments, FriendTable.TABLE_NAME,
                            FriendTable.sProjectionMap, FriendTable.COLUMN_ID);
                    break;
                case FRIEND_TABLE_SEARCH_FILTER:
                    standardFilterQuery(qb, segments, FriendTable.TABLE_NAME,
                            FriendTable.sProjectionMap, FriendTable.COLUMN_ID, FriendTable.COLUMN_USERNAME, uri.getLastPathSegment());
                    break;
                case CHAT_TABLE_QUERY_CODE:
                    standardQuery(qb, segments, ChatTable.TABLE_NAME,
                            ChatTable.sProjectionMap, ChatTable.COLUMN_ID);
                    break;
                default:
                    throw new UnsupportedOperationException("The URI "
                            + uri.toString() + " is not supported for query.");
            }

            Cursor c = qb.query(db, projection, selection, selectionArgs,
                    groupBy, having, sortOrder);
            c.setNotificationUri(getContext().getContentResolver(),
                    notificationUri);
            return  c;

        } catch (Exception e) {
            Log.e(TAG, "Exception executing query", e);
        }

        return null;
    }

    @Override
    public String getType(Uri uri) {
        switch (getUriMatcher().match(uri)) {
            case FRIEND_TABLE_QUERY_CODE:
                return FriendTable.CONTENT_TYPE;
            case CHAT_TABLE_QUERY_CODE:
                return ChatTable.CONTENT_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri baseUri = null;
        Uri newObjectUri = null;
        long rowId = - 1;

        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            int uriCode = getUriMatcher().match(uri);

            switch (uriCode) {
                case FRIEND_TABLE_QUERY_CODE:
                    rowId = db.insertWithOnConflict(FriendTable.TABLE_NAME, null,
                            values, SQLiteDatabase.CONFLICT_REPLACE);
                    baseUri = FriendTable.CONTENT_URI;
                    break;
                case CHAT_TABLE_QUERY_CODE:
                    rowId = db.insertWithOnConflict(ChatTable.TABLE_NAME, null,
                            values, SQLiteDatabase.CONFLICT_REPLACE);
                    baseUri = ChatTable.CONTENT_URI;
                    break;
                default:
                    throw new UnsupportedOperationException("The URI "
                            + uri.toString() + " is not supported for insert.");

            }
        } catch (Exception e) {
            Log.e(TAG, "Exception during insert", e);
        }

        if (rowId >= 0 && baseUri != null) {
            newObjectUri = ContentUris.withAppendedId(baseUri, rowId);
            getContext().getContentResolver().notifyChange(baseUri, null);
        }
        return newObjectUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;

        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            int uriCode = getUriMatcher().match(uri);

            switch (uriCode) {
                case FRIEND_TABLE_QUERY_CODE:
                    count = db.delete(FriendTable.TABLE_NAME, selection,
                            selectionArgs);
                    break;
                case CHAT_TABLE_QUERY_CODE:
                    count = db.delete(ChatTable.TABLE_NAME, selection,
                            selectionArgs);
                    break;
                default:
                    throw new UnsupportedOperationException("The URI "
                            + uri.toString() + " is not supported for delete.");
            }

        } catch (Exception e) {
            Log.e(TAG, "Exception while deleting", e);
        }

        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        String table = null;
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            int uriCode = getUriMatcher().match(uri);
            switch (uriCode) {
                case FRIEND_TABLE_QUERY_CODE:
                    count = db.updateWithOnConflict(FriendTable.TABLE_NAME, values,
                            selection, selectionArgs,
                            SQLiteDatabase.CONFLICT_REPLACE);
                    break;
                case FRIEND_TABLE_SINGLE_ROW_SELECTION:
                    count = db.update(FriendTable.TABLE_NAME, values, "_id = ?", new String[]{uri.getLastPathSegment()});
                    break;
                case CHAT_TABLE_QUERY_CODE:
                    count = db.updateWithOnConflict(ChatTable.TABLE_NAME, values,
                            selection, selectionArgs,
                            SQLiteDatabase.CONFLICT_REPLACE);
                    break;
                case CHAT_TABLE_SINGLE_ROW_SELECTION:
                    count = db.update(ChatTable.TABLE_NAME, values, "_id = ?", new String[]{uri.getLastPathSegment()});
                    break;
                default:
                    throw new UnsupportedOperationException("The URI "
                            + uri.toString() + " is not supported for update.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception during update", e);
        }
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            Log.i("Error", "Update " + table + " is not updated");
        }

        return count;
    }

    public int bulkInsert(Uri uri, ContentValues[] values) {
        int count = 0;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();

        try {
            int uriCode = getUriMatcher().match(uri);

            switch (uriCode) {
                case FRIEND_TABLE_QUERY_CODE:
                    count = standardBulkInsert(db, values, FriendTable.TABLE_NAME,
                            null, SQLiteDatabase.CONFLICT_REPLACE);
                    break;
                case CHAT_TABLE_QUERY_CODE:
                    count = standardBulkInsert(db, values, ChatTable.TABLE_NAME,
                            null, SQLiteDatabase.CONFLICT_REPLACE);
                    break;
                default:
                    throw new UnsupportedOperationException("The URI "
                            + uri.toString() + " is not supported for bulkInsert.");
            }

        } catch (Exception e) {
            Log.e(TAG, "Exception during bulk insert", e);
        } finally {
            db.endTransaction();
        }

        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return count;
    }

    private static void standardFilterQuery(SQLiteQueryBuilder qb,
                                      List<String> segments, String tableName,
                                      HashMap<String, String> projectionMap, String idColumn, String filerColumn, String value) {
        if (segments.size() == 2) {
            qb.appendWhere(filerColumn + " LIKE '%"+value+"%'");
        }
        qb.setTables(tableName);
        qb.setProjectionMap(projectionMap);
    }

    private static void standardQuery(SQLiteQueryBuilder qb,
                                      List<String> segments, String tableName,
                                      HashMap<String, String> projectionMap, String idColumn) {
        if (segments.size() == 2) {
            qb.appendWhere(idColumn + "=" + segments.get(1));
        }
        qb.setTables(tableName);
        qb.setProjectionMap(projectionMap);
    }

    private int standardBulkInsert(SQLiteDatabase db,
                                   ContentValues[] valuesArray, String tableName,
                                   String nullColumnHack, int conflictAlgorithm) {
        int count = 0;
        long rowId;

        for (ContentValues values : valuesArray) {
            if (values != null) {
                rowId = db.insertWithOnConflict(tableName, nullColumnHack,
                        values, SQLiteDatabase.CONFLICT_REPLACE);

                if (rowId != -1)
                    count++;
            }
        }

        if (count > 0) {
            db.setTransactionSuccessful();
        }
        return count;
    }
}