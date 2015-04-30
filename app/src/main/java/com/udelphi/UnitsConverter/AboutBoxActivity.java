package com.udelphi.UnitsConverter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AboutBoxActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_box);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        Intent intent = this.getIntent();
        String s = intent.getStringExtra("AppVersionInfo");
        TextView text = (TextView) findViewById(R.id.textViewAppName);
        text.setText(s);
    }
}
