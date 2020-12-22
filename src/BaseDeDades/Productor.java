package BaseDeDades;

public class Productor {
    private String  nif;
    private String nomproductor;

    public Productor(String nif, String nomproductor) {
        this.nif = nif;
        this.nomproductor = nomproductor;
    }


    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNomproductor() {
        return nomproductor;
    }

    public void setNomproductor(String nomproductor) {
        this.nomproductor = nomproductor;
    }

}
