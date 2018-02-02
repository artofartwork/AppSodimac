package sodimac.net.appsodimac.model;

public class Puntuacion {
int idpuntuacion;
String puntuacion;
EstadoRegistro idestadoRegistro;




public Puntuacion() {
	super();
}
public Puntuacion(int idpuntuacion, String puntuacion, EstadoRegistro idestadoRegistro) {
	super();
	this.idpuntuacion = idpuntuacion;
	this.puntuacion = puntuacion;
	this.idestadoRegistro = idestadoRegistro;
}
public int getIdpuntuacion() {
	return idpuntuacion;
}
public void setIdpuntuacion(int idpuntuacion) {
	this.idpuntuacion = idpuntuacion;
}
public String getPuntuacion() {
	return puntuacion;
}
public void setPuntuacion(String puntuacion) {
	this.puntuacion = puntuacion;
}
public EstadoRegistro getIdestadoRegistro() {
	return idestadoRegistro;
}
public void setIdestadoRegistro(EstadoRegistro idestadoRegistro) {
	this.idestadoRegistro = idestadoRegistro;
}


}
