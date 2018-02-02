package sodimac.net.appsodimac.model;

public class EstadoSolicitud {
int idestadosolicitud;
String estadoSolicitud;
EstadoRegistro estadoRegistro;
public int getIdestadosolicitud() {
	return idestadosolicitud;
}
public void setIdestadosolicitud(int idestadosolicitud) {
	this.idestadosolicitud = idestadosolicitud;
}
public String getEstadoSolicitud() {
	return estadoSolicitud;
}
public void setEstadoSolicitud(String estadoSolicitud) {
	this.estadoSolicitud = estadoSolicitud;
}
public EstadoRegistro getEstadoRegistro() {
	return estadoRegistro;
}
public void setEstadoRegistro(EstadoRegistro estadoRegistro) {
	this.estadoRegistro = estadoRegistro;
}
public EstadoSolicitud(int idestadosolicitud, String estadoSolicitud, EstadoRegistro estadoRegistro) {
	super();
	this.idestadosolicitud = idestadosolicitud;
	this.estadoSolicitud = estadoSolicitud;
	this.estadoRegistro = estadoRegistro;
}
public EstadoSolicitud() {
	super();
}



}
