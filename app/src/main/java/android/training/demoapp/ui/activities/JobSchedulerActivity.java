package android.training.demoapp.ui.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.os.Bundle;
import android.training.demoapp.R;
import android.training.demoapp.services.NotificationJobService;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerActivity extends AppCompatActivity {

    private JobScheduler jobScheduler;
    private static final int JOB_ID = 0;

    private Switch mDeviceIdleSwitch;
    private Switch mDeviceChargingSwitch;

    private SeekBar mSeekBar;

//https://codelabs.developers.google.com/codelabs/android-training-job-scheduler/index.html?index=..%2F..%2Fandroid-training#3

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_scheduler);

        mDeviceIdleSwitch = findViewById(R.id.idleSwitch);
        mDeviceChargingSwitch = findViewById(R.id.chargingSwitch);

        mSeekBar = findViewById(R.id.seekBar);
        final TextView seekBarProgress = findViewById(R.id.seekBarProgress);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress > 0){
                    seekBarProgress.setText(progress + " s");
                }else {
                    seekBarProgress.setText("Not Set");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }




    public void scheduleJob(View v) {

        int seekBarInteger = mSeekBar.getProgress();
        boolean seekBarSet = seekBarInteger > 0;

        //ID por y guárdelo en una variable de instancia llamada
        RadioGroup networkOptions = findViewById(R.id.networkOptions);

        //obtenga la ID de red seleccionada y guárdela en una variable entera
        int selectedNetworkID = networkOptions.getCheckedRadioButtonId();

        //cree una variable entera para la opción de red seleccionada
        int selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;

        //asigne a la opción de red seleccionada la JobInfoconstante de red apropiada
        switch (selectedNetworkID) {
            case R.id.noNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;
                break;
            case R.id.anyNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_ANY;
                break;
            case R.id.wifiNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_UNMETERED;
                break;
        }
        // El primer parámetro es el JOB_ID. El segundo parámetro es ComponentNamepara el JobServiceque creó.
        // El ComponentNamese utiliza para asociar el JobServicecon el JobInfoobjeto.
        ComponentName serviceName = new ComponentName(getPackageName(),
                NotificationJobService.class.getName());
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, serviceName).setRequiredNetworkType(selectedNetworkOption);


        if (seekBarSet) {
            builder.setOverrideDeadline(seekBarInteger * 1000);
        }


        //Dentro del scheduleJob()método, use getSystemService()para inicializar mScheduler.
        jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);


        boolean constraintSet =
                (selectedNetworkOption != JobInfo.NETWORK_TYPE_NONE)
                        || mDeviceChargingSwitch.isChecked()
                        || mDeviceIdleSwitch.isChecked()
                        || seekBarSet;

        //Verificar restricciones
        if(constraintSet) {
            //Schedule the job and notify the user
            JobInfo myJobInfo = builder.build();
            jobScheduler.schedule(myJobInfo);
            Toast.makeText(this, "Trabajo programado, el trabajo se ejecutará cuando " +
                    "se cumplen las restricciones.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Establezca al menos una restricción",
                    Toast.LENGTH_SHORT).show();
        }


    }


    public void cancelJobs(View view) {
        if (jobScheduler != null) {
            jobScheduler.cancelAll();
            jobScheduler = null;
            Toast.makeText(this, R.string.jobs_canceled, Toast.LENGTH_SHORT)
                    .show();
        }
    }


}
