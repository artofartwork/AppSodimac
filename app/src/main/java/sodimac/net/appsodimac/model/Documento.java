package sodimac.net.appsodimac.model;

/**
 * Created by Cuenta02 on 15/09/2017.
 */

public class Documento {

    private int idDocumento;
    private String tipoDocumento;

    public Documento(){}


    public int getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
}
