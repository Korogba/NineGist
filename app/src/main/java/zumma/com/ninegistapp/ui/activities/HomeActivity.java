package zumma.com.ninegistapp.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseUser;

import zumma.com.ninegistapp.R;

public class HomeActivity extends Activity {


    private static final String FIREBASE_URL = "https://boiling-fire-6685.firebaseio.com/";
    private static final String TAG = HomeActivity.class.getSimpleName();

    private ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        user = ParseUser.getCurrentUser();
        if (user == null){
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
            finish();
        }

//        BasicInfo info = new BasicInfo(user);

//        Firebase mFirebaseRef = new Firebase(FIREBASE_URL)
//                .child("9Gist/Users")
//                .child("user_2348022020231")
//                .child("friends");
//
//
//        mFirebaseRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.d(TAG, "  onDataChange  " + dataSnapshot.getValue());
//                Toast.makeText(HomeActivity.this, dataSnapshot.getValue() + "", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                Log.d(TAG, " onCancelled  " + firebaseError.getMessage());
//                Toast.makeText(HomeActivity.this, firebaseError.getMessage() + "", Toast.LENGTH_LONG).show();
//            }
//        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
