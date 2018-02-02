package net.sodimac.spring.model;

public class Documento {

	int idtipodocumento;
	String tipodocumento;
	public int getIdtipodocumento() {
		return idtipodocumento;
	}
	public void setIdtipodocumento(int idtipodocumento) {
		this.idtipodocumento = idtipodocumento;
	}
	public String getTipodocumento() {
		return tipodocumento;
	}
	public void setTipodocumento(String tipodocumento) {
		this.tipodocumento = tipodocumento;
	}
	public Documento(int idtipodocumento, String tipodocumento) {
		super();
		this.idtipodocumento = idtipodocumento;
		this.tipodocumento = tipodocumento;
	}
	public Documento() {
		super();
	}
	
	
	
}
