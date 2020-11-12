
package android.training.demoapp.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//Eliminate findViewById calls by using @BindView on fields.
//https://github.com/JakeWharton/butterknife

@Entity(tableName = "estaciones_provincia_tabla")
public class ListaEESSPrecio {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @SerializedName("C.P.")
    @Expose
    private String cp;

    @SerializedName("Dirección")
    @Expose
    private String direcciN;

    @SerializedName("Horario")
    @Expose
    private String horario;

    @SerializedName("Latitud")
    @Expose
    private String latitud;

    @SerializedName("Localidad")
    @Expose
    private String localidad;

    @SerializedName("Longitud (WGS84)")
    @Expose
    private String longitudWGS84;

    @SerializedName("Margen")
    @Expose
    private String margen;

    @SerializedName("Municipio")
    @Expose
    private String municipio;

    @SerializedName("Precio Biodiesel")
    @Expose
    private String precioBiodiesel;

    @SerializedName("Precio Bioetanol")
    @Expose
    private String precioBioetanol;

    @SerializedName("Precio Gas Natural Comprimido")
    @Expose
    private String precioGasNaturalComprimido;

    @SerializedName("Precio Gas Natural Licuado")
    @Expose
    private String precioGasNaturalLicuado;

    @SerializedName("Precio Gases licuados del petróleo")
    @Expose
    private String precioGasesLicuadosDelPetrLeo;


    @SerializedName("Precio Gasoleo A")
    @Expose
    private String precioGasoleoA;

    @SerializedName("Precio Gasoleo B")
    @Expose
    private String precioGasoleoB;

    @SerializedName("Precio Gasolina 95 E5")
    @ColumnInfo (name = "gasolina_name")
    @Expose
    private String precioGasolina95ProtecciN;

    @SerializedName("Precio Gasolina  98")
    @Expose
    private String precioGasolina98;

    @SerializedName("Precio Nuevo Gasoleo A")
    @Expose
    private String precioNuevoGasoleoA;

    @SerializedName("Provincia")
    @Expose
    private String provincia;

    @SerializedName("Remisión")
    @Expose
    private String remisiN;

    @SerializedName("Rótulo")
    @Expose
    private String rotulo;

    @SerializedName("Tipo Venta")
    @Expose
    private String tipoVenta;

    @SerializedName("% BioEtanol")
    @Expose
    private String bioEtanol;

    @SerializedName("% Éster metílico")
    @Expose
    private String sterMetLico;

    @SerializedName("IDEESS")
    @Expose
    private String ideess;

    @SerializedName("IDMunicipio")
    @Expose
    private String idmunicipio;

    @SerializedName("IDProvincia")
    @Expose
    private String idprovincia;

    @SerializedName("IDCCAA")
    @Expose
    private String idccaa;

    private Float distancia;
    /**
     * No args constructor for use in serialization
     */
    public ListaEESSPrecio() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getDirecciN() {
        return direcciN;
    }

    public void setDirecciN(String direcciN) {
        this.direcciN = direcciN;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getLongitudWGS84() {
        return longitudWGS84;
    }

    public void setLongitudWGS84(String longitudWGS84) {
        this.longitudWGS84 = longitudWGS84;
    }

    public String getMargen() {
        return margen;
    }

    public void setMargen(String margen) {
        this.margen = margen;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getPrecioBiodiesel() {
        return precioBiodiesel;
    }

    public void setPrecioBiodiesel(String precioBiodiesel) {
        this.precioBiodiesel = precioBiodiesel;
    }

    public String getPrecioBioetanol() {
        return precioBioetanol;
    }

    public void setPrecioBioetanol(String precioBioetanol) {
        this.precioBioetanol = precioBioetanol;
    }

    public String getPrecioGasNaturalComprimido() {
        return precioGasNaturalComprimido;
    }

    public void setPrecioGasNaturalComprimido(String precioGasNaturalComprimido) {
        this.precioGasNaturalComprimido = precioGasNaturalComprimido;
    }

    public String getPrecioGasNaturalLicuado() {
        return precioGasNaturalLicuado;
    }

    public void setPrecioGasNaturalLicuado(String precioGasNaturalLicuado) {
        this.precioGasNaturalLicuado = precioGasNaturalLicuado;
    }

    public String getPrecioGasesLicuadosDelPetrLeo() {
        return precioGasesLicuadosDelPetrLeo;
    }

    public void setPrecioGasesLicuadosDelPetrLeo(String precioGasesLicuadosDelPetrLeo) {
        this.precioGasesLicuadosDelPetrLeo = precioGasesLicuadosDelPetrLeo;
    }

    public String getPrecioGasoleoA() {
        return precioGasoleoA;
    }

    public void setPrecioGasoleoA(String precioGasoleoA) {
        this.precioGasoleoA = precioGasoleoA;
    }

    public String getPrecioGasoleoB() {
        return precioGasoleoB;
    }

    public void setPrecioGasoleoB(String precioGasoleoB) {
        this.precioGasoleoB = precioGasoleoB;
    }

    public String getPrecioGasolina95ProtecciN() {
        return precioGasolina95ProtecciN;
    }

    public void setPrecioGasolina95ProtecciN(String precioGasolina95ProtecciN) {
        this.precioGasolina95ProtecciN = precioGasolina95ProtecciN;
    }

    public String getPrecioGasolina98() {
        return precioGasolina98;
    }

    public void setPrecioGasolina98(String precioGasolina98) {
        this.precioGasolina98 = precioGasolina98;
    }

    public String getPrecioNuevoGasoleoA() {
        return precioNuevoGasoleoA;
    }

    public void setPrecioNuevoGasoleoA(String precioNuevoGasoleoA) {
        this.precioNuevoGasoleoA = precioNuevoGasoleoA;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getRemisiN() {
        return remisiN;
    }

    public void setRemisiN(String remisiN) {
        this.remisiN = remisiN;
    }

    public String getRotulo() {
        return rotulo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public String getBioEtanol() {
        return bioEtanol;
    }

    public void setBioEtanol(String bioEtanol) {
        this.bioEtanol = bioEtanol;
    }

    public String getSterMetLico() {
        return sterMetLico;
    }

    public void setSterMetLico(String sterMetLico) {
        this.sterMetLico = sterMetLico;
    }

    public String getIdeess() {
        return ideess;
    }

    public void setIdeess(String ideess) {
        this.ideess = ideess;
    }

    public String getIdmunicipio() {
        return idmunicipio;
    }

    public void setIdmunicipio(String idmunicipio) {
        this.idmunicipio = idmunicipio;
    }

    public String getIdprovincia() {
        return idprovincia;
    }

    public void setIdprovincia(String idprovincia) {
        this.idprovincia = idprovincia;
    }

    public String getIdccaa() {
        return idccaa;
    }

    public void setIdccaa(String idccaa) {
        this.idccaa = idccaa;
    }

    public Float getDistancia() {
        return distancia;
    }

    public void setDistancia(Float distancia) {
        this.distancia = distancia;
    }
}