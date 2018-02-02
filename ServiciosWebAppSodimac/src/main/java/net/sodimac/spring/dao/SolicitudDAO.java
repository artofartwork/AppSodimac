package net.sodimac.spring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;
import net.sodimac.spring.inter.ISolicitud;
import net.sodimac.spring.model.Cliente;
import net.sodimac.spring.util.BDConexion;

@Component
public class SolicitudDAO implements ISolicitud{
	BDConexion con = new BDConexion();
	ClienteDAO clientedao = new ClienteDAO();
	@Override
	public String insertarSolicitudInicial(String correo,int idafiliado, int idoficio) throws SQLException {
		 Connection co = con.getConexion();
		 String res = "";
		 int r = 0;
		 List<Cliente> listaCliente = clientedao.ObtenerCliente(correo);
		 Cliente cliente = listaCliente.get(0);
		 
		 try {
			 PreparedStatement s1 = co.prepareStatement("insert into Sodimac.GesSolicitud values (?,?,?,0,0,0,null,1,?,1)");
			s1.setInt(1, cliente.getIdcliente()); 
			s1.setInt(2, idafiliado);
			s1.setInt(3, idoficio);
			
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		      
			s1.setString(4,formatter.format(sqlDate));
			s1.execute();
            res = "INSERTADO";	
		} catch (Exception e) {
			e.printStackTrace();
			res = "ERROR " + e;
			
		}
		 
		 
		return res;
	}
	@Override
	public int consultarSolicitudAceptada(String correo, int idafiliado, int idoficio) throws SQLException {
		 Connection co = con.getConexion();
		 int res = 0;
		 List<Cliente> listaCliente = clientedao.ObtenerCliente(correo);
		 Cliente cliente = listaCliente.get(0);
		 try {
			 PreparedStatement s1 = co.prepareStatement("  select MAX(IdSolicitud) from Sodimac.GesSolicitud where IdEstadoSolicitud = 2 and IdCliente = ? and IdAfiliado = ? and IdServicio  =?");		
				s1.setInt(1, cliente.getIdcliente()); 
				s1.setInt(2, idafiliado);
				s1.setInt(3, idoficio);
				 ResultSet rs = s1.executeQuery();
				 if(rs!=null){
					   while (rs.next()){
				        	 
						   String rx = rs.getString(1);
							 if(rx!=""){
								 				res = Integer.parseInt(rx);		 
							 }
				        	 
				         }
					
					
				 }
		} catch (Exception e) {
		e.printStackTrace();
		}
		 
		 
		return res;
	}
	@Override
	public List<Cliente> consultarSolicitudInicial(String correo) throws SQLException {
		 Connection co = con.getConexion();
		
		 List<Cliente> listaCliente = new ArrayList<Cliente>();
	try {
		PreparedStatement s1 = co.prepareStatement("select s.idCliente,a.IdAfiliado,a.CorreoElectronico,o.IdServicio from Sodimac.GesSolicitud s join Sodimac.GesAfiliado a  on s.IdAfiliado = a.IdAfiliado  join Sodimac.GesAfiliadoServicio o on a.IdAfiliado = o.IdAfiliado  where o.IdAfiliado = s.IdAfiliado and s.IdEstadoSolicitud =1 and a.CorreoElectronico = ?  and o.IdAfiliado = s.IdAfiliado AND o.IdServicio = s.IdServicio ");
		s1.setString(1,correo);
		 ResultSet rs = s1.executeQuery();
		 if(rs!=null){
			   while (rs.next()){
		        	 
				  listaCliente = clientedao.obtenerclienteInicial(rs.getInt(1));
		         }
			
			
		 }
		
		
		
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
		return listaCliente;
	}
	@Override
	public String aceptarSolicitud(int idcliente, int idafiliado) {
		
		 Connection co = con.getConexion();
		 String res ="";
		 
		 try {
			 PreparedStatement s1 = co.prepareStatement("  update Sodimac.GesSolicitud set IdEstadoSolicitud = 2 from Sodimac.GesSolicitud s join Sodimac.GesCliente c on s.IdCliente = c.IdCliente join Sodimac.GesAfiliado a on s.IdAfiliado = a.IdAfiliado where s.IdCliente = ? and s.IdAfiliado = ?  and c.LongitudCliente is not null and c.LatitudCliente is not null and a.LongitudAfiliado is not null and a.LatitudAfiliado is not null and s.IdEstadoSolicitud != 2");
			 s1.setInt(1, idcliente); 
				s1.setInt(2, idafiliado);
				s1.execute();
		 res = "ACTUALIZADO";
		 } catch (Exception e) {
			 e.printStackTrace();
			// TODO: handle exception
		}
		// TODO Auto-generated method stub
		return res;
	}
	@Override
	public int consultarSolicitudPuntuar(int idsolicitud) throws SQLException {
		 Connection co = con.getConexion();
		 int res = 0;
		 try {
			 PreparedStatement s1 = co.prepareStatement("  select IdEstadoSolicitud from Sodimac.GesSolicitud where IdEstadoSolicitud = 4 and IdSolicitud = ?");		
				s1.setInt(1, idsolicitud); 
				 ResultSet rs = s1.executeQuery();
				 if(rs!=null){
					   while (rs.next()){
				        	 
						   String rx = rs.getString(1);
							 if(rx!=""){
								 				res = 1;		 
							 }
				        	 
				         }
					
					
				 }
		} catch (Exception e) {
		e.printStackTrace();
		}
		 
		 
		return res;
	}
	@Override
	public String pasarAPuntuar(int idcliente, int idafiliado) {
		 Connection co = con.getConexion();
		 String res ="";
		 
		 try {
			 PreparedStatement s1 = co.prepareStatement("  update Sodimac.GesSolicitud set IdEstadoSolicitud = 4 from Sodimac.GesSolicitud s join Sodimac.GesCliente c on s.IdCliente = c.IdCliente join Sodimac.GesAfiliado a on s.IdAfiliado = a.IdAfiliado where s.IdCliente = ? and s.IdAfiliado = ?  and c.LongitudCliente is not null and c.LatitudCliente is not null and a.LongitudAfiliado is not null and a.LatitudAfiliado is not null ");
			 s1.setInt(1, idcliente); 
				s1.setInt(2, idafiliado);
				s1.execute();
		 res = "ACTUALIZADO";
		 } catch (Exception e) {
			 e.printStackTrace();
			// TODO: handle exception
		}
		// TODO Auto-generated method stub
		return res;
	}
	@Override
	public String insertarPuntuaciones(int idsolicitud, int a, int b, int c, String observaciones) {
		 Connection co = con.getConexion();
		 String res ="";
		 
		 try {
			 PreparedStatement s1 = co.prepareStatement(" update Sodimac.GesSolicitud set Puntualidad = ?,Desempenio=?,Conocimiento=?,Observacion=?,IdEstadoSolicitud = 5 where IdSolicitud = ? ");
			 s1.setInt(1, a); 
				s1.setInt(2, b);
				s1.setInt(3,c);
				s1.setString(4, observaciones);
				s1.setInt(5,idsolicitud);
				s1.execute();
		 res = "ACTUALIZADO";
		 } catch (Exception e) {
			 e.printStackTrace();
			// TODO: handle exception
		}
		// TODO Auto-generated method stub
		return res;
	}
   
}
