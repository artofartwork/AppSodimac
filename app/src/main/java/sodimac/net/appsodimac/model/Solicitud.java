package sodimac.net.appsodimac.model;

import java.util.Date;

public class Solicitud {
int idsolicitud;
Cliente cliente;
Afiliado afiliado;
Oficio oficio;
Puntuacion puntualidad;
Puntuacion desempenio;
Puntuacion conocimiento;
String observacion;
EstadoSolicitud estadosolicitud;
Date fechaCreacion;
EstadoRegistro estadoRegistro;
public int getIdsolicitud() {
	return idsolicitud;
}
public void setIdsolicitud(int idsolicitud) {
	this.idsolicitud = idsolicitud;
}
public Cliente getCliente() {
	return cliente;
}
public void setCliente(Cliente cliente) {
	this.cliente = cliente;
}
public Afiliado getAfiliado() {
	return afiliado;
}
public void setAfiliado(Afiliado afiliado) {
	this.afiliado = afiliado;
}
public Oficio getOficio() {
	return oficio;
}
public void setOficio(Oficio oficio) {
	this.oficio = oficio;
}
public Puntuacion getPuntualidad() {
	return puntualidad;
}
public void setPuntualidad(Puntuacion puntualidad) {
	this.puntualidad = puntualidad;
}
public Puntuacion getDesempenio() {
	return desempenio;
}
public void setDesempenio(Puntuacion desempenio) {
	this.desempenio = desempenio;
}
public Puntuacion getConocimiento() {
	return conocimiento;
}
public void setConocimiento(Puntuacion conocimiento) {
	this.conocimiento = conocimiento;
}
public String getObservacion() {
	return observacion;
}
public void setObservacion(String observacion) {
	this.observacion = observacion;
}
public EstadoSolicitud getEstadosolicitud() {
	return estadosolicitud;
}
public void setEstadosolicitud(EstadoSolicitud estadosolicitud) {
	this.estadosolicitud = estadosolicitud;
}
public Date getFechaCreacion() {
	return fechaCreacion;
}
public void setFechaCreacion(Date fechaCreacion) {
	this.fechaCreacion = fechaCreacion;
}
public EstadoRegistro getEstadoRegistro() {
	return estadoRegistro;
}
public void setEstadoRegistro(EstadoRegistro estadoRegistro) {
	this.estadoRegistro = estadoRegistro;
}
public Solicitud(int idsolicitud, Cliente cliente, Afiliado afiliado, Oficio oficio, Puntuacion puntualidad,
		Puntuacion desempenio, Puntuacion conocimiento, String observacion, EstadoSolicitud estadosolicitud,
		Date fechaCreacion, EstadoRegistro estadoRegistro) {
	super();
	this.idsolicitud = idsolicitud;
	this.cliente = cliente;
	this.afiliado = afiliado;
	this.oficio = oficio;
	this.puntualidad = puntualidad;
	this.desempenio = desempenio;
	this.conocimiento = conocimiento;
	this.observacion = observacion;
	this.estadosolicitud = estadosolicitud;
	this.fechaCreacion = fechaCreacion;
	this.estadoRegistro = estadoRegistro;
}
public Solicitud() {
	super();
}





}
