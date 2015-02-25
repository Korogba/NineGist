package zumma.com.ninegistapp.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.HandlerThread;
import android.os.IBinder;
import android.widget.Toast;

import com.parse.ParseUser;

import zumma.com.ninegistapp.service.listeners.ChatListeners;
import zumma.com.ninegistapp.service.listeners.FriendListener;

public class DataService extends Service {

    private static final String TAG = DataService.class.getSimpleName();

    private FriendListener friendListener;
    private ChatListeners chatListener;

    private ParseUser mCurrentUser;

    private String user_id;

    SharedPreferences preferences;

    public DataService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "service started", Toast.LENGTH_LONG).show();

        friendListener = new FriendListener(this);
        chatListener = new ChatListeners(this);

        mCurrentUser = ParseUser.getCurrentUser();
        user_id = mCurrentUser.getObjectId();

//
//        Firebase mFirebaseRef = new Firebase(ParseConstants.FIREBASE_URL).child("9Gist/").child(user_id).child("friends");
//        mFirebaseRef.addValueEventListener(friendListener);
//
//        Firebase cFirebaseRef = new Firebase(ParseConstants.FIREBASE_URL).child("9Gist/").child(user_id).child("chats");
//        cFirebaseRef.addChildEventListener(chatListener);

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "service started", Toast.LENGTH_LONG).show();

        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                android.os.Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_LONG).show();
    }


}
