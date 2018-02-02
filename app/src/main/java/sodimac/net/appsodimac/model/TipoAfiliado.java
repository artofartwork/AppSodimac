package sodimac.net.appsodimac.model;

public class TipoAfiliado {

	int idtipoafiliado;
	String tipoafiliado;
	EstadoRegistro idestadoregistro;
	public int getIdtipoafiliado() {
		return idtipoafiliado;
	}
	public void setIdtipoafiliado(int idtipoafiliado) {
		this.idtipoafiliado = idtipoafiliado;
	}
	public String getTipoafiliado() {
		return tipoafiliado;
	}
	public void setTipoafiliado(String tipoafiliado) {
		this.tipoafiliado = tipoafiliado;
	}
	public EstadoRegistro getIdestadoregistro() {
		return idestadoregistro;
	}
	public void setIdestadoregistro(EstadoRegistro idestadoregistro) {
		this.idestadoregistro = idestadoregistro;
	}
	public TipoAfiliado(int idtipoafiliado, String tipoafiliado, EstadoRegistro idestadoregistro) {
		super();
		this.idtipoafiliado = idtipoafiliado;
		this.tipoafiliado = tipoafiliado;
		this.idestadoregistro = idestadoregistro;
	}
	public TipoAfiliado() {
		super();
	}
	
	
	
}
