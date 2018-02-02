package net.sodimac.spring.inter;

import java.sql.SQLException;
import java.util.List;
import net.sodimac.spring.model.Cliente;

public interface ISolicitud {

	
	public String insertarSolicitudInicial(String correo,int idafiliado, int idoficio) throws SQLException;
	public int consultarSolicitudAceptada(String correo, int idafiliado,int idoficio) throws SQLException;
	public String aceptarSolicitud(int idcliente,int idafiliado);
	public int consultarSolicitudPuntuar(int idsolicitud) throws SQLException;
	List<Cliente> consultarSolicitudInicial(String correo) throws SQLException;
	public String pasarAPuntuar(int idafiliado,int idoficio);
	public String insertarPuntuaciones(int idsolicitud,int a, int b, int c, String observaciones);
}


