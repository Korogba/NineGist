package zumma.com.ninegistapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import zumma.com.ninegistapp.custom.CustomActivity;


public class Login extends CustomActivity {

    private static final String TAG = Login.class.getSimpleName();

    @Override
    public void onClick(View v) {
        Log.d(TAG, "am at click");
        if ((v.getId() == R.id.btnLogin) || (v.getId() == R.id.btnFb) || (v.getId() == R.id.btnTw)) {
            Log.d(TAG, "am at click enter");
            startActivity(new Intent(this, MainActivity.class));
//            finish();
            Log.d(TAG, "am at click end");
        }
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.login);
        TextView localTextView = (TextView) setTouchNClick(R.id.lblReg);
        localTextView.setText(Html.fromHtml(localTextView.getText().toString()));
        setTouchNClick(R.id.btnLogin);
        setTouchNClick(R.id.btnFb);
        setTouchNClick(R.id.btnTw);

        Log.d(TAG, "am at login");
    }
}