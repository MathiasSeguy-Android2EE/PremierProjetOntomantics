package com.android2ee.formation.intra.ontomantics.smslistener;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getIntent().getExtras()!=null){
            if(getIntent().getExtras().getString(MySmsService.ACTION)!=null){
                Toast.makeText(this, "Action Love !!!!", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "No Action Notif normale!!!!", Toast.LENGTH_LONG).show();
        }
    }
}
