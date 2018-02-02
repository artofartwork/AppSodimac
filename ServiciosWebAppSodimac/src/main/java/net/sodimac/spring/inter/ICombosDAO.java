package net.sodimac.spring.inter;

import java.sql.SQLException;
import java.util.List;

import net.sodimac.spring.model.Documento;
import net.sodimac.spring.model.Oficio;
import net.sodimac.spring.model.Puntuacion;
import net.sodimac.spring.model.Ubigeo;

public interface ICombosDAO {

	
 List<Ubigeo>cargaComboUbigeo() throws SQLException;
 List<Documento>cargaDocumentos() throws SQLException;
 List<Oficio>cargaOficio()throws SQLException;
 List<Puntuacion>cargaPuntuacion()throws SQLException;
}
