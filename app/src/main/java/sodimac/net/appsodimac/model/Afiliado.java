package sodimac.net.appsodimac.model;

import java.io.File;
import java.util.Date;

/**
 * Created by Cuenta02 on 25/09/2017.
 */

public class Afiliado {

    private int idafiliado;
    private Documento tipodocumento;
    private String numdoc;
    private String apat;
    private String amat;
    private String nombres;
    private String tlf;
    private Ubigeo ubigeo;
    private String direccion;
    private String correo;
    private String foto;
    private File currVit;
    private File docIdnt;
    private File certPol;
    private int tipoafiliado;
    private Date fechaMod;
    private int idestadoRegistro;
    private double latitudafiliado;
    private double longitudafiliado;


    public int getIdafiliado() {
        return idafiliado;
    }

    public void setIdafiliado(int idafiliado) {
        this.idafiliado = idafiliado;
    }

    public Documento getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(Documento tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public String getNumdoc() {
        return numdoc;
    }

    public void setNumdoc(String numdoc) {
        this.numdoc = numdoc;
    }

    public String getApat() {
        return apat;
    }

    public void setApat(String apat) {
        this.apat = apat;
    }

    public String getAmat() {
        return amat;
    }

    public void setAmat(String amat) {
        this.amat = amat;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getTlf() {
        return tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    public Ubigeo getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(Ubigeo ubigeo) {
        this.ubigeo = ubigeo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public File getCurrVit() {
        return currVit;
    }

    public void setCurrVit(File currVit) {
        this.currVit = currVit;
    }

    public File getDocIdnt() {
        return docIdnt;
    }

    public void setDocIdnt(File docIdnt) {
        this.docIdnt = docIdnt;
    }

    public File getCertPol() {
        return certPol;
    }

    public void setCertPol(File certPol) {
        this.certPol = certPol;
    }

    public int getTipoafiliado() {
        return tipoafiliado;
    }

    public void setTipoafiliado(int tipoafiliado) {
        this.tipoafiliado = tipoafiliado;
    }

    public Date getFechaMod() {
        return fechaMod;
    }

    public void setFechaMod(Date fechaMod) {
        this.fechaMod = fechaMod;
    }

    public int getIdestadoRegistro() {
        return idestadoRegistro;
    }

    public void setIdestadoRegistro(int idestadoRegistro) {
        this.idestadoRegistro = idestadoRegistro;
    }

    public double getLatitudafiliado() {
        return latitudafiliado;
    }

    public void setLatitudafiliado(double latitudafiliado) {
        this.latitudafiliado = latitudafiliado;
    }

    public double getLongitudafiliado() {
        return longitudafiliado;
    }

    public void setLongitudafiliado(double longitudafiliado) {
        this.longitudafiliado = longitudafiliado;
    }
}
