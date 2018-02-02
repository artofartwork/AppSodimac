package net.sodimac.spring.model;

import java.util.Date;


public class Cliente {

	int idcliente;
	Documento idtipodoc;
	String numdoc;
	String apaterno;
	String amaterno;
	String nombres;
	String tlf;
	Ubigeo idubigeo;
	String direccion;
	String email;
	EstadoRegistro idestadoreg;
	Date FechaCreacion;
	double latitudCliente;
	double longitudCliente;
	
	
	public double getLatitudCliente() {
		return latitudCliente;
	}
	public void setLatitudCliente(double latitudCliente) {
		this.latitudCliente = latitudCliente;
	}
	public double getLongitudCliente() {
		return longitudCliente;
	}
	public void setLongitudCliente(double longitudCliente) {
		this.longitudCliente = longitudCliente;
	}
	public int getIdcliente() {
		return idcliente;
	}
	public void setIdcliente(int idcliente) {
		this.idcliente = idcliente;
	}
	public Documento getIdtipodoc() {
		return idtipodoc;
	}
	public void setIdtipodoc(Documento idtipodoc) {
		this.idtipodoc = idtipodoc;
	}
	public String getNumdoc() {
		return numdoc;
	}
	public void setNumdoc(String numdoc) {
		this.numdoc = numdoc;
	}
	public String getApaterno() {
		return apaterno;
	}
	public void setApaterno(String apaterno) {
		this.apaterno = apaterno;
	}
	public String getAmaterno() {
		return amaterno;
	}
	public void setAmaterno(String amaterno) {
		this.amaterno = amaterno;
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
	public Ubigeo getIdubigeo() {
		return idubigeo;
	}
	public void setIdubigeo(Ubigeo idubigeo) {
		this.idubigeo = idubigeo;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public EstadoRegistro getIdestadoreg() {
		return idestadoreg;
	}
	public void setIdestadoreg(EstadoRegistro idestadoreg) {
		this.idestadoreg = idestadoreg;
	}
	public Date getFechaCreacion() {
		return FechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		FechaCreacion = fechaCreacion;
	}
	public Cliente(int idcliente, Documento idtipodoc, String numdoc, String apaterno, String amaterno, String nombres,
			String tlf, Ubigeo idubigeo, String direccion, String email, EstadoRegistro idestadoreg, Date fechaCreacion) {
		super();
		this.idcliente = idcliente;
		this.idtipodoc = idtipodoc;
		this.numdoc = numdoc;
		this.apaterno = apaterno;
		this.amaterno = amaterno;
		this.nombres = nombres;
		this.tlf = tlf;
		this.idubigeo = idubigeo;
		this.direccion = direccion;
		this.email = email;
		this.idestadoreg = idestadoreg;
		FechaCreacion = fechaCreacion;
	}
	public Cliente() {
		super();
	}
	
	
	
	
}
