package sodimac.net.appsodimac.model;

public class OficioAfiliado {

	Afiliado ifafiliado;
	Oficio idoficio;
	public Afiliado getIfafiliado() {
		return ifafiliado;
	}
	public void setIfafiliado(Afiliado ifafiliado) {
		this.ifafiliado = ifafiliado;
	}
	public Oficio getIdoficio() {
		return idoficio;
	}
	public void setIdoficio(Oficio idoficio) {
		this.idoficio = idoficio;
	}
	public OficioAfiliado(Afiliado ifafiliado, Oficio idoficio) {
		super();
		this.ifafiliado = ifafiliado;
		this.idoficio = idoficio;
	}
	public OficioAfiliado() {
		super();
	}
	
	
	
	
}
