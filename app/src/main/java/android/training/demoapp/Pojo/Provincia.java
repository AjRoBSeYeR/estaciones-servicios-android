
package android.training.demoapp.pojo;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "provincia_tabla")
public class Provincia {

    @SerializedName("IDPovincia")
    @Expose
    @PrimaryKey
    @NonNull
    private String iDPovincia;
    @SerializedName("IDCCAA")
    @Expose
    private String iDCCAA;
    @SerializedName("Provincia")
    @Expose
    private String provincia;
    @SerializedName("CCAA")
    @Expose
    private String cCAA;

    /**
     * No args constructor for use in serialization
     *
     */


    /**
     *
     * @param cCAA
     * @param iDPovincia
     * @param iDCCAA
     * @param provincia
     */
    @Ignore
    public Provincia(String iDPovincia, String iDCCAA, String provincia, String cCAA) {
        super();
        this.iDPovincia = iDPovincia;
        this.iDCCAA = iDCCAA;
        this.provincia = provincia;
        this.cCAA = cCAA;
    }
    public Provincia(){}
    public String getIDPovincia() {
        return iDPovincia;
    }

    public void setIDPovincia(String iDPovincia) {
        this.iDPovincia = iDPovincia;
    }

    public String getIDCCAA() {
        return iDCCAA;
    }

    public void setIDCCAA(String iDCCAA) {
        this.iDCCAA = iDCCAA;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCCAA() {
        return cCAA;
    }

    public void setCCAA(String cCAA) {
        this.cCAA = cCAA;
    }

}
