package zumma.com.ninegistapp.ui.activities;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;

import zumma.com.ninegistapp.MainActivity;
import zumma.com.ninegistapp.MainApplication;
import zumma.com.ninegistapp.ParseConstants;
import zumma.com.ninegistapp.R;
import zumma.com.ninegistapp.custom.CustomActivity;

public class ConfirmActivity extends CustomActivity {

    private static final String TAG = ConfirmActivity.class.getSimpleName() ;
    private EditText passcode;
    private Button cancel;
    private String mobile;
    private TextView confirm_note;

    private ProgressDialog pDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);



        Intent intent = getIntent();
        if (intent != null){
            mobile = intent.getStringExtra(ParseConstants.CONFIRM_NOTE);
            confirm_note = (TextView) findViewById(R.id.confirm_note);
            confirm_note.setText(getResources().getString(R.string.confirm_note,mobile));
        }

        initComponents();
        addListeners();

        ActionBar localActionBar = getActionBar();
        localActionBar.setTitle("Enter Code");
        localActionBar.setDisplayHomeAsUpEnabled(false);
        localActionBar.setHomeButtonEnabled(true);
        localActionBar.setDisplayShowHomeEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pDialog.dismiss();
    }

    // this will be used to initialize ui objects in the view
    private void initComponents() {

        passcode = (EditText) findViewById(R.id.passCode);

        pDialog = new ProgressDialog(ConfirmActivity.this);
        pDialog.setMessage(getString(R.string.please_wait));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);

        cancel = (Button) setTouchNClick(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    // Add The Action Listeners
    public void addListeners(){
        passcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (s.toString().length() < 5) {
//                    enterButton.setEnabled(false);
//                } else {
//                    enterButton.setEnabled(true);
//                }
            }
        });
    }


    private void executeOkAction() {

        String code = passcode.getText().toString();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.CLASS_AUTHENTICATE);
        query.whereEqualTo("code",code);
        query.setLimit(1);

        pDialog.show();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(e == null){
                    pDialog.hide();
                    if(parseObjects != null && parseObjects.size() > 0){
                        Log.d(TAG, "Succesfull Code Search");
                        ParseObject object = parseObjects.get(0);
                        ParseUser new_user = createUser(object);

                        ParseQuery<ParseUser> query = ParseUser.getQuery();
                        query.whereEqualTo(ParseConstants.KEY_USERNAME, new_user.getUsername());

                        ParseUser user = null;
                        try {
                            user = query.getFirst();
                            if (user != null){
                                ParseUser.logInInBackground(user.getUsername(), new_user.getUsername(), new LogInCallback() {
                                    @Override
                                    public void done(ParseUser user, ParseException e) {
                                        if (e == null) {
                                            MainApplication.updateParseInstallation(ParseUser.getCurrentUser());
                                            Intent intent = new Intent(ConfirmActivity.this, MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(ConfirmActivity.this, R.string.encounter_error_label, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        } catch (ParseException ex) {
                            new_user.signUpInBackground(new SignUpCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null){
                                        MainApplication.updateParseInstallation(ParseUser.getCurrentUser());
                                        Intent intent = new Intent(ConfirmActivity.this,MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(ConfirmActivity.this, R.string.encounter_error_label, Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }else{
                        pDialog.hide();
                        Toast.makeText(ConfirmActivity.this, R.string.code_error_label, Toast.LENGTH_LONG).show();
                        passcode.setText("");
                    }
                }else{
                    pDialog.hide();
                    Toast.makeText(ConfirmActivity.this, R.string.encounter_error_label, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private ParseUser createUser(ParseObject object) {

        String username = (String)object.get(ParseConstants.KEY_PHONE_NUMBER);
        String password = (String)object.get(ParseConstants.KEY_PHONE_NUMBER);
        String country_code = (String)object.get(ParseConstants.KEY_COUNTRY_CODE);
        String country_name = (String)object.get(ParseConstants.KEY_COUNTRY_NAME);

        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.put(ParseConstants.KEY_COUNTRY_CODE, country_code);
        user.put(ParseConstants.KEY_COUNTRY_NAME, country_name);
        return user;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_confirm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_done) {
            executeOkAction();
        }

        return super.onOptionsItemSelected(item);
    }
}
