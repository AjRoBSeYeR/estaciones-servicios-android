package android.training.demoapp.pojo;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
import android.training.demoapp.tools.DateConverter;

import androidx.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "registro_tabla")
public class Registro implements Parcelable {

    public Registro( ) {}
    @Ignore
    public Registro(Date fecha, double euros, double litros, double kmTotales) {

        this.fecha = fecha;
        this.euros = euros;
        this.litros = litros;
        this.kmTotales = kmTotales;
    }
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;
    @TypeConverters(DateConverter.class)
    private Date fecha;
    private double euros;
    private double litros;
    private double kmTotales;


    protected Registro(Parcel in) {
        String f=  String.valueOf(fecha);
        id = in.readInt();
        f = in.readString();
        euros = in.readDouble();
        litros = in.readDouble();
        kmTotales = in.readDouble();
    }


    public static final Creator<Registro> CREATOR = new Creator<Registro>() {
        @Override
        public Registro createFromParcel(Parcel in) {
            return new Registro(in);
        }

        @Override
        public Registro[] newArray(int size) {
            return new Registro[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getEuros() {
        return euros;
    }

    public void setEuros(double euros) {
        this.euros = euros;
    }

    public double getLitros() {
        return litros;
    }

    public void setLitros(double litros) {
        this.litros = litros;
    }

    public double getKmTotales() {
        return kmTotales;
    }

    public void setKmTotales(double kmTotales) {
        this.kmTotales = kmTotales;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(String.valueOf(fecha));
        dest.writeDouble(euros);
        dest.writeDouble(litros);
        dest.writeDouble(kmTotales);
    }
}

