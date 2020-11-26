package android.training.demoapp.services;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.training.demoapp.R;
import android.training.demoapp.ui.activities.HomeActivity;
import android.training.demoapp.ui.activities.JobSchedulerActivity;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MiIntentService extends IntentService {

    private NotificationManager notificationManager;
    private static final String PRIMARY_CHANNEL_ID ="job_scheduler_notification_channel";

    //asociarle un nombre de acción determinada
    public static final String  ACTION_PROGRESO = "demoapp.intent.action.PROGRESO";
    public static final String  ACTION_FIN = "demoapp.intent.action.FIN";

    public MiIntentService(){
        super("MiIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        createNotificationChannel();

            int iter = intent.getIntExtra("iteraciones", 0);

            for(int i=1; i<=iter; i++) {
                tareaLarga();

                //Comunicamos el progreso
                Intent bcIntent = new Intent();
                bcIntent.setAction(ACTION_PROGRESO);
                bcIntent.putExtra("progreso", i*10);
                sendBroadcast(bcIntent);

            }

            Intent bcIntent = new Intent();
            bcIntent.setAction(ACTION_FIN);
            sendBroadcast(bcIntent);

        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (this, 0, new Intent(this, JobSchedulerActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder
                (this, PRIMARY_CHANNEL_ID)
                .setContentTitle("IntentService")
                .setContentText("Finalizacion")
                .setContentIntent(contentPendingIntent)
                .setSmallIcon(R.drawable.ic_job_running)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);

        notificationManager.notify(0, builder.build());

        }

        private void tareaLarga(){
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }

    public void createNotificationChannel() {
        notificationManager =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "Job Service notification",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription
                    ("Notifications from Job Service");

            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
