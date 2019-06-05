package com.android2ee.formation.intra.ontomantics.smslistener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MySmsReceiver extends BroadcastReceiver {
    private static final String tag="MySmsReceiver";
    public MySmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(tag,"J'ai recu un sms ouais, trop chouette");
        //start service
        Intent intentSmsService=new Intent(context,MySmsService.class);
        intentSmsService.putExtras(intent);
        intentSmsService.setAction(intent.getAction());
        context.startService(intentSmsService);
    }
}
