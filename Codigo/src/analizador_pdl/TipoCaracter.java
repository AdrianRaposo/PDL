package analizador_pdl;

import java.util.regex.*;

public class TipoCaracter {
	private Pattern letra = Pattern.compile("[a-zA-z]"+"");
	private Pattern digito = Pattern.compile("[0-9]");
	private Pattern delimitador = Pattern.compile("["+" "+"\\t"+"\\f"+"\\n"+"]");
	private Pattern ce = Pattern.compile("\\{\\}();,");
	
	public TipoCaracter(){
		
	}
	
	public boolean esLetra(String caracter){
		Matcher letra = this.letra.matcher(caracter);
		return letra.matches();
	}
	
	public boolean esDigito(String caracter){
		Matcher digito = this.digito.matcher(caracter);
		return digito.matches();
	}
	
	public boolean esDelimitador(String caracter){
		Matcher delimitador = this.delimitador.matcher(caracter);
		return delimitador.matches();
	}
	public boolean esCe(String caracter){
		Matcher letra = this.ce.matcher(caracter);
		return letra.matches();
	}

}
