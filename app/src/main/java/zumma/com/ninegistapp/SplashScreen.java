package zumma.com.ninegistapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import java.util.Timer;
import java.util.TimerTask;

import zumma.com.ninegistapp.ui.activities.IntroActivity;

public class SplashScreen extends Activity {
    private boolean isRunning;

    private void doFinish() {
        Intent localIntent = new Intent(this, IntroActivity.class);
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(localIntent);
        finish();
    }

    private void startSplash() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                doFinish();
            }
        }, 1 * 1000);
    }

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.splash);
        this.isRunning = true;
        startSplash();
    }

    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
        if (paramInt == 4) {
            this.isRunning = false;
            finish();
            return true;
        }
        return super.onKeyDown(paramInt, paramKeyEvent);
    }
}
