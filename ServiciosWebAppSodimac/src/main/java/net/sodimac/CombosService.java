package net.sodimac.spring.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sodimac.spring.dao.CombosDAO;
import net.sodimac.spring.model.Documento;
import net.sodimac.spring.model.Oficio;
import net.sodimac.spring.model.Puntuacion;
import net.sodimac.spring.model.Ubigeo;

@RestController
public class CombosService {

	@Autowired
	CombosDAO combosDao = new CombosDAO();
	
	Map<String,String> errorMap = new HashMap<String,String>();
	@RequestMapping(value = "/comboUbigeo", method = RequestMethod.GET, produces = "application/json")
	public String obtenerUbigeo() throws JsonProcessingException{
		
		String res = "";
		Map<String, List<Ubigeo>> ubigeoMap = new HashMap<String, List<Ubigeo>>();	
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			List<Ubigeo> listaUbigeo = combosDao.cargaComboUbigeo();
			ubigeoMap.put("response", listaUbigeo);
			if (listaUbigeo.size() < 1) {
				errorMap.put("response","ERROR AL CARGAR EL UBIGEO");
				res = mapper.writeValueAsString(errorMap);	
			} else {
				res = mapper.writeValueAsString(ubigeoMap);	
			}
			
		} catch (Exception e) {
			errorMap.put("response","ERROR : " + e);
			res = mapper.writeValueAsString(errorMap);
			// TODO: handle exception
		}
		
		
		
		return res;
		
	}
	
	@RequestMapping(value = "/comboDocumento", method = RequestMethod.GET, produces = "application/json")	
	public String obtenerDocumento() throws JsonProcessingException{
		
		String res = "";
		Map<String, List<Documento>> documentoMap = new HashMap<String, List<Documento>>();	
		ObjectMapper mapper = new ObjectMapper();
	try {
			
			List<Documento> listaDocumento = combosDao.cargaDocumentos();
			documentoMap.put("response", listaDocumento);
			if (listaDocumento.size() < 1) {
				errorMap.put("response","ERROR AL CARGAR EL DOCUMENTO");
				res = mapper.writeValueAsString(errorMap);	
			} else {
				res = mapper.writeValueAsString(documentoMap);	
			}
			
		} catch (Exception e) {
			errorMap.put("response","ERROR : " + e);
			res = mapper.writeValueAsString(errorMap);
			// TODO: handle exception
		}
		
		
		return res;

	}
	
	
	@RequestMapping(value = "/comboOcupacion", method = RequestMethod.GET, produces = "application/json")
	public String obtenerOficio() throws JsonProcessingException{
		String res = "";
		Map<String, List<Oficio>> oficioMap = new HashMap<String, List<Oficio>>();	
		ObjectMapper mapper = new ObjectMapper();
try {
			
			List<Oficio> listaOficio = combosDao.cargaOficio();
			oficioMap.put("response", listaOficio);
			if (listaOficio.size() < 1) {
				errorMap.put("response","ERROR AL CARGAR EL DOCUMENTO");
				res = mapper.writeValueAsString(errorMap);	
			} else {
				res = mapper.writeValueAsString(oficioMap);	
			}
			
		} catch (Exception e) {
			errorMap.put("response","ERROR : " + e);
			res = mapper.writeValueAsString(errorMap);
			// TODO: handle exception
		}
		
		
		return res;
}
	
	@RequestMapping(value = "/comboPuntuacion", method = RequestMethod.GET, produces = "application/json")
	public String obtenerPuntuacion() throws JsonProcessingException{
		String res = "";
		Map<String, List<Puntuacion>> PuntuacionMap = new HashMap<String, List<Puntuacion>>();	
		ObjectMapper mapper = new ObjectMapper();
try {
			
			List<Puntuacion> listaPuntuacion = combosDao.cargaPuntuacion();
			PuntuacionMap.put("response", listaPuntuacion);
			if (listaPuntuacion.size() < 1) {
				errorMap.put("response","ERROR AL CARGAR EL DOCUMENTO");
				res = mapper.writeValueAsString(errorMap);	
			} else {
				res = mapper.writeValueAsString(PuntuacionMap);	
			}
			
		} catch (Exception e) {
			errorMap.put("response","ERROR : " + e);
			res = mapper.writeValueAsString(errorMap);
			// TODO: handle exception
		}
		
		
		return res;
}
}
