package net.sodimac.spring.model;

public class Oficio {

	int idoficio;
	String oficio;
	EstadoRegistro idestadoRegistro;
	public int getIdoficio() {
		return idoficio;
	}
	public void setIdoficio(int idoficio) {
		this.idoficio = idoficio;
	}
	public String getOficio() {
		return oficio;
	}
	public void setOficio(String oficio) {
		this.oficio = oficio;
	}
	public EstadoRegistro getIdestadoRegistro() {
		return idestadoRegistro;
	}
	public void setIdestadoRegistro(EstadoRegistro idestadoRegistro) {
		this.idestadoRegistro = idestadoRegistro;
	}
	public Oficio(int idoficio, String oficio, EstadoRegistro idestadoRegistro) {
		super();
		this.idoficio = idoficio;
		this.oficio = oficio;
		this.idestadoRegistro = idestadoRegistro;
	}
	public Oficio() {
		super();
	}
	
	
	
	
}
