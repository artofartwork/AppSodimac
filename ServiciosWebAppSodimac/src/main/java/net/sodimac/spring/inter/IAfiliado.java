package net.sodimac.spring.inter;

import java.sql.SQLException;
import java.util.List;

import net.sodimac.spring.model.Afiliado;
import net.sodimac.spring.model.Oficio;

public interface IAfiliado {

	public int iniciarSesion(String email,String clave);
	public List<Afiliado> obtenerDatosAfiliado(int ocupacion) throws SQLException;
	public String actualizarUbicacion(double latitud, double longitud,String correo);
	public List<Afiliado> obtenerDatosAfiliadoCorreo(String correo) throws SQLException;
}
