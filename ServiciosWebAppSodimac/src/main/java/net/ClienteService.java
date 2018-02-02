package net.sodimac.spring.controller;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sodimac.spring.dao.ClienteDAO;
import net.sodimac.spring.model.Cliente;
import net.sodimac.spring.model.Documento;
import net.sodimac.spring.model.Ubigeo;
import net.sodimac.spring.model.Usuario;

@RestController
public class ClienteService {
	
	@Autowired
	ClienteDAO clientedao = new ClienteDAO();
	

	Map<String,String> errorMap = new HashMap<String,String>();
	
	@RequestMapping(value = "/obtener", method = RequestMethod.GET, produces = "application/json")
public  String obtenerCliente(@RequestParam(value = "correo") String correo) throws JsonProcessingException{
	
	String res = "";
	Map<String, List<Cliente>> clienteMap = new HashMap<String, List<Cliente>>();
	ObjectMapper mapper = new ObjectMapper();
	try {
		
			List<Cliente> lista = clientedao.ObtenerCliente(correo);
		clienteMap.put("response", lista);
		if(lista.size() < 1){
			errorMap.put("response","ERROR CLIENTE NO EXISTE");
			res = mapper.writeValueAsString(errorMap);	
			
		}else{
		res = mapper.writeValueAsString(clienteMap);		
		}
	} catch (Exception e) {
		errorMap.put("response","ERROR : " + e);
		res = mapper.writeValueAsString(errorMap);
	}
	return res;


	
}
	
	@RequestMapping(value = "/iniciosesion", method = RequestMethod.GET, produces = "application/json")
	public String inicioSesion(
			@RequestParam(value = "usuario")String usuario,
			@RequestParam(value = "clave")String clave			
			){
		String res = "";
		try {
			
			Map<String, String> sesionMap = new HashMap<String, String>();
			ObjectMapper mapper = new ObjectMapper();
			int resultado = clientedao.iniciarSesion(usuario, clave);
			if(resultado == -1){
				errorMap.put("response",resultado+"");
				res = mapper.writeValueAsString(errorMap);			
			}if(resultado==0){
				errorMap.put("response",resultado+"");
				res = mapper.writeValueAsString(errorMap);
			
			}if(resultado==1){
				sesionMap.put("response",resultado+"");
				res = mapper.writeValueAsString(sesionMap);	
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
				return res;
		
		
	}
	
	
	@RequestMapping(value = "/registrar", method = RequestMethod.GET, produces = "application/json")
	public String registrarCliente(
			@RequestParam(value = "idtipodoc")int idtipodoc,
			@RequestParam(value = "numdoc")String numdoc,
			@RequestParam(value = "apaterno")String apaterno,
			@RequestParam(value = "amaterno")String amaterno,
			@RequestParam(value = "nombres")String nombres,
			@RequestParam(value = "tlf")String tlf,
			@RequestParam(value = "idubigeo")String idubigeo,
			@RequestParam(value = "direccion")String direccion,
			@RequestParam(value = "email")String email,
			@RequestParam(value = "fechaCreacion")String fechaCreacion,
			@RequestParam(value = "Clave")String Clave
			) throws JsonProcessingException{
		String res = "";
		Map<String, String> clienteMap = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
					
			Usuario usuario = new Usuario();
			Cliente cliente = new Cliente();
		 	 Ubigeo ubi = new Ubigeo();
	    	 Documento doc = new Documento();
			
	    	ubi.setIdubigeo(idubigeo); 
			cliente.setIdubigeo(ubi);
			doc.setIdtipodocumento(idtipodoc);
			cliente.setIdtipodoc(doc);
			cliente.setNumdoc(numdoc);
			cliente.setApaterno(apaterno);
			cliente.setAmaterno(amaterno);
			cliente.setNombres(nombres);
			cliente.setTlf(tlf);
			cliente.setDireccion(direccion);
			cliente.setEmail(email);
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			formatter.setLenient(false);
			usuario.setClave(Clave);
			Date date;
			try {
				date = formatter.parse(fechaCreacion);
				cliente.setFechaCreacion(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			usuario.setIdcliente(cliente);
			String resu = clientedao.insertarCliente(usuario);
			if(resu.contains("ERROR")){
				errorMap.put("response",resu);
				res = mapper.writeValueAsString(errorMap);			
				
				
				
			}else{
				clienteMap.put("response",resu);	
				res = mapper.writeValueAsString(clienteMap);
			
			}
			
	
		
		return res;
		
		
		
		
	}
	
	
	@RequestMapping(value = "/actualizarUbicacion", method = RequestMethod.GET, produces = "application/json")
    public String actualizarUbicacion(
			@RequestParam(value = "latitud")String latitud,
			@RequestParam(value = "longitud")String longitud,
			@RequestParam(value = "user")String correo) throws JsonProcessingException{
		String res = "";
		Map<String, String> clienteMap = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
		String resu = clientedao.actualizarUbicacion(Double.parseDouble(latitud), Double.parseDouble(longitud), correo);
		if(resu.contains("ACTUALIZADO")){
			clienteMap.put("response",resu);	
			res = mapper.writeValueAsString(clienteMap);						
		}else{
			errorMap.put("response",resu);
			res = mapper.writeValueAsString(errorMap);	
		}
		

	
	return res;
	
		
		
	}
}
