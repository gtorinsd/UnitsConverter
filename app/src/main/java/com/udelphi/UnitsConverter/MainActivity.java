package com.udelphi.UnitsConverter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.content.Intent;
import android.widget.TabWidget;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity
{
    private ConverterApplication m_Application;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Show the EULA
        new Eula(this).show();

        m_Application = (ConverterApplication) getApplicationContext();
        InitTabs();
        SetAllTabsFontSize();
        if(savedInstanceState == null)
        {
            AssignFragmentToTabs();
        }

        this.getSupportActionBar().setIcon(R.drawable.ic_launcher);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
    }

    // Fill tab sheets
    private void InitTabs()
    {
        String[] tabsCaptions = this.getResources().getStringArray(R.array.ArrTabsCaptions);
        TabHost tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec("Tab1");
        spec.setContent(R.id.tabDataUnits);
        spec.setIndicator(tabsCaptions[0], getResources().getDrawable(R.drawable.tab_icon_selector));
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Tab2");
        spec.setContent(R.id.tabSpeedUnits);
        spec.setIndicator(tabsCaptions[1], getResources().getDrawable(R.drawable.tab_icon_selector));
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Tab3");
        spec.setContent(R.id.tabTemperatureUnits);
        spec.setIndicator(tabsCaptions[2], getResources().getDrawable(R.drawable.tab_icon_selector));
        tabHost.addTab(spec);

        tabHost.getTabWidget().setStripEnabled(false);
        tabHost.setCurrentTab(0);
    }

    // Fill tabs content
    private void AssignFragmentToTabs()
    {
        //FragmentManager manager = this.getFragmentManager();
        android.support.v4.app.FragmentManager manager = this.getSupportFragmentManager();

        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        ConverterFragment fragment = ConverterFragment.GetInstance(R.array.ArrInformationUnits, m_Application.tagConverterInfo);
        transaction.replace(R.id.tabDataUnits, fragment);
        transaction.commit();

        transaction = manager.beginTransaction();
        fragment = ConverterFragment.GetInstance(R.array.ArrSpeedUnits, m_Application.tagConverterSpeed);
        transaction.replace(R.id.tabSpeedUnits, fragment);
        transaction.commit();

        transaction = manager.beginTransaction();
        fragment = ConverterFragment.GetInstance(R.array.ArrTemperatureUnits, m_Application.tagConverterTemperature);
        transaction.replace(R.id.tabTemperatureUnits, fragment);
        transaction.commit();
    }

    // Changes caption font size for all tabs
    private void SetAllTabsFontSize() {
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        for (int i = 0; i < tabHost.getTabWidget().getTabCount(); i++)
        {
            TabWidget tw = (TabWidget) tabHost.findViewById(android.R.id.tabs);
            ViewGroup tab = (ViewGroup) tw.getChildAt(i);
            TextView tabTextView = (TextView) tab.getChildAt(1);
            int textSize = getResources().getInteger(R.integer.tabHostCaptionFontSize);
            tabTextView.setTextSize(textSize);
        }
    }

    // Events handlers
    //==============================================================================================
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.dialog_exit_text);
            builder.setTitle(R.string.app_name);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.btnOk, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                            System.exit(0);
                            dialog.dismiss();
                        }
                    }
            );
            builder.setNegativeButton(R.string.btnCancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }
            );

            AlertDialog alert = builder.create();
            alert.show();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch(id)
        {
            case R.id.action_about:
            {
                // Get app version info
                String appVersionInfo = getString(R.string.app_name) + " " + getString(R.string.app_version);

                // Get intent
                Intent intent = new Intent(this, AboutBoxActivity.class);
                // Send parameter to the AboutBoxActivity activity
                intent.putExtra("AppVersionInfo", appVersionInfo);
                startActivity(intent);
                return true;
            }

            default:
            {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        TabHost host = (TabHost) findViewById(R.id.tabHost);
        outState.putInt("ActiveTabIndex", host.getCurrentTab());

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle outState)
    {
        super.onRestoreInstanceState(outState);
        int i = outState.getInt("ActiveTabIndex");
        TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setCurrentTab(i);
    }
    //==============================================================================================
}
