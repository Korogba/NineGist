package zumma.com.ninegistapp.service.listeners;

import android.content.Context;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import java.util.Map;

/**
 * Created by Okafor on 20/02/2015.
 */
public class ChatListeners implements ChildEventListener {


    private static final String TAG = ChatListeners.class.getSimpleName() ;
    private Context context;

    public ChatListeners(Context context) {
        this.context = context;
    }


    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Log.d(TAG, " onChildAdded "+dataSnapshot.getValue() +"   "+s);
        Map<String, Object> conversationMap = (Map<String, Object>) dataSnapshot.getValue();

        Log.d(TAG, " conversationMap "+conversationMap.toString());
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Log.d(TAG, " onChildChanged "+dataSnapshot.getValue() +"   "+s);

//        Map<String, Object> conversationMap = (Map<String, Object>) dataSnapshot.getValue();
//        Set<Map.Entry<String, Object>> contacts = conversationMap.entrySet();
//
//        for (Map.Entry<String, Object> eachEntry : contacts) {
//            String key = eachEntry.getKey();
//            HashMap<String, String> map = (HashMap<String, String>) eachEntry.getValue();
//            Conversation value = Conversation.getConversation(map);
//            value.save();
//            Log.d(TAG, " Conversation "+value.toString());
//        }
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        Log.d(TAG, " onChildRemoved "+dataSnapshot.getValue());
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        Log.d(TAG, " onChildMoved "+dataSnapshot.getValue() +"   "+s);
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {
        Log.d(TAG, " onCancelled "+firebaseError.getMessage() +"   ");
    }
}
