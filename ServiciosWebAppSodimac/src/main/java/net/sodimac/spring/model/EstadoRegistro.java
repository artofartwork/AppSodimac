package net.sodimac.spring.model;

public class EstadoRegistro {
int idestadoregistro;
String estadoRegistro;
public int getIdestadoregistro() {
	return idestadoregistro;
}
public void setIdestadoregistro(int idestadoregistro) {
	this.idestadoregistro = idestadoregistro;
}
public String getEstadoRegistro() {
	return estadoRegistro;
}
public void setEstadoRegistro(String estadoRegistro) {
	this.estadoRegistro = estadoRegistro;
}
public EstadoRegistro(int idestadoregistro, String estadoRegistro) {
	super();
	this.idestadoregistro = idestadoregistro;
	this.estadoRegistro = estadoRegistro;
}
public EstadoRegistro() {
	super();
}

}
