package net.sodimac.spring.controller;

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
import com.fasterxml.jackson.databind.SerializationFeature;

import net.sodimac.spring.dao.AfiliadoDAO;
import net.sodimac.spring.model.Afiliado;


@RestController
public class AfiliadoService {

	
	@Autowired
	AfiliadoDAO afiliadodao = new AfiliadoDAO();
	
	Map<String,String> errorMap = new HashMap<String,String>();

	
	@RequestMapping(value = "/iniciosesionaf", method = RequestMethod.GET, produces = "application/json")
	public String inicioSesion(
			@RequestParam(value = "usuario")String usuario,
			@RequestParam(value = "clave")String clave			
			){
		String res = "";
		try {
			
			Map<String, String> sesionMap = new HashMap<String, String>();
			ObjectMapper mapper = new ObjectMapper();
			int resultado = afiliadodao.iniciarSesion(usuario, clave);
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
			// TODO: handle exception
		}
	
				return res;
		
		
	}
	
	@RequestMapping(value = "/actualizarUbicacionAfiliado", method = RequestMethod.GET, produces = "application/json")
    public String actualizarUbicacion(
			@RequestParam(value = "latitud")String latitud,
			@RequestParam(value = "longitud")String longitud,
			@RequestParam(value = "user")String correo) throws JsonProcessingException{
		String res = "";
		Map<String, String> afiliadoMap = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
		String resu = afiliadodao.actualizarUbicacion(Double.parseDouble(latitud), Double.parseDouble(longitud), correo);
		if(resu.contains("ACTUALIZADO")){
			afiliadoMap.put("response",resu);	
			res = mapper.writeValueAsString(afiliadoMap);						
		}else{
			errorMap.put("response",resu);
			res = mapper.writeValueAsString(errorMap);	
		}
		

	
	return res;
	
		
		
	}
	
	
	
	@RequestMapping(value = "/obtenerAfiliado", method = RequestMethod.GET, produces = "application/json")
public  String obtenerCliente(@RequestParam(value = "oficio") String oficio) throws JsonProcessingException{
	
	String res = "";
	Map<String, List<Afiliado>> clienteMap = new HashMap<String, List<Afiliado>>();
	ObjectMapper mapper = new ObjectMapper();
	mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false); 
	try {
		
			List<Afiliado> lista = afiliadodao.obtenerDatosAfiliado(Integer.parseInt(oficio));
		clienteMap.put("response", lista);
		if(lista.size() < 1){
			errorMap.put("response","ERROR AL OBTENER AFILIADO");
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
	
	
	@RequestMapping(value = "/obtenerAfiliadoCorreo", method = RequestMethod.GET, produces = "application/json")
public  String obtenerAfiliado(@RequestParam(value = "correo") String correo) throws JsonProcessingException{
	
	String res = "";
	Map<String, List<Afiliado>> clienteMap = new HashMap<String, List<Afiliado>>();
	ObjectMapper mapper = new ObjectMapper();
	mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false); 
	try {
		
			List<Afiliado> lista = afiliadodao.obtenerDatosAfiliadoCorreo(correo);
		clienteMap.put("response", lista);
		if(lista.size() < 1){
			errorMap.put("response","ERROR AL OBTENER AFILIADO");
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
	
	
	
}
