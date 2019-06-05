package com.android2ee.formation.intra.ontomantics.smslistener;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;

public class MySmsService extends Service {
    private static final String tag = "MySmsService";
    private static final String SMS_RECEIVE_INTENT_NAME = "android.provider.Telephony.SMS_RECEIVED";
    public static final String ACTION = "action";
    private int UniqueNotificationId=191114;
    private int notifNum=0;

    private PendingIntent pdIntent=null,pdIntentAction=null;
    public MySmsService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(tag, "Mon service a reçu un sms");
        if(pdIntent==null){
            pdIntent=PendingIntent.getActivity(this,0,new Intent(this,MainActivity.class),0);
            Intent intentAction=new Intent(this,MainActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString(ACTION, "Loved");
            intentAction.putExtras(bundle);
            pdIntentAction=PendingIntent.getActivity(this,1,intentAction,0);
        }
        //analyse intent
        analyseIntentToExtractSmsData(intent);
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }


    private void analyseIntentToExtractSmsData(Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVE_INTENT_NAME)) {
            //Retrieve the bundle that handles the Messages
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                //Retrieve the data store in the SMS
                Object[] pdus = (Object[]) bundle.get("pdus");
                //Declare the associated SMS Messages
                SmsMessage[] smsMessages = new SmsMessage[pdus.length];
                //Rebuild your SMS Messages
                for (int i = 0; i < pdus.length; i++) {
                    smsMessages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                //Parse your SMS Message
                SmsMessage currentMessage;
                String body, from;
                long when;
                for (int i = 0; i < smsMessages.length; i++) {
                    currentMessage = smsMessages[i];
                    body = currentMessage.getDisplayMessageBody();
                    from = currentMessage.getDisplayOriginatingAddress();
                    when = currentMessage.getTimestampMillis();
                    makeNotification(body, from, when);
                    notifNum++;
                }
            }
        }
    }

    /**
     * Display the notification
     */
    private void makeNotification(String body,String from,long when){
        String dude = getName(from);
        //adding a sound
        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setAutoCancel(true)
                .setContentIntent(pdIntent)
                .setContentText(body)
                .setContentTitle(dude)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_notif_largeicon))


                .setSound(soundUri)//set the sound
                .addAction(R.drawable.ic_notif_actionlove, "Love", pdIntentAction)//add an action
                .setOngoing(false)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.mipmap.ic_notif_small)
                .setSubText("tel : " + from)
                .setTicker("You received a new SMS from " + from)
                .setVibrate(new long[] { 100, 200, 100, 200, 100 }) // don't work
                .setWhen(System.currentTimeMillis())
                .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(body)
                                .setSummaryText("*tel : " + from)
                                .setBigContentTitle(":)"+dude)
                );

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(UniqueNotificationId+notifNum, builder.build());
    }

    public String getName(String from) {
        String contact = "unknow";
        // Android 2.0 and later
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(from));
        // Query the filter URI
        String[] projection = new String[] { ContactsContract.PhoneLookup.DISPLAY_NAME };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
        while (cursor.moveToNext()) {
            contact = cursor.getString(nameFieldColumnIndex);
        }
        cursor.close();
        return contact;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Not destinated to be bound
        return null;
    }
}
