package net.sodimac.spring.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Component;

import net.sodimac.spring.inter.IAfiliado;
import net.sodimac.spring.model.Afiliado;
import net.sodimac.spring.model.Documento;
import net.sodimac.spring.model.EstadoRegistro;
import net.sodimac.spring.model.Oficio;
import net.sodimac.spring.model.TipoAfiliado;
import net.sodimac.spring.model.Ubigeo;
import net.sodimac.spring.util.BDConexion;


@Component
public class AfiliadoDAO implements IAfiliado{
	BDConexion con = new BDConexion();
	@Override
	public int iniciarSesion(String email, String clave) {
		 Connection co = con.getConexion();
		 int res = 0;
       
		 try {
			
			 PreparedStatement s1 = co.prepareStatement("select Count(Usuario) from BDSodimac.Sodimac.SegUsuario where Usuario = ? and Clave = ? and  IdAfiliado is not null");
			s1.setString(1,email);
			 s1.setString(2,clave);
			 ResultSet rs = s1.executeQuery();
			 

			  if(rs!=null){
			         while (rs.next()){
			        	 
			        	res = rs.getInt(1); 
			        	 
			         }}
			  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = -1;
		}
		
		 
		return res;
	}

	@Override
	public List<Afiliado> obtenerDatosAfiliado(int ocupacion) throws SQLException {
		   Connection co = con.getConexion();
		   List<Afiliado> listaAfiliado = new ArrayList<Afiliado>();
		  
		   Documento doc = new Documento();
		   Ubigeo ubi = new Ubigeo();
		   EstadoRegistro est = new EstadoRegistro();
		   TipoAfiliado tafi = new TipoAfiliado();
		   try {
	 PreparedStatement s1 = co.prepareStatement("select GA.idAfiliado,IdTipoDocumento,NumDocumentoIdentidad,ApellidoPaterno,ApellidoMaterno,Nombres,Telefono,IdUbigeo,DireccionDomicilio,CorreoElectronico,Fotografia,IdTipoAfiliado,IdEstadoRegistro,LatitudAfiliado,LongitudAfiliado from Sodimac.GesAfiliado GA join Sodimac.GesAfiliadoServicio OC on GA.IdAfiliado = OC.IdAfiliado where OC.IdServicio = ?  and LatitudAfiliado is not null and  IdTipoAfiliado = 2 and LongitudAfiliado is not null");			
		s1.setInt(1,ocupacion);
		 ResultSet rs = s1.executeQuery();
		 
		 if (rs!=null) {
			while (rs.next()) {
				 Afiliado afi = new Afiliado();
		afi.setIdafiliado(rs.getInt(1));
		doc.setIdtipodocumento(rs.getInt(2));
		afi.setTipoDocumento(doc);
		afi.setNumdoc(rs.getString(3));
		afi.setApat(rs.getString(4));
		afi.setAmat(rs.getString(5));
		afi.setNombres(rs.getString(6));
		afi.setTlf(rs.getString(7));
		ubi.setIdubigeo(rs.getString(8));
		afi.setUbigeo(ubi);
		afi.setDireccion(rs.getString(9));
		afi.setCorreo(rs.getString(10));

		byte[] imageBytes = rs.getBytes(11);
		String imageBase64 = DatatypeConverter.printBase64Binary(imageBytes);
		
		
		afi.setFoto(imageBase64);
		tafi.setIdtipoafiliado(rs.getInt(12));
		afi.setTipoafiliado(tafi);
	    est.setIdestadoregistro(rs.getInt(13));
	    afi.setIdestadoRegistro(est);
		afi.setLatitudAfiliado(rs.getDouble(14));
		afi.setLongitudAfiliado(rs.getDouble(15));
		listaAfiliado.add(afi);
			
			}
		}

		   } catch (SQLException e) {
		e.printStackTrace();
		}finally{
			
			co.close();
		}
		   
		   
		return listaAfiliado;
	}

	@Override
	public String actualizarUbicacion(double latitud, double longitud, String correo) {
		 Connection co = con.getConexion();
		 String res = "";
		 try {
			 PreparedStatement s1 = co.prepareStatement("update Sodimac.GesAfiliado set LatitudAfiliado = ?, LongitudAfiliado =? where CorreoElectronico = ?");
			 s1.setDouble(1, latitud);
			 s1.setDouble(2, longitud);
			 s1.setString(3,correo);
			 
			 s1.execute();
			 res = "ACTUALIZADO";
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
		
	}

	@Override
	public List<Afiliado> obtenerDatosAfiliadoCorreo(String correo) throws SQLException {
		   Connection co = con.getConexion();
		   List<Afiliado> listaAfiliado = new ArrayList<Afiliado>();
		  
		   Documento doc = new Documento();
		   Ubigeo ubi = new Ubigeo();
		   EstadoRegistro est = new EstadoRegistro();
		   TipoAfiliado tafi = new TipoAfiliado();
		   try {
	 PreparedStatement s1 = co.prepareStatement("  select GA.idAfiliado,IdTipoDocumento,NumDocumentoIdentidad,ApellidoPaterno,ApellidoMaterno,Nombres,Telefono,IdUbigeo,DireccionDomicilio,CorreoElectronico,Fotografia,IdTipoAfiliado,IdEstadoRegistro,LatitudAfiliado,LongitudAfiliado from Sodimac.GesAfiliado GA where  CorreoElectronico = ?");			
		s1.setString(1,correo);
		 ResultSet rs = s1.executeQuery();
		 
		 if (rs!=null) {
			while (rs.next()) {
				 Afiliado afi = new Afiliado();
		afi.setIdafiliado(rs.getInt(1));
		doc.setIdtipodocumento(rs.getInt(2));
		afi.setTipoDocumento(doc);
		afi.setNumdoc(rs.getString(3));
		afi.setApat(rs.getString(4));
		afi.setAmat(rs.getString(5));
		afi.setNombres(rs.getString(6));
		afi.setTlf(rs.getString(7));
		ubi.setIdubigeo(rs.getString(8));
		afi.setUbigeo(ubi);
		afi.setDireccion(rs.getString(9));
		afi.setCorreo(rs.getString(10));

		byte[] imageBytes = rs.getBytes(11);
		String imageBase64 = DatatypeConverter.printBase64Binary(imageBytes);
		
		
		afi.setFoto(imageBase64);
		tafi.setIdtipoafiliado(rs.getInt(12));
		afi.setTipoafiliado(tafi);
	    est.setIdestadoregistro(rs.getInt(13));
	    afi.setIdestadoRegistro(est);
		afi.setLatitudAfiliado(rs.getDouble(14));
		afi.setLongitudAfiliado(rs.getDouble(15));
		listaAfiliado.add(afi);
			
			}
		}

		   } catch (SQLException e) {
		e.printStackTrace();
		}finally{
			
			co.close();
		}
		   
		   
		return listaAfiliado;
	}



}
