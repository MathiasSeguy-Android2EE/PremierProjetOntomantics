package com.android2ee.formation.intra.ontomantics.premierprojet.view;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.android2ee.formation.intra.ontomantics.premierprojet.R;

public class MainActivity extends AppCompatActivity  {

    /***********************************************************
     * Managing life cycle
     **********************************************************/
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inflate the view
        setContentView(R.layout.activity_main);
    }

    /***********************************************************
     *  Managing Menu
     **********************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

}
