package net.sodimac.spring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import net.sodimac.spring.inter.ICombosDAO;
import net.sodimac.spring.model.Documento;
import net.sodimac.spring.model.Oficio;
import net.sodimac.spring.model.Puntuacion;
import net.sodimac.spring.model.Ubigeo;
import net.sodimac.spring.util.BDConexion;

@Component
public class CombosDAO implements ICombosDAO{

	
	BDConexion con = new BDConexion();
	@Override
	public List<Ubigeo> cargaComboUbigeo() throws SQLException {
		// TODO Auto-generated method stub
        Connection co = con.getConexion();
   	    List<Ubigeo> listaUbigeo = new ArrayList<Ubigeo>();	
   	   
   	    
   	    try {
   		 PreparedStatement s1 = co.prepareStatement("select IdUbigeo,Distrito from Sodimac.RrhUbigeo where Departamento = 'Lima' and Distrito is not null and Provincia = 'Lima'");
   		 ResultSet rs = s1.executeQuery();
   			
   		 while (rs.next()) {
   			 Ubigeo ubi = new Ubigeo();
			ubi.setIdubigeo(rs.getString(1));
			ubi.setDistrito(rs.getString(2));
			listaUbigeo.add(ubi);
			
		}

		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			
			co.close();
		}
		
		return listaUbigeo;
	}

	@Override
	public List<Documento> cargaDocumentos() throws SQLException {
		
		
		 Connection co = con.getConexion();
	   	    List<Documento> listaDocumento = new ArrayList<Documento>();	
	   
	   	 
	   	    try {
	      		 PreparedStatement s1 = co.prepareStatement("select IdTipoDocumento,TipoDocumento from Sodimac.GesTipoDocumento");
	      		 ResultSet rs = s1.executeQuery();
	      			
	      		 while (rs.next()) {
	      			 Documento doc = new Documento();
	      			doc.setIdtipodocumento(rs.getInt(1));
	      			doc.setTipodocumento(rs.getString(2));
	      			listaDocumento.add(doc);
	      		
	   		}
	      		 
	      		 
	      		 
	   		} catch (Exception e) {
	   			e.printStackTrace();
	   		} finally{
	   			
	   			co.close();
	   		}
	   	 
	   	 
	   	 
		// TODO Auto-generated method stub
		return listaDocumento;
	}

	@Override
	public List<Oficio> cargaOficio() throws SQLException {

		 Connection co = con.getConexion();
		 List<Oficio> listaOficio= new  ArrayList<Oficio>();
		 try {
	 		 PreparedStatement s1 = co.prepareStatement("select * from Sodimac.GesServicio");
      		 ResultSet rs = s1.executeQuery();
      			
      		 while (rs.next()) {
      			 Oficio ofi = new Oficio();
      			ofi.setIdoficio(rs.getInt(1));
      			ofi.setOficio(rs.getString(2));
      			listaOficio.add(ofi);	}
      		 
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
   			
   			co.close();
   		}
   	 
		 
		 
		return listaOficio;
	}

	@Override
	public List<Puntuacion> cargaPuntuacion() throws SQLException {
		 Connection co = con.getConexion();
		 List<Puntuacion> listaPuntuacion= new  ArrayList<Puntuacion>();
		 try {
	 		 PreparedStatement s1 = co.prepareStatement("select * from Sodimac.GesPuntuacion");
     		 ResultSet rs = s1.executeQuery();
     			
     		 while (rs.next()) {
     			 Puntuacion ofi = new Puntuacion();
     			ofi.setIdpuntuacion(rs.getInt(1));
     			ofi.setPuntuacion(rs.getString(2));
     			listaPuntuacion.add(ofi);	}
     		 
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
  			
  			co.close();
  		}
  	 
		 
		 
		return listaPuntuacion;
	}

}
