package analizador_pdl;

public class ItemTS {
	
	private String nombre;
	private String tipo;
	private int desp;
	private int param;
	private String retorno;
	private String etiqueta;
	
	public ItemTS (String nombre) {
		this.setNombre(nombre);
		this.tipo = null;
		this.desp = -1;
		this.tipo=null;
		this.param=-1;
		this.retorno=null;
		this.setEtiqueta(null);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getDesp() {
		return desp;
	}

	public void setDesp(int desp) {
		this.desp = desp;
	}

	public int getParam() {
		return param;
	}

	public void setParam(int param) {
		this.param = param;
	}

	public String getRetorno() {
		return retorno;
	}

	public void setRetorno(String retorno) {
		this.retorno = retorno;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}
}
