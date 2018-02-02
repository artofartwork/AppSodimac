package net.sodimac.spring.inter;


import java.sql.SQLException;
import java.util.List;

import net.sodimac.spring.model.Cliente;
import net.sodimac.spring.model.Usuario;

public interface IClienteDAO {

	
	public List<Cliente> ObtenerCliente(String correo) throws SQLException;
	public List<Cliente> obtenerclienteInicial(int idcliente) throws SQLException;
	public String insertarCliente(Usuario cliente);
	public int iniciarSesion(String email,String clave);
	public String actualizarUbicacion(double latitud, double longitud,String correo);
}
