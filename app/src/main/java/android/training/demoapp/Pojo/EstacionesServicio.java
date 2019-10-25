
package android.training.demoapp.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EstacionesServicio {
    @SerializedName("Fecha")
    private String fecha;
    @SerializedName("ListaEESSPrecio")
    private List<ListaEESSPrecio> listaEESSPrecio;

    public EstacionesServicio(String fecha, List<ListaEESSPrecio> listaEESSPrecio) {
        this.fecha = fecha;
        this.listaEESSPrecio = listaEESSPrecio;
    }


    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<ListaEESSPrecio> getListaEESSPrecio() {
        return listaEESSPrecio;
    }

    public void setListaEESSPrecio(List<ListaEESSPrecio> listaEESSPrecio) {
        this.listaEESSPrecio = listaEESSPrecio;
    }
}
