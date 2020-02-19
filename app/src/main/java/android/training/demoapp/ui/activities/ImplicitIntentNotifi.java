package android.training.demoapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.ShareCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.training.demoapp.R;
import android.training.demoapp.ui.receiver.AlarmReceiver;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import java.util.List;

public class ImplicitIntentNotifi extends AppCompatActivity {

    private static final String LOG_TAG = ImplicitIntentNotifi.class.getSimpleName();
    private static final String CANAL_ID_PRIMARIO = "canal_notificacion_primario";
    private static final int NOTIFICATION_ID = 0;

    private static final int ALARM_ID = 1;
    private static final String ALARM_CHANNEL_ID =
            "alarm_notification_channel";

    private static final String ACTION_UPDATE_NOTIFICATION ="android.training.demoapp.ACTION_UPDATE_NOTIFICATION";

    private EditText et_web;
    private EditText et_location;
    private EditText et_share;

    private Button btn_share;
    private Button btn_location;
    private Button btn_web;

    private Button notificarme;
    private Button update_notificarme;
    private Button cancelar_notificarme;

    private NotificationManager notificationManager;
    private NotificationReceiver notificationReceiver;

    private ToggleButton btnAlarmToggle;

    private Context context;
    private AlarmManager alarmManager;
private PendingIntent contentPendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_implicit_intent);

        context = this;



        et_web = findViewById(R.id.et_web);
        et_location = findViewById(R.id.et_location);
        et_share = findViewById(R.id.et_share);
        btn_share = findViewById(R.id.btn_share);
        btn_location = findViewById(R.id.btn_location);
        btn_web = findViewById(R.id.btn_web);
        btnAlarmToggle  = findViewById(R.id.btnAlarmToggle);


        notificarme = findViewById(R.id.notificarme);
        update_notificarme = findViewById(R.id.update_notificarme);
        cancelar_notificarme = findViewById(R.id.cancelar_notificarme);

        crearCanalNotificacion();
//        registerReceiver(notificationReceiver, new IntentFilter(ACTION_UPDATE_NOTIFICATION));



        notificationReceiver = new NotificationReceiver();

        // Register the broadcast receiver to receive the update action from the notification.
        registerReceiver(notificationReceiver, new IntentFilter(ACTION_UPDATE_NOTIFICATION));

        notificarme.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                enviarNotificaciones();
            }
        });

        update_notificarme.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                updateNotification();
            }
        });

        cancelar_notificarme.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                cancelNotification();
            }
        });


        notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);



        Intent notifyIntent = new Intent(context, AlarmReceiver.class);

        boolean alarmUp = (PendingIntent.getBroadcast(this, ALARM_ID,
                notifyIntent, PendingIntent.FLAG_NO_CREATE) != null);
        btnAlarmToggle.setChecked(alarmUp);

        contentPendingIntent = PendingIntent.getActivity(context,ALARM_ID,notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                (this, ALARM_ID, notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);



        btnAlarmToggle.setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged
                            (CompoundButton buttonView, boolean isChecked) {
                        String toastMessage;
                        if (isChecked) {

                            long repeatInterval =1 * 60 * 1000;

                            long triggerTime = SystemClock.elapsedRealtime() + repeatInterval;

                            // If the Toggle is turned on, set the repeating alarm with
                            // a 15 minute interval.
                            if (alarmManager != null) {
                                alarmManager.setInexactRepeating
                                        (AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                                triggerTime, repeatInterval,
                                                notifyPendingIntent);
                            }
                            // Set the toast message for the "on" case.
                            //toastMessage = getString(R.string.alarm_on_toast);

                        } else {
                            // Cancel notification if the alarm is turned off.
                            notificationManager.cancelAll();

                            if (alarmManager != null) {
                                alarmManager.cancel(notifyPendingIntent);
                            }
                            // Set the toast message for the "off" case.
                           // toastMessage = getString(R.string.alarm_off_toast);

                        }

                        // Show a toast to say the alarm is turned on or off.
                      //  Toast.makeText(MainActivity.this, toastMessage,Toast.LENGTH_SHORT).show();
                    }
                });


        crearCanalAlarmaNotificacion();
    }





    @Override
    protected void onDestroy() {
        unregisterReceiver(notificationReceiver);
        super.onDestroy();
    }





    public  void updateNotification() {
        Bitmap androidImage = BitmapFactory.decodeResource(getResources(), R.drawable.icon);

        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();


        notifyBuilder.setStyle(
                new NotificationCompat.BigPictureStyle()
                        .bigPicture(androidImage)
                .setBigContentTitle(getString(R.string.update_notificarme))
        );

        notificationManager.notify(NOTIFICATION_ID, notifyBuilder.build());

        setNotificationButtonState(false, false, true);
    }

    void setNotificationButtonState(Boolean isNotifyEnabled, Boolean isUpdateEnabled, Boolean isCancelEnabled) {
        notificarme.setEnabled(isNotifyEnabled);
        update_notificarme.setEnabled(isUpdateEnabled);
        cancelar_notificarme.setEnabled(isCancelEnabled);
    }

    public void cancelNotification() {
        notificationManager.cancel(NOTIFICATION_ID);
        setNotificationButtonState(true, false, false);

    }

    private void enviarNotificaciones(){

        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(this,
                NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

        notifyBuilder.addAction(R.drawable.ic_refresh, getString(R.string.update_notificarme), updatePendingIntent);

        notificationManager.notify(NOTIFICATION_ID, notifyBuilder.build());

        setNotificationButtonState(false, true, true);


    }

    private NotificationCompat.Builder getNotificationBuilder(){

        Intent notificationIntent = new Intent(this, ImplicitIntentNotifi.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, CANAL_ID_PRIMARIO)
                .setContentTitle("Notificacion titulo")
                .setContentText("Contenido notificacion bla bla bla")
                .setSmallIcon(R.drawable.ic_gas)
                .setContentIntent(notificationPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);
        return notifyBuilder;

    }

    public void crearCanalAlarmaNotificacion() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    new NotificationChannel(
                            ALARM_CHANNEL_ID,
                            "Alarma Notificacion",
                            NotificationManager.IMPORTANCE_HIGH
                    );

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("notificacion de alarma");
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }




    public void crearCanalNotificacion() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    new NotificationChannel(
                            CANAL_ID_PRIMARIO,
                            "Noticacion ejemplo",
                            NotificationManager.IMPORTANCE_HIGH
                    );

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Noticacion de ejemplo");
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }


    public class NotificationReceiver extends BroadcastReceiver {
        public NotificationReceiver() {}
        @Override
        public void onReceive(Context context, Intent intent) {
            updateNotification();
        }
    }





    public void abrirWeb(View v){
        String url = et_web.getText().toString();
        Uri uri = Uri.parse(url);
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        String title = getResources().getString(R.string.share_text_with);
        Intent chooser = Intent.createChooser(i, title);
        if (i.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        } else {
            Log.d(LOG_TAG, "No se puede abrirWeb");
        }


//        Intent intent = getIntent();
//        Uri uri = intent.getData();
//        if (uri != null) {
//            String uri_string = getString(R.string.edittext_uri)
//                    + uri.toString();
//            TextView textView = findViewById(R.id.text_uri_message);
//            textView.setText(uri_string);
//        }
    }

    public void abrirLoc(View v){
        String loc = et_location.getText().toString();

        Uri addressUri = Uri.parse("geo:0,0?q=" + loc);
        Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);

        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);

        boolean isIntentSafe = activities.size() > 0;

        if (isIntentSafe) {
            startActivity(intent);
            Log.d(LOG_TAG, "No se puede abrirLoc");
        }
    }

    public void compartir(View view) {
        String txt = et_share.getText().toString();
        String mimeType = "text/plain";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle(R.string.share_text_with)
                .setText(txt)
                .startChooser();
    }
}
