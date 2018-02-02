package net.sodimac.spring.model;

public class Ubigeo {

	String idubigeo;
	String distrito;
	String departamento;
	String provincia;
	
	
	
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getIdubigeo() {
		return idubigeo;
	}
	public void setIdubigeo(String idubigeo) {
		this.idubigeo = idubigeo;
	}
	public String getDistrito() {
		return distrito;
	}
	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}
	public Ubigeo(String idubigeo, String distrito) {
		super();
		this.idubigeo = idubigeo;
		this.distrito = distrito;
	}
	public Ubigeo() {
		super();
	}
	
	
}
