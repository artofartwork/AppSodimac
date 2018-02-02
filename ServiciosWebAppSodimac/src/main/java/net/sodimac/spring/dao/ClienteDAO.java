package net.sodimac.spring.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import net.sodimac.spring.inter.IClienteDAO;
import net.sodimac.spring.model.Cliente;
import net.sodimac.spring.model.Documento;
import net.sodimac.spring.model.EstadoRegistro;
import net.sodimac.spring.model.Ubigeo;
import net.sodimac.spring.model.Usuario;
import net.sodimac.spring.util.BDConexion;


@Component
public class ClienteDAO implements IClienteDAO {

	BDConexion con = new BDConexion();
	
	@Override
	public List<Cliente> ObtenerCliente(String correo) throws SQLException{
         Connection co = con.getConexion();
    	 List<Cliente> listaCliente = new ArrayList<Cliente>();
    	 Cliente cli = new Cliente();
    	 Ubigeo ubi = new Ubigeo();   	
    	 Documento doc = new Documento();
    	 EstadoRegistro ereg = new EstadoRegistro();
		try {
	 PreparedStatement s1 = co.prepareStatement("SELECT  IdCliente,Doc.IdtipoDocumento,NumDocumentoIdentidad,ApellidoPaterno,ApellidoMaterno,Nombres,Telefono,Ubi.IdUbigeo,DireccionDomicilio,CorreoElectronico,FechaCreacion,Est.IdEstadoRegistro,Ubi.Provincia, Ubi.Distrito,Ubi.Departamento,Est.EstadoRegistro,Doc.TipoDocumento FROM Sodimac.GesCliente Cli join Sodimac.RrhUbigeo Ubi on Cli.IdUbigeo = Ubi.IdUbigeo join Sodimac.SegEstadoRegistro Est on Cli.IdEstadoRegistro = Est.IdEstadoRegistro join Sodimac.GesTipoDocumento Doc on Cli.IdTipoDocumento = Doc.IdTipoDocumento where CorreoElectronico = ? ");
	 s1.setString(1,correo);
 
	 ResultSet rs = s1.executeQuery();
	

     if(rs!=null){
         while (rs.next()){
            
             cli.setIdcliente(rs.getInt(1));
             doc.setIdtipodocumento(rs.getInt(2));
         
                     
             cli.setNumdoc(rs.getString(3));
             cli.setApaterno(rs.getString(4));
             cli.setAmaterno(rs.getString(5));
             cli.setNombres(rs.getString(6));
             cli.setTlf(rs.getString(7));
             ubi.setIdubigeo(rs.getString(8));
            
             cli.setDireccion(rs.getString(9));
             cli.setEmail(rs.getString(10));

         cli.setFechaCreacion(rs.getDate(11));
                 ereg.setIdestadoregistro(rs.getInt(12));
                 ubi.setProvincia(rs.getString(13));
ubi.setDistrito(rs.getString(14));
ubi.setDepartamento(rs.getString(15));
             ereg.setEstadoRegistro(rs.getString(16));
             doc.setTipodocumento(rs.getString(17));
             
                 cli.setIdestadoreg(ereg);
             cli.setIdtipodoc(doc);
             cli.setIdubigeo(ubi);
                listaCliente.add(cli);
               

         }
     }
	 
	 
		
		} catch (SQLException e) {
		e.printStackTrace();
		}finally{
			
			 co.close();
		}

		
		
		
		return listaCliente;
	}

	@Override
	public String insertarCliente(Usuario cliente) {
		 Connection co = con.getConexion();
		 String res = "";
		 int r = 0;
		 
		 try {
//			PreparedStatement s1 = co.prepareStatement("Insert into Sodimac.GesCliente values (?,?,?,?,?,?,?,?,?,?,?,?) ");
			PreparedStatement s0 = co.prepareStatement("select Count(CorreoElectronico) from BDSodimac.Sodimac.GesCliente where CorreoElectronico = ?");
			s0.setString(1,cliente.getIdcliente().getEmail());
			ResultSet rs = s0.executeQuery();
			
		
			  if(rs!=null){
			         while (rs.next()){
			        	 
			        	r = rs.getInt(1); 
			        	 
			         }}
			if (r == 0) {
				

				CallableStatement s1 = co.prepareCall("{call Sodimac.GesCliente_InsertarConUsuario(?,?,?,?,?,?,?,?,?,?,?)}");	

				s1.setInt(1,cliente.getIdcliente().getIdtipodoc().getIdtipodocumento());
				s1.setString(2,cliente.getIdcliente().getNumdoc());
				s1.setString(3,cliente.getIdcliente().getApaterno());
				s1.setString(4,cliente.getIdcliente().getAmaterno());
				s1.setString(5,cliente.getIdcliente().getNombres());
				s1.setString(6,cliente.getIdcliente().getTlf());
				s1.setString(7,cliente.getIdcliente().getIdubigeo().getIdubigeo());
				s1.setString(8,cliente.getIdcliente().getDireccion());
				s1.setString(9,cliente.getIdcliente().getEmail());
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date date = cliente.getIdcliente().getFechaCreacion();
				java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			      
				s1.setString(10,formatter.format(sqlDate));
				s1.setString(11,cliente.getClave());
				s1.execute();
	            res = "INSERTADO";			
				
				
			}else{
				 res = "ERROR USUARIO YA EXISTE";	
				
			}
			
			 	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = "ERROR : " + e;
		}		 
		 
		 
		 
		 
		return res;
	}

