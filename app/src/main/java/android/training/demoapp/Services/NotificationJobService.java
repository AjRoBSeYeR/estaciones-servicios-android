package android.training.demoapp.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.training.demoapp.ui.activities.JobSchedulerActivity;
import android.training.demoapp.R;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NotificationJobService extends JobService {

    private NotificationManager notificationManager;
    private static final String PRIMARY_CHANNEL_ID ="job_scheduler_notification_channel";

    @Override
    public boolean onStartJob(JobParameters params) {

        createNotificationChannel();

        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (this, 0, new Intent(this, JobSchedulerActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder
                (this, PRIMARY_CHANNEL_ID)
                .setContentTitle("Job Service")
                .setContentText("Su trabajo se ejecutó hasta su finalización!")
                .setContentIntent(contentPendingIntent)
                .setSmallIcon(R.drawable.ic_job_running)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);

        notificationManager.notify(0, builder.build());
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }


    public void createNotificationChannel() {
        notificationManager =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "Job Service notification", NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription
                    ("Notifications from Job Service");

            notificationManager.createNotificationChannel(notificationChannel);
        }
    }



}
