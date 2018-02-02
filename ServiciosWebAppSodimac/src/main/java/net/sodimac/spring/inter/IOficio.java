package net.sodimac.spring.inter;

import java.sql.SQLException;
import java.util.List;
import net.sodimac.spring.model.Oficio;

public interface IOficio {

	
	public List<Oficio> obtenerOficio(int idoficio) throws SQLException;
}
