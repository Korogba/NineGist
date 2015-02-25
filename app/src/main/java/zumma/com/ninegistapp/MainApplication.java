package zumma.com.ninegistapp;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.firebase.client.Firebase;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

/**
 * Created by Okafor on 20/11/2014.
 */

public class MainApplication extends Application {

    private static final String TAG = MainApplication.class.getSimpleName();

    private SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize Crash Reporting

        Firebase.setAndroidContext(this);

        Parse.initialize(this, "tlqzrcEnfxp7Z6VukN9jURsdDGmU4yy1vf5qWw5w", "fcpglExKozc9DQFprnjGmA0leNOxh8sq9QAqoBof");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferences.edit().putBoolean(ParseConstants.FRIENDS_AVAILABLE,false).apply();

    }


    public static void updateParseInstallation(ParseUser user){
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put(ParseConstants.KEY_USER_ID,user.getObjectId());
        installation.saveInBackground();
    }




}
