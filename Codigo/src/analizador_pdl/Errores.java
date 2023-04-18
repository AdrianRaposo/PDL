package analizador_pdl;

public class Errores {


	private int linea;
	private String error;
	private int cod;
	private String token;
	private String tipo;

	public Errores(int linea , int codError, String token,String tipo) {
		this.linea= linea;
		this.cod= codError;
		this.token = token;
		this.tipo = tipo;
		this.error = getError(codError);
	}

	private String getError(int cod) {
		//Corregir en el futuro.
		String error ="";

		if(cod==1) {
			error = "El analizador no reconoce el caracter leido.";
		}
		else if(cod == 2) {
			error = "Caracter no esperado, despues de '&' se espera el caracter '&'.";
		}
		else if (cod == 3) {
			error = "Caracter no esperado, despues de '/' se espera el caracter '*'.";
		}
		else if (cod == 4) {
			error = "La cadena de caracteres tiene una longitud mayor de 64 caracteres.";
		}
			else if (cod == 5) {
				error = "La constante entera esta fuera del rango disponible.";
			}
		else if(cod == 6) {
			error = "Se esperaba un } pero se ha recibido un : " + this.token;
		}
		else if(cod == 7) {
			error = "Se esperaba un { pero se ha recibido un : " + this.token;
		}
		else if(cod == 8) {
			error = "Se esperaba un ) pero se ha recibido un : " + this.token;
		}
		else if(cod == 9) {
			error = "Se esperaba un ( pero se ha recibido un : " + this.token;
		}
		
		else if(cod == 10) {
			error = "Se esperaba un identificador pero se ha recibido un : "  + this.token;
		}
		
		else if(cod == 11) {
			error = "Se esperaba un ; pero se ha recibido un : " + this.token;
		}
		else if(cod == 12) {
			error ="Se esperaba un while pero se ha recibido un : " + this.token;
		}
		else if(cod == 13) {
			error ="El argumento "+ this.token + " ya ha sido declarado previamente. "  ;
		}
		else if(cod == 14) {
			error ="El tipo de la expresión dentro del if debe ser de tipo lógico. "  ;
		}
		else if(cod == 15) {
			error ="El identificador "+ this.token + " ya ha sido declarado anteriormente. "  ;
		}
		else if(cod == 16) {
			error ="La expresión dentro del while debe ser de tipo lógico."  ;
		}
		else if(cod == 17) {
			error ="Los tipos para hacer un asignación no coinciden."  ;
		}
		else if(cod == 18) {
			error ="funcion "+ this.token+" no declarada anteriormente."  ;
		}
		else if(cod == 19) {
			error ="Solo se puede de hacer print de expresiones de tipo cadena o entero."  ;
		}
		else if(cod == 20) {
			error ="Solo se puede de hacer input de variables de tipo cadena o entero."  ;
		}
		else if(cod == 21) {
			error ="Para hacer una asignación con suma el tipo de la expresión a asignar tiene que ser de tipo entera."  ;
		}
		else if(cod == 22) {
			error ="Para realizar un AND lógico los tipos de las expresiones tienen que ser iguales  y de tipo lógico."  ;
		}
		else if(cod == 23) {
			error ="Para a hacer un IGUAL logico los tipos de las expresiones tienen que ser iguales y de tipo entero."  ;
		}
		else if(cod == 24) {
			error ="Para realizar una suma aritmetica los tipos de las expresiones tienen que ser iguales y de tipo entero."  ;
		}
		else if(cod == 25) {
			error ="Argumentos incorrectos en la funcion "+this.token   ;
		}
		else if(cod == 26) {
			error ="Tipo de retorno incorrecto en la funcion."   ;
		}
		else if(cod == 27) {
			error ="Se esperaba una ',' o un ')'"   ;
		}
		else if(cod == 28) {
			error ="No se ha cerrado la cadena."   ;
		}
		return error;
	}

	public String toString() {
		return "Error  "+ cod +" ("+this.tipo+") en linea " + linea +": "+error ;
	}

}
