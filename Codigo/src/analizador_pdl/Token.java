package analizador_pdl;

public class Token {

	private int codigo;
	private String atributo;

	public Token(int codigo , String atributo) {

		this.codigo = codigo;
		this.atributo = atributo;

	}

	public int getCod() {
		return this.codigo;
	}

	public String getAtr() {
		return this.atributo;
	}

	public String codeToString () {
		String  token =  "";
		int cod = this.codigo;
		switch(cod) {
		case 1 :
			token = "boolean";
			break;
		case 2 :
			token = "do";
			break;
		case 3 :
			token = "function";
			break;
		case 4 :
			token = "if";
			break;
		case 5 :
			token = "input";
			break;
		case 6 :
			token = "int";
			break;
		case 7 :
			token = "let";
			break;
		case 8 :
			token = "print";
			break;
		case 9 :
			token = "return";
			break;
		case 10 :
			token = "string";
			break;
		case 11 :
			token = "while";
			break;
		case 12:
			token = "constante entera";
			break;
		case 13 :
			token = "cadena";
			break;
		case 14 :
			token = "identificador";
			break;
		case 15 :
			token = "+=";
			break;
		case 16 :
			token = "=";
			break;
		case 17 :
			token = ",";
			break;
		case 18 :
			token = ";";
			break;
		case 19 :
			token = "(";
			break;
		case 20 :
			token = ")";
			break;
		case 21 :
			token = "{";
			break;
		case 22 :
			token = "}";
			break;
		case 23 :
			token = "+";
			break;
		case 24 :
			token = "&&";
			break;
		case 25:
			token = "==";
			break;
		case 26:
			token = "false";
			break;
		case 27:
			token = "true";
			break;
		case 28:
			token = "EOF";
			break;
		default:
			token = "Token no permitido";
		}
		return token;
	}


}
