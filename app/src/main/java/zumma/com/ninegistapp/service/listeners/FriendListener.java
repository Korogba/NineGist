package zumma.com.ninegistapp.service.listeners;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import zumma.com.ninegistapp.database.table.FriendTable;

/**
 * Created by Okafor on 19/02/2015.
 */
public class FriendListener implements ValueEventListener {

    private Context context;
    public FriendListener(Context context) {
        this.context = context;
    }

    private static final String TAG = FriendListener.class.getSimpleName();

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Log.d(TAG, " onDataChange DataSnapshot :" +dataSnapshot.getValue());

        HashMap<String,HashMap<String,String>> friendList = dataSnapshot.getValue(HashMap.class);
        if (friendList != null){
            upDateFriendTable(friendList);
        }

    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {
        Log.d(TAG, " onCancelled FirebaseError :"+firebaseError.getMessage());
    }


    public void upDateFriendTable(HashMap<String,HashMap<String,String>> friendList){

        String SELECTION = FriendTable.COLUMN_ID + "=?";

        Set<Map.Entry<String, HashMap<String,String>>> friends = friendList.entrySet();

        for (Map.Entry<String, HashMap<String,String>> eachEntry : friends) {

            String objectId = eachEntry.getKey();
            HashMap<String,String> friend = eachEntry.getValue();

            ContentValues values = new ContentValues();
            values.put(FriendTable.COLUMN_STATUS,friend.get("status"));
            values.put(FriendTable.COLUMN_USERNAME,friend.get("name"));
            values.put(FriendTable.COLUMN_PHONE_NUMBER,friend.get("phone_number"));

            String[] args = {objectId};
            int update = context.getContentResolver().update(FriendTable.CONTENT_URI, values, SELECTION, args);
            if (update > 0){
                Log.d(TAG, " UPDATE SUCCES ON :"+objectId);
            }
        }
    }

}
