package android.training.demoapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.training.demoapp.Pojo.Registro;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AgregarRegistroActivity extends AppCompatActivity {

    private EditText fecha;
    private EditText euros;
    private EditText litros;
    private EditText kmTotales;

    private DatePickerDialog picker;
    private Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_registro);

        final Button button = findViewById(R.id.button_save);


        fecha = findViewById(R.id.fecha);
        fecha.setInputType(InputType.TYPE_NULL);


        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                fecha.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                //fecha = findViewById(R.id.fecha);
                euros = findViewById(R.id.euros);
                litros = findViewById(R.id.litros);
                kmTotales = findViewById(R.id.kmTotales);
                Registro registro = new Registro();


                String sDate1=fecha.getText().toString();

                Date date1= null;
                try {
                    date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                System.out.println(sDate1+"\t"+date1);

                registro.setFecha(date1);

                registro.setEuros(Double.parseDouble(euros.getText().toString()));
                registro.setKmTotales(Double.parseDouble(kmTotales.getText().toString()));
                registro.setLitros(Double.parseDouble(litros.getText().toString()));

                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(fecha.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    replyIntent.putExtra("registro",  registro);
                    replyIntent.putExtra("fecha",  sDate1);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

    //MENU CERRAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.cerrar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.atras:
                finish();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

