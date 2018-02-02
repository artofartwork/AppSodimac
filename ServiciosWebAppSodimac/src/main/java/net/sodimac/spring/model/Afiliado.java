package net.sodimac.spring.model;

import java.io.File;
import java.sql.Blob;
import java.util.Date;

public class Afiliado {
int idafiliado;
Documento tipoDocumento;
String numdoc;
String apat;
String amat;
String nombres;
String tlf;
Ubigeo ubigeo;
String direccion;
String correo;
String foto;
File currVit;
File docIdnt;
File certPol;
TipoAfiliado tipoafiliado;
Date fechaMod;
EstadoRegistro idestadoRegistro;
double latitudAfiliado;
double longitudAfiliado;


public double getLatitudAfiliado() {
	return latitudAfiliado;
}
public void setLatitudAfiliado(double latitudAfiliado) {
	this.latitudAfiliado = latitudAfiliado;
}
public double getLongitudAfiliado() {
	return longitudAfiliado;
}
public void setLongitudAfiliado(double longitudAfiliado) {
	this.longitudAfiliado = longitudAfiliado;
}
public int getIdafiliado() {
	return idafiliado;
}
public void setIdafiliado(int idafiliado) {
	this.idafiliado = idafiliado;
}
public Documento getTipoDocumento() {
	return tipoDocumento;
}
public void setTipoDocumento(Documento tipoDocumento) {
	this.tipoDocumento = tipoDocumento;
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
public void setFoto(String imageBase64) {
	this.foto = imageBase64;
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
public TipoAfiliado getTipoafiliado() {
	return tipoafiliado;
}
public void setTipoafiliado(TipoAfiliado tipoafiliado) {
	this.tipoafiliado = tipoafiliado;
}
public Date getFechaMod() {
	return fechaMod;
}
public void setFechaMod(Date fechaMod) {
	this.fechaMod = fechaMod;
}
public EstadoRegistro getIdestadoRegistro() {
	return idestadoRegistro;
}
public void setIdestadoRegistro(EstadoRegistro idestadoRegistro) {
	this.idestadoRegistro = idestadoRegistro;
}
public Afiliado(int idafiliado, Documento tipoDocumento, String numdoc, String apat, String amat, String nombres,
		String tlf, Ubigeo ubigeo, String direccion, String correo, String foto, File currVit, File docIdnt, File certPol,
		TipoAfiliado tipoafiliado, Date fechaMod, EstadoRegistro idestadoRegistro) {
	super();
	this.idafiliado = idafiliado;
	this.tipoDocumento = tipoDocumento;
	this.numdoc = numdoc;
	this.apat = apat;
	this.amat = amat;
	this.nombres = nombres;
	this.tlf = tlf;
	this.ubigeo = ubigeo;
	this.direccion = direccion;
	this.correo = correo;
	this.foto = foto;
	this.currVit = currVit;
	this.docIdnt = docIdnt;
	this.certPol = certPol;
	this.tipoafiliado = tipoafiliado;
	this.fechaMod = fechaMod;
	this.idestadoRegistro = idestadoRegistro;
}
public Afiliado() {
	super();
}




}
