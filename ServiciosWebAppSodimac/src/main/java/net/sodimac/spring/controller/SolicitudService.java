package net.sodimac.spring.controller;

import java.sql.SQLException;
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
import net.sodimac.spring.dao.SolicitudDAO;
import net.sodimac.spring.model.Cliente;

@RestController
public class SolicitudService {

	@Autowired
	SolicitudDAO solicituddao = new SolicitudDAO();
	
	
	
	Map<String,String> errorMap = new HashMap<String,String>();
	
	
	@RequestMapping(value = "/registroSolicitudInicial", method = RequestMethod.GET, produces = "application/json")
	public String registrarCliente(
			@RequestParam(value = "cliente")String correo,
			@RequestParam(value = "idafiliado")String idafiliado,
			@RequestParam(value = "idoficio")String idoficio
			) throws JsonProcessingException{
		
		String res = "";
		Map<String, String> clienteMap = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			res = solicituddao.insertarSolicitudInicial(correo, Integer.parseInt(idafiliado), Integer.parseInt(idoficio));
			
			if(res.contains("INSERTADO")){
			
				
				clienteMap.put("response",res);	
				res = mapper.writeValueAsString(clienteMap);	
			}else{
				errorMap.put("response",res);
				res = mapper.writeValueAsString(errorMap);	
			}

		
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}
	
	@RequestMapping(value = "/consultaEstadoAceptado", method = RequestMethod.GET, produces = "application/json")
	public String consultaEstadoAceptado(
			@RequestParam(value = "cliente")String correo,
			@RequestParam(value = "idafiliado")String idafiliado,
			@RequestParam(value = "idoficio")String idoficio
			) throws JsonProcessingException{
		int res = 0;
		String resu="";
		Map<String, String> clienteMap = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
		
		try {
	res = solicituddao.consultarSolicitudAceptada(correo, Integer.parseInt(idafiliado), Integer.parseInt(idoficio));
			
			if(res!=0){
			
				
				clienteMap.put("response",res+"");	
				resu = mapper.writeValueAsString(clienteMap);	
			}else{
				errorMap.put("response",res+"");
				resu = mapper.writeValueAsString(errorMap);	
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return resu;
	}
	
	
	@RequestMapping(value = "/consultaEstadoInicial", method = RequestMethod.GET, produces = "application/json")
	public String consultaEstadoInicial(
			@RequestParam(value = "correo")String correo
			) throws JsonProcessingException{
		Map<String, List<Cliente>> clienteMap = new HashMap<String, List<Cliente>>();
		ObjectMapper mapper = new ObjectMapper();
		String res = "";
		try {
			
			List<Cliente> lista = solicituddao.consultarSolicitudInicial(correo);
			
		clienteMap.put("response", lista);
		if(lista.size() < 1){
			errorMap.put("response","ERROR");
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
	

	@RequestMapping(value = "/consultaEstadoPuntuar", method = RequestMethod.GET, produces = "application/json")
	public String consultaEstadoPendiente(
			@RequestParam(value = "idsolicitud")int idsolicitud
			) throws JsonProcessingException{
		int res = 0;
		String resu="";
		Map<String, String> clienteMap = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
		
		try {
	res = solicituddao.consultarSolicitudPuntuar(idsolicitud);
			
			if(res ==1){
			
				
				clienteMap.put("response",res+"");	
				resu = mapper.writeValueAsString(clienteMap);	
			}else{
				errorMap.put("response",res+"");
				resu = mapper.writeValueAsString(errorMap);	
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return resu;
		}
	
	
	@RequestMapping(value = "/aceptarSolicitud", method = RequestMethod.GET, produces = "application/json")
	public String aceptarSolicitud(

			
			@RequestParam(value = "cliente")int correo,
			@RequestParam(value = "idafiliado")int idafiliado
	
			) throws JsonProcessingException{
		String res = "";
		String resu="";
		Map<String, String> clienteMap = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			 res = solicituddao.aceptarSolicitud(correo,idafiliado);
			
			if(res.equalsIgnoreCase("ACTUALIZADO")){
			
				
				clienteMap.put("response",res+"");	
				resu = mapper.writeValueAsString(clienteMap);	
			}else{
				errorMap.put("response",res+"");
				resu = mapper.writeValueAsString(errorMap);	
			}

		} catch (Exception e) {
		e.printStackTrace();
		}
		
		
		return resu;
		}


	@RequestMapping(value = "/pasarAPuntuar", method = RequestMethod.GET, produces = "application/json")
	public String pasarAPuntuar(
			@RequestParam(value = "cliente")int correo,
			@RequestParam(value = "idafiliado")int idafiliado
	
			) throws JsonProcessingException{
		String res = "";
		String resu="";
		Map<String, String> clienteMap = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			 res = solicituddao.pasarAPuntuar(correo,idafiliado);
			
			if(res.equalsIgnoreCase("ACTUALIZADO")){
			
				
				clienteMap.put("response",res+"");	
				resu = mapper.writeValueAsString(clienteMap);	
			}else{
				errorMap.put("response",res+"");
				resu = mapper.writeValueAsString(errorMap);	
			}

		} catch (Exception e) {
		e.printStackTrace();
		}
		
		
		return resu;
		}
	
	
	@RequestMapping(value = "/insertarPuntuaciones", method = RequestMethod.GET, produces = "application/json")
	public String insertarPuntuaciones(
			@RequestParam(value = "puntualidad")String a,
			@RequestParam(value = "conocimiento")String b,
			@RequestParam(value = "desempenio")String c,
			@RequestParam(value = "observaciones")String observaciones,
			@RequestParam(value = "idsolicitud")String idsolicitud
	
			) throws JsonProcessingException{
		String res = "";
		String resu="";
		Map<String, String> clienteMap = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			 res = solicituddao.insertarPuntuaciones(Integer.parseInt(idsolicitud), Integer.parseInt(a),Integer.parseInt(b),Integer.parseInt(c), observaciones);
			
			if(res.equalsIgnoreCase("ACTUALIZADO")){
			
				
				clienteMap.put("response",res+"");	
				resu = mapper.writeValueAsString(clienteMap);	
			}else{
				errorMap.put("response",res+"");
				resu = mapper.writeValueAsString(errorMap);	
			}

		} catch (Exception e) {
		e.printStackTrace();
		}
		
		
		return resu;
		}
	
	
	
}
