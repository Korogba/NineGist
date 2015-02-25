package zumma.com.ninegistapp.ui.activities;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import java.util.HashMap;
import java.util.Random;
import zumma.com.ninegistapp.ParseConstants;
import zumma.com.ninegistapp.R;
import zumma.com.ninegistapp.custom.CustomActivity;

public class SignUpActivity extends CustomActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName() ;
    private Button countryField;
    private TextView countryCodeField;
    private EditText phoneField;
    private Button cancel;

    HashMap<String,String> options;
    ArrayAdapter dataAdapter;

    private ProgressDialog pDialog;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initComponents();

        ActionBar localActionBar = getActionBar();
        localActionBar.setTitle("Your Phone");
        localActionBar.setDisplayHomeAsUpEnabled(false);
        localActionBar.setHomeButtonEnabled(true);
        localActionBar.setDisplayShowHomeEnabled(false);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pDialog.dismiss();
    }

    public void initComponents(){
        countryField = (Button) findViewById(R.id.countryField);
        countryCodeField = (TextView) findViewById(R.id.countryCodeField);
        phoneField = (EditText) findViewById(R.id.phoneField);
        pDialog = new ProgressDialog(SignUpActivity.this);
        pDialog.setMessage(getString(R.string.send_code));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);

        cancel = (Button) setTouchNClick(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, IntroActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // to add items from local to the spinner
    public void addItemsOnSpinnerCountry() {

        options = new HashMap<String,String>();
        String optionsCountry [] = getResources().getStringArray(R.array.country_arrays);
        String optionsCode []= getResources().getStringArray(R.array.country_code_arrays);
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);

        for(int i=0;i<optionsCountry.length;i++)  {
            options.put(optionsCountry[i], optionsCode[i]);
            dataAdapter.add(optionsCountry[i]);
        }

        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
    }

    public AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String value = parent.getItemAtPosition(position).toString();
            countryCodeField.setText(options.get(value));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void executeOkAction() {
        final String country = countryField.getText().toString().trim();
        final String code = countryCodeField.getText().toString().trim();
        final String phone = phoneField.getText().toString().trim();

        if(country.isEmpty() || code.isEmpty() || phone.isEmpty() ){
            Toast.makeText(SignUpActivity.this, R.string.signup_error_label, Toast.LENGTH_LONG).show();
        }else{

            final String fullNumber = getFullMobileNumber(code,phone);

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(R.string.alert_title);
            dialog.setMessage(getResources().getString(R.string.verify, fullNumber));
            dialog.setCancelable(false);
            dialog.setPositiveButton(R.string.yes,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int buttonId) {
                            int passCode = randInt(10000,99999);

                            HashMap<String, String> params = new HashMap<String, String>();
                            params.put(ParseConstants.KEY_COUNTRY_NAME,country);
                            params.put(ParseConstants.KEY_COUNTRY_CODE,code.replace("+",""));
                            params.put(ParseConstants.KEY_PHONE_NUMBER,fullNumber);
                            params.put(ParseConstants.KEY_CONFIRM_CODE,passCode+"");

                            pDialog.show();
                            ParseCloud.callFunctionInBackground("AuthenticateAndSave", params, new FunctionCallback<String>() {
                                public void done(String result, ParseException e) {
                                    if (e == null) {
                                        if (result.trim().equals("Message Sent Successfully.")) {
                                            pDialog.hide();
                                            Intent intent = new Intent(SignUpActivity.this, ConfirmActivity.class);
                                            intent.putExtra(ParseConstants.CONFIRM_NOTE, fullNumber);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(SignUpActivity.this, getString(R.string.code_send_error) + "1", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        pDialog.hide();
                                        Toast.makeText(SignUpActivity.this, getString(R.string.code_send_error) + "2 " + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }
            );
            dialog.setNegativeButton(R.string.no,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int buttonId) {
                            dialog.cancel();
                        }
                    }
            );
            dialog.setIcon(R.drawable.ic_action_warning);
            dialog.show();
        }
    }

    public int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    private String getFullMobileNumber(String code, String phone_number){
        if(phone_number.subSequence(0, 1).equals("0")){
            phone_number = code + phone_number.substring(1);
        }else{
            phone_number = code + phone_number;
        }
        return phone_number;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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
