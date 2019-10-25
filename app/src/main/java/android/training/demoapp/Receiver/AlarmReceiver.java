package android.training.demoapp.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.training.demoapp.activities.ImplicitIntentNotifi;
import android.training.demoapp.R;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    private static final int ALARM_ID = 1;
    private static final String ALARM_CHANNEL_ID =
            "alarm_notification_channel";
    private NotificationManager notificationManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        deliverNotification(context);
    }

    private void deliverNotification(Context c){
        Intent i = new Intent(c, ImplicitIntentNotifi.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(c,ALARM_ID,i, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(c, ALARM_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_ajustes)
                .setContentTitle("Titulo Alarma")
                .setContentText("Contenido alarma cualquier icono que considere apropiado!")
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        notificationManager.notify(ALARM_ID, builder.build());
    }
}
