package android.training.demoapp.pojo;

public class HomeItem {



    private Integer id;
    private  String titulo;
    private String descriptcion;

    public HomeItem(Integer id, String titulo, String descriptcion) {
        this.setId(id);
        this.setTitulo(titulo);
        this.setDescriptcion(descriptcion);
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescriptcion() {
        return descriptcion;
    }

    public void setDescriptcion(String descriptcion) {
        this.descriptcion = descriptcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
