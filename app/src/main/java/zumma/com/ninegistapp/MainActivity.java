package zumma.com.ninegistapp;

/**
 * Created by Okafor on 03/02/2015.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import zumma.com.ninegistapp.custom.CustomActivity;
import zumma.com.ninegistapp.database.table.FriendTable;
import zumma.com.ninegistapp.model.BasicInfo;
import zumma.com.ninegistapp.model.Conversation;
import zumma.com.ninegistapp.model.Data;
import zumma.com.ninegistapp.model.Friend;
import zumma.com.ninegistapp.model.User;
import zumma.com.ninegistapp.service.DataService;
import zumma.com.ninegistapp.service.FriendsSearchService;
import zumma.com.ninegistapp.service.classes.FriendsSearch;
import zumma.com.ninegistapp.ui.adapters.LeftNavAdapter;
import zumma.com.ninegistapp.ui.adapters.RightNavAdapter;
import zumma.com.ninegistapp.ui.fragments.ChatFragment;
import zumma.com.ninegistapp.ui.fragments.FindMatch;
import zumma.com.ninegistapp.ui.fragments.FriendsFragment;
import zumma.com.ninegistapp.ui.fragments.Match;
import zumma.com.ninegistapp.ui.fragments.Profile;
import zumma.com.ninegistapp.ui.fragments.Settings;

/**
 * The Class MainActivity is the base activity class of the application. This
 * activity is launched after the Login and it holds all the Fragments used in
 * the app. It also creates the Navigation Drawers on left and right side.
 */
public class MainActivity extends CustomActivity
{

    private static final String TAG = MainActivity.class.getSimpleName();
    /** The drawer layout. */
    private DrawerLayout drawerLayout;

    /** ListView for left side drawer. */
    private ListView drawerLeft;

    /** ListView for right side drawer. */
    private ListView drawerRight;

    /** The drawer toggle. */
    private ActionBarDrawerToggle drawerToggle;

    /** The left navigation list adapter. */
    private LeftNavAdapter adapter;

    private Menu menu;

    /* (non-Javadoc)
     * @see com.newsfeeder.custom.CustomActivity#onCreate(android.os.Bundle)
     */

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG, "am at onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupDrawer();
        setupContainer();

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        initUser();
    }

    /**
     * Setup the drawer layout. This method also includes the method calls for
     * setting up the Left & Right side drawers.
     */
    private void setupDrawer()
    {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View view)
            {
                setActionBarTitle();
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView)
            {
                setActionBarTitle();
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        drawerLayout.closeDrawers();

        setupLeftNavDrawer();
        setupRightNavDrawer();
    }

    /**
     * Setup the left navigation drawer/slider. You can add your logic to load
     * the contents to be displayed on the left side drawer. It will also setup
     * the Header and Footer contents of left drawer. This method also apply the
     * Theme for components of Left drawer.
     */
    private void setupLeftNavDrawer()
    {
        Log.d(TAG, "am setupLeftNavDrawer");

        drawerLeft = (ListView) findViewById(R.id.left_drawer);

        View header = getLayoutInflater().inflate(R.layout.left_nav_header,
                null);
        drawerLeft.addHeaderView(header);

        adapter = new LeftNavAdapter(this, getResources().getStringArray(
                R.array.arr_left_nav_list));
        drawerLeft.setAdapter(adapter);
        drawerLeft.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
                                    long arg3)
            {
                drawerLayout.closeDrawers();
                if (pos != 0)
                    launchFragment(pos - 1, null);
                else
                    launchFragment(-2, null);

            }
        });

    }

    /**
     * Setup the right navigation drawer/slider. You can add your logic to load
     * the contents to be displayed on the right side drawer. It will also setup
     * the Header contents of right drawer.
     */
    private void setupRightNavDrawer()
    {
        drawerRight = (ListView) findViewById(R.id.right_drawer);

        View header = getLayoutInflater().inflate(R.layout.rigth_nav_header,
                null);
        header.setClickable(true);
        drawerRight.addHeaderView(header);

        ArrayList<Data> al = new ArrayList<Data>();
        al.add(new Data("Emely", R.drawable.img_f1));
        al.add(new Data("John", R.drawable.img_f2));
        al.add(new Data("Aaliyah", R.drawable.img_f3));
        al.add(new Data("Valentina", R.drawable.img_f4));
        al.add(new Data("Barbara", R.drawable.img_f5));

        ArrayList<Data> al1 = new ArrayList<Data>(al);
        al1.addAll(al);
        al1.addAll(al);
        al1.addAll(al);

        drawerRight.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                drawerLayout.closeDrawers();
                launchFragment(1, null);
            }
        });
        drawerRight.setAdapter(new RightNavAdapter(this, al1));
    }

    /**
     * Setup the container fragment for drawer layout. This method will setup
     * the grid view display of main contents. You can customize this method as
     * per your need to display specific content.
     */
    private void setupContainer()
    {
        getSupportFragmentManager().addOnBackStackChangedListener(
                new OnBackStackChangedListener() {

                    @Override
                    public void onBackStackChanged()
                    {
                        setActionBarTitle();
                    }
                });
        launchFragment(0, null);
    }

    /**
     * This method can be used to attach Fragment on activity view for a
     * particular tab position. You can customize this method as per your need.
     *
     * @param pos
     *            the position of tab selected.
     */
    public void launchFragment(int pos, Bundle bundle)
    {

        Fragment f = null;
        String title = null;
        if (pos == -1)
        {
            title = "Your Match";
            f = new Match();
        }
        else if (pos == -2)
        {
            title = "Profile";
            f = new Profile();
        }
        else if (pos == 0)
        {
            title = "Home";
            Log.d(TAG, "am MainFrag Launch");
//            f = new MainFragment();
            f = new FriendsFragment();

        }
        else if (pos == 1)
        {
            MenuItem item = menu.findItem(R.id.menu_search);
            Log.d(TAG, "Testing " + item.getTitle());
            item.setVisible(false);
            title = "Chat";
            f = new ChatFragment();
            f.setArguments(bundle);
        }
        else if (pos == 2)
        {
            title = "Find Match";
            f = new FindMatch();
        }
        else if (pos == 3)
        {
            title = "Settings";
            f = new Settings();
        }

        if (f != null)
        {
            while (getSupportFragmentManager().getBackStackEntryCount() > 0)
            {
                getSupportFragmentManager().popBackStackImmediate();
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, f).addToBackStack(title)
                    .commit();
            if (adapter != null && pos >= 0)
                adapter.setSelection(pos);
        }
    }

    /**
     * Set the action bar title text.
     */
    private void setActionBarTitle()
    {
        if (drawerLayout.isDrawerOpen(drawerLeft))
        {
            getActionBar().setTitle("Main Menu");
            return;
        }
        if (drawerLayout.isDrawerOpen(drawerRight))
        {
            getActionBar().setTitle(R.string.all_matches);
            return;
        }

        if (getSupportFragmentManager().getBackStackEntryCount() == 0)
            return;
        String title = getSupportFragmentManager().getBackStackEntryAt(
                getSupportFragmentManager().getBackStackEntryCount() - 1)
                .getName();
        getActionBar().setTitle(title);
        // getActionBar().setLogo(R.drawable.icon);
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onPostCreate(android.os.Bundle)
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);

        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onConfigurationChanged(android.content.res.Configuration)
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggle
        drawerToggle.onConfigurationChanged(newConfig);
    }

    /* (non-Javadoc)
     * @see com.newsfeeder.custom.CustomActivity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);

        this.menu = menu;

        if (drawerLayout.isDrawerOpen(drawerLeft)
                || drawerLayout.isDrawerOpen(drawerRight))
            menu.findItem(R.id.menu_chat).setVisible(false);
        else if (drawerLayout.isDrawerOpen(drawerRight))
            menu.findItem(R.id.menu_edit).setVisible(true);

        return true;
    }

    /* Called whenever we call invalidateOptionsMenu() */
	/* (non-Javadoc)
	 * @see android.app.Activity#onPrepareOptionsMenu(android.view.Menu)
	 */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        // If the nav drawer is open, hide action items related to the content
        // view
        // boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        // menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /* (non-Javadoc)
     * @see com.newsfeeder.custom.CustomActivity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home
                && drawerLayout.isDrawerOpen(drawerRight))
        {
            drawerLayout.closeDrawer(drawerRight);
            return true;
        }
        if (drawerToggle.onOptionsItemSelected(item))
        {
            drawerLayout.closeDrawer(drawerRight);
            return true;
        }

        if (item.getItemId() == R.id.menu_chat)
        {
            drawerLayout.closeDrawer(drawerLeft);
            if (!drawerLayout.isDrawerOpen(drawerRight))
                drawerLayout.openDrawer(drawerRight);
            else
                drawerLayout.closeDrawer(drawerRight);
        }
        return super.onOptionsItemSelected(item);
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onKeyDown(int, android.view.KeyEvent)
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1)
            {
                getSupportFragmentManager().popBackStackImmediate();
            }
            else
                finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isNetwork = StaticMethods.haveNetworkConnection(this);
        if (isNetwork == true){
            Log.d(TAG, "network home available=" + isNetwork);

            boolean search_flag = preferences.getBoolean(ParseConstants.FRIENDS_AVAILABLE, false);
            if (search_flag == false){
                Intent fiIntent = new Intent(this, FriendsSearchService.class);
                startService(fiIntent);
            }

        }else{
            Toast.makeText(this, "Cannot connect to the network...", Toast.LENGTH_LONG).show();
        }

        Intent fiIntent = new Intent(this, DataService.class);
        startService(fiIntent);

    }

    public void initUser(){

        boolean user_created = preferences.getBoolean(ParseConstants.USER_CREATED,false);

        String user_id = ParseUser.getCurrentUser().getObjectId();
        if (!user_created){

            Firebase mFirebaseRef = new Firebase(ParseConstants.FIREBASE_URL).child("9Gist").child(user_id);

            BasicInfo info = new BasicInfo(ParseUser.getCurrentUser());

            FriendsSearch friendsSearch = new FriendsSearch();

            Set<Map.Entry<String, String>> contacts = friendsSearch.allUserContacts(this).entrySet();
            HashMap<String,Friend> friendList = new HashMap<String,Friend>();

            Cursor cursor = getContentResolver().query(FriendTable.CONTENT_URI, null, null, null, null);

            int indexID = cursor.getColumnIndex(FriendTable.COLUMN_ID);
            int indexName = cursor.getColumnIndex(FriendTable.COLUMN_USERNAME);
            int indexNumber = cursor.getColumnIndex(FriendTable.COLUMN_PHONE_NUMBER);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    List<Conversation> arrayList = new ArrayList<Conversation>();

                    String id = cursor.getString(indexID);
                    String name = cursor.getString(indexName);
                    String number = cursor.getString(indexNumber);

                    Friend friend = new Friend(name,number,arrayList);
                    friendList.put(id,friend);

                } while (cursor.moveToNext());

            }
            cursor.close();

            User user = new User(info, friendList);

            mFirebaseRef.setValue(user, new Firebase.CompletionListener() {
                @Override
                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                    if (firebaseError != null) {
                        Log.d(TAG,"Data could not be saved. " + firebaseError.getMessage());
                    } else {
                        Log.d(TAG, "Data saved successfully.");
                    }
                }
            });

            preferences.edit().putBoolean(ParseConstants.USER_CREATED, true).apply();
        }
    }
}