package zumma.com.ninegistapp.ui.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

import zumma.com.ninegistapp.database.table.ChatTable;
import zumma.com.ninegistapp.model.Conversation;

/**
 * Created by Okafor on 21/02/2015.
 */
public class ChatArrayList extends ArrayList<Conversation> {

    private static final String TAG = ChatArrayList.class.getSimpleName();

    private Context context;
    private String friend_id;

    public ChatArrayList(Context context, String friend_id) {
        this.context = context;
        this.friend_id = friend_id;


        initChatList();
    }

    public boolean addNewChat(Conversation conversation){
        saveConversation(conversation);
        return super.add(conversation);
    }

    public void saveConversation(Conversation conversation){

        ContentValues values = new ContentValues();

        values.put(ChatTable.COLUMN_FROM,conversation.getFromId());
        values.put(ChatTable.COLUMN_TO,conversation.getToId());
        values.put(ChatTable.COLUMN_MESSAGE,conversation.getMsg());
        values.put(ChatTable.COLUMN_SENT,conversation.isSent());
        values.put(ChatTable.COLUMN_TIME,conversation.getDate());
        values.put(ChatTable.COLUMN_PRIVATE,conversation.getPflag());
        values.put(ChatTable.COLUMN_REPORT,conversation.getReport());
        values.put(ChatTable.COLUMN_CREATED_AT,conversation.getCreated_at());
        values.put(ChatTable.COLUMN_UNIQ_ID,conversation.getUniqkey());

        Uri uri = context.getContentResolver().insert(ChatTable.CONTENT_URI, values);
        if (uri != null){
            Log.d(TAG, " uri  "+uri.toString());
        }
    }

    public void upDateDeliveredConversation(Conversation conversation){

        String SELECTION = ChatTable.COLUMN_TO + "=? AND "+ChatTable.COLUMN_CREATED_AT +"=?";
        String[] args = {friend_id, conversation.getCreated_at()+""};

        ContentValues values = new ContentValues();
        values.put(ChatTable.COLUMN_REPORT,conversation.getReport());
        values.put(ChatTable.COLUMN_UNIQ_ID,conversation.getUniqkey());

        int update = context.getContentResolver().update(ChatTable.CONTENT_URI, values,SELECTION,args);
        if (update > 0){
            Log.d(TAG, " update delivered happened  "+update);
        }
    }

    public void upDisplayedConversation(Conversation conversation){

        String SELECTION = ChatTable.COLUMN_TO + "=? AND "+ChatTable.COLUMN_CREATED_AT +"=?";
        String[] args = {friend_id, conversation.getCreated_at()+""};

        ContentValues values = new ContentValues();
        values.put(ChatTable.COLUMN_REPORT,conversation.getReport());

        int update = context.getContentResolver().update(ChatTable.CONTENT_URI, values,SELECTION,args);
        if (update > 0){
            Log.d(TAG, " update displayed happened  "+update);
        }
    }


    public void initChatList(){

        String SELECTION = ChatTable.COLUMN_TO + "=?";
        String[] args = {friend_id};

        Cursor cursor = context.getContentResolver().query(ChatTable.CONTENT_URI, null, SELECTION, args,ChatTable.COLUMN_CREATED_AT);

        if (cursor != null && cursor.getCount() > 0) {


            int indexID = cursor.getColumnIndex(ChatTable.COLUMN_ID);
            int indexFrom = cursor.getColumnIndex(ChatTable.COLUMN_FROM);
            int indexTo = cursor.getColumnIndex(ChatTable.COLUMN_TO);
            int indexMsg = cursor.getColumnIndex(ChatTable.COLUMN_MESSAGE);
            int indexSent = cursor.getColumnIndex(ChatTable.COLUMN_SENT);
            int indexTime = cursor.getColumnIndex(ChatTable.COLUMN_TIME);
            int indexPrivate = cursor.getColumnIndex(ChatTable.COLUMN_PRIVATE);
            int indexReport = cursor.getColumnIndex(ChatTable.COLUMN_REPORT);
            int indexCreatedAt = cursor.getColumnIndex(ChatTable.COLUMN_CREATED_AT);
            int indexUnique = cursor.getColumnIndex(ChatTable.COLUMN_UNIQ_ID);


            cursor.moveToFirst();

            do {

                String id = cursor.getString(indexID);
                String fromId = cursor.getString(indexFrom);
                String toId = cursor.getString(indexTo);
                String msg = cursor.getString(indexMsg);
                boolean sent = cursor.getInt(indexSent) == 1 ? true : false;
                String date = cursor.getString(indexTime);

                int flag = cursor.getInt(indexPrivate);
                int report = cursor.getInt(indexReport);
                long created = cursor.getLong(indexCreatedAt);
                String uniq = cursor.getString(indexUnique);

                Conversation conversation = new Conversation(fromId,toId,date,sent,msg,flag,report,created,uniq);
                Log.d(TAG, conversation.toString() );

                this.add(conversation);

            } while (cursor.moveToNext());
        }

        cursor.close();
    }
}