	@Override
	public int iniciarSesion(String email, String clave) {
		 Connection co = con.getConexion();
		 int res = 0;
		 System.out.println("LLAMADA A SESION INICIADA");
       
		 try {
			
			 PreparedStatement s1 = co.prepareStatement("select Count(Usuario) from BDSodimac.Sodimac.SegUsuario where Usuario = ? and Clave = ?");
			s1.setString(1,email);
			 s1.setString(2,clave);
			 ResultSet rs = s1.executeQuery();
			 System.out.println("INICIANDO SESION");

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
	public String actualizarUbicacion(double latitud, double longitud,String correo) {
		 Connection co = con.getConexion();
		 String res ="";
		 
		 try {
			 PreparedStatement s1 = co.prepareStatement("update Sodimac.GesCliente set LatitudCliente = ?, LongitudCliente =? where CorreoElectronico = ?");
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
	public List<Cliente> obtenerclienteInicial(int idcliente) throws SQLException {
		   Connection co = con.getConexion();
	    	 List<Cliente> listaCliente = new ArrayList<Cliente>();
	    	 Cliente cli = new Cliente();
	    	 Ubigeo ubi = new Ubigeo();   	
	    	 Documento doc = new Documento();
	    	 EstadoRegistro ereg = new EstadoRegistro();
			try {
		 PreparedStatement s1 = co.prepareStatement("SELECT  IdCliente,Doc.IdtipoDocumento,NumDocumentoIdentidad,ApellidoPaterno,ApellidoMaterno,Nombres,Telefono,Ubi.IdUbigeo,DireccionDomicilio,CorreoElectronico,FechaCreacion,Est.IdEstadoRegistro,Ubi.Provincia, Ubi.Distrito,Ubi.Departamento,Est.EstadoRegistro,Doc.TipoDocumento,Cli.LongitudCliente,Cli.LatitudCliente FROM Sodimac.GesCliente Cli join Sodimac.RrhUbigeo Ubi on Cli.IdUbigeo = Ubi.IdUbigeo join Sodimac.SegEstadoRegistro Est on Cli.IdEstadoRegistro = Est.IdEstadoRegistro join Sodimac.GesTipoDocumento Doc on Cli.IdTipoDocumento = Doc.IdTipoDocumento where IdCliente = ? ");
		 s1.setInt(1,idcliente);
	 
		 ResultSet rs = s1.executeQuery();
		

	     if(rs!=null){
	         while (rs.next()){
	            
	             cli.setIdcliente(rs.getInt(1));
	             doc.setIdtipodocumento(rs.getInt(2));
	         
	                     
	             cli.setNumdoc(rs.getString(3));
	             cli.setApaterno(rs.getString(4));
	             cli.setAmaterno(rs.getString(5));
	             cli.setNombres(rs.getString(6));
	             cli.setTlf(rs.getString(7));
	             ubi.setIdubigeo(rs.getString(8));
	            
	             cli.setDireccion(rs.getString(9));
	             cli.setEmail(rs.getString(10));

	         cli.setFechaCreacion(rs.getDate(11));
	                 ereg.setIdestadoregistro(rs.getInt(12));
	                 ubi.setProvincia(rs.getString(13));
	ubi.setDistrito(rs.getString(14));
	ubi.setDepartamento(rs.getString(15));
	             ereg.setEstadoRegistro(rs.getString(16));
	             doc.setTipodocumento(rs.getString(17));
	           cli.setLongitudCliente(rs.getDouble(18));
	           cli.setLatitudCliente(rs.getDouble(19));
	                 cli.setIdestadoreg(ereg);
	             cli.setIdtipodoc(doc);
	             cli.setIdubigeo(ubi);
	                listaCliente.add(cli);
	               

	         }
	     }
		 
		 
			
			} catch (SQLException e) {
			e.printStackTrace();
			}finally{
				
				 co.close();
			}

			
			
			
			return listaCliente;
	}



	
}
