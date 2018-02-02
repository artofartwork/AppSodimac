package net.sodimac.spring.model;

import java.util.Date;

public class Usuario {

	String idusuario;
	String nomusuario;
	String clave;
	int idperfil;
	int idpersonal;
	Cliente idcliente;
	Afiliado idafiliado;
	String idusiariocreacion;
	Date fechacreacion;
	String idusuariomodificacion;
	Date fechamodificacion;
	EstadoRegistro idestadoregistro;
	public String getIdusuario() {
		return idusuario;
	}
	public void setIdusuario(String idusuario) {
		this.idusuario = idusuario;
	}
	public String getNomusuario() {
		return nomusuario;
	}
	public void setNomusuario(String nomusuario) {
		this.nomusuario = nomusuario;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public int getIdperfil() {
		return idperfil;
	}
	public void setIdperfil(int idperfil) {
		this.idperfil = idperfil;
	}
	public int getIdpersonal() {
		return idpersonal;
	}
	public void setIdpersonal(int idpersonal) {
		this.idpersonal = idpersonal;
	}
	public Cliente getIdcliente() {
		return idcliente;
	}
	public void setIdcliente(Cliente idcliente) {
		this.idcliente = idcliente;
	}
	public Afiliado getIdafiliado() {
		return idafiliado;
	}
	public void setIdafiliado(Afiliado idafiliado) {
		this.idafiliado = idafiliado;
	}
	public String getIdusiariocreacion() {
		return idusiariocreacion;
	}
	public void setIdusiariocreacion(String idusiariocreacion) {
		this.idusiariocreacion = idusiariocreacion;
	}
	public Date getFechacreacion() {
		return fechacreacion;
	}
	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
	}
	public String getIdusuariomodificacion() {
		return idusuariomodificacion;
	}
	public void setIdusuariomodificacion(String idusuariomodificacion) {
		this.idusuariomodificacion = idusuariomodificacion;
	}
	public Date getFechamodificacion() {
		return fechamodificacion;
	}
	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}
	public EstadoRegistro getIdestadoregistro() {
		return idestadoregistro;
	}
	public void setIdestadoregistro(EstadoRegistro idestadoregistro) {
		this.idestadoregistro = idestadoregistro;
	}
	public Usuario(String idusuario, String nomusuario, String clave, int idperfil, int idpersonal, Cliente idcliente,
			Afiliado idafiliado, String idusiariocreacion, Date fechacreacion, String idusuariomodificacion,
			Date fechamodificacion, EstadoRegistro idestadoregistro) {
		super();
		this.idusuario = idusuario;
		this.nomusuario = nomusuario;
		this.clave = clave;
		this.idperfil = idperfil;
		this.idpersonal = idpersonal;
		this.idcliente = idcliente;
		this.idafiliado = idafiliado;
		this.idusiariocreacion = idusiariocreacion;
		this.fechacreacion = fechacreacion;
		this.idusuariomodificacion = idusuariomodificacion;
		this.fechamodificacion = fechamodificacion;
		this.idestadoregistro = idestadoregistro;
	}
	public Usuario() {
		
	}
	
	
	
	
}
