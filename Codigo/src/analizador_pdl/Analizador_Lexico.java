package analizador_pdl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;


public class Analizador_Lexico {

	private FileReader fileReader;
	private ArrayList<Token> tokensLeidos;
	private ArrayList<Integer> lineas;
	private ArrayList<String> palabrasReservadas;
	private boolean fin;
	private String caracter;
	private GestorTS gestorTS;
	private String resultsPath;
	private String nPrueba;
	private int nLinea;
	private GestorErrores gestorErrores;
	private int tablaActiva;
	private boolean eof;
	private boolean zonaDecl=false;




	public Analizador_Lexico(String resultsPath,String nPrueba, String file, GestorTS gestorTS, GestorErrores gestorErrores) {

		tokensLeidos = new ArrayList<>();
		palabrasReservadas = new ArrayList<>();
		rellenarPalabrasReservadas(palabrasReservadas);
		try {
			fileReader = new FileReader(file);

		}catch(IOException ex) {
			System.err.println("Erorr al leer el archivo");
		}
		this.resultsPath = resultsPath;
		this.fin = false;
		this.caracter = leerCaracter();
		this.gestorTS= gestorTS;
		this.nPrueba = nPrueba;
		this.nLinea=1;
		this.gestorErrores = gestorErrores;
		this.lineas = new ArrayList<>();
		this.tablaActiva=0;
		this.eof=false;
	}

	public void generarTokens() {
		System.out.println("Creando archivo...");
		try {
			this.fileReader.close();
		} catch (IOException e) {
			System.out.println("Error al cerrar el lector de fichero.");
		}
		escribirTokens();
		System.out.println("Archivo txt de tokens generado con exito.");
	}

	public Token sigToken() {
		Token token = leerToken();
		if(token!=null) {
			guardarToken(token);
			lineas.add(nLinea);
		}
		return token;
	}


	private Token leerToken() {

		TipoCaracter cTipo = new TipoCaracter(); // objeto que comprueba el tipo de caracter segun un Pattern
		Token token = null;
		boolean leido = false;
		int estado = 0;
		String lexema = "";
		int valor = 0;

		// Automata
		while(!leido && !this.eof) {
			switch(estado) {
			case 0 :
				if(cTipo.esDelimitador(caracter) || caracter.equals("\r")){
					caracter = leerCaracter();
					estado = 0;
					if(caracter.equals("\n")) {
						System.out.println("Linea "+ nLinea +" leida.");
						this.nLinea++;
					}
				}
				else if(cTipo.esLetra(caracter)|| caracter.equals("_")){
					lexema+=caracter;
					caracter = leerCaracter();
					estado = 1;
				}
				else if(cTipo.esDigito(caracter)) {
					valor = Integer.valueOf(caracter);
					caracter = leerCaracter();
					estado = 2;
				}
				else if( caracter.equals("+")) {
					lexema += caracter;
					caracter = leerCaracter();
					estado = 3;

				}
				else if (caracter.equals("&")) {
					lexema += caracter;
					caracter = leerCaracter();
					estado = 4;
				}
				else if(caracter.equals("=")) {
					lexema += caracter;
					caracter = leerCaracter();
					estado = 5;
				}
				else if(caracter.equals("/")) {
					lexema += caracter;
					caracter = leerCaracter();
					estado = 6;
				}
				else if(caracter.equals("\"")) {
					lexema += caracter;
					caracter = leerCaracter();
					estado = 9;
				}
				else if(caracter.equals("{") ||caracter.equals("}") || caracter.equals("(") ||  caracter.equals(")") || caracter.equals(";") || caracter.equals(",")) {
					lexema += caracter;
					caracter=leerCaracter();
					estado = 19;
				}
				else {
					if(caracter.equals("FinFicher@") && fin==true) {
						token = new Token(Codigos.getEof(),"");
						this.eof=true;
					}else {
						gestorErrores.addError(new Errores(nLinea,1,"","l�xico"));
						token = null;
						
					}
					caracter = leerCaracter();
					leido = true;

				}
				break;
			case 1:
				if(cTipo.esLetra(caracter) || cTipo.esDigito(caracter) || caracter.equals("_")) {
					lexema +=caracter;
					caracter = leerCaracter();
					estado = 1;
				}
				else {
					estado = 10;
				}
				break;
			case 2 :
				if( cTipo.esDigito(caracter)) {
					valor= valor*10 + Integer.valueOf(caracter);
					caracter = leerCaracter();
					estado = 2;
				}
				else {
					estado = 11;
				}

				break;
			case 3 :
				if(caracter.equals("=")) {
					lexema+=caracter;
					caracter = leerCaracter();
					estado = 13;
				}else {
					estado = 12;
				}
				break;
			case 4 :
				if(caracter.equals("&")) {
					lexema += caracter;
					caracter = leerCaracter();
					estado = 14;
				}
				else {
					this.gestorErrores.addError(new Errores(nLinea,2,"","l�xico"));
					leido = true;
					token = null;
				}
				break;
			case 5 :
				if(caracter.equals("=")) {
					lexema+=caracter;
					caracter = leerCaracter();
					estado = 15;
				}else {
					estado = 18;
				}
				break;
			case 6 :
				if(caracter.equals("*")) {
					lexema+=caracter;
					caracter = leerCaracter();
					estado = 7;
				}else{
					this.gestorErrores.addError(new Errores(nLinea,3,"","l�xico"));
					leido = true;
					token = null;
				}
				break;
			case 7 :
				if(!caracter.equals("*")) {
					lexema += caracter;
					caracter = leerCaracter();
					estado = 7;
				}
				else{
					lexema += caracter;
					caracter = leerCaracter();
					estado = 8;
				}
				break;
			case 8 :
				if(!caracter.equals("*") && !caracter.equals("/") ){
					lexema += caracter;
					caracter = leerCaracter();
					estado = 7;
				}
				else if(caracter.equals("*")) {
					lexema += caracter;
					caracter = leerCaracter();
					estado = 8;
				}
				else if(caracter.equals("/")){
					lexema += caracter;
					caracter = leerCaracter();
					estado = 16;
				}
				else {
					this.gestorErrores.addError(new Errores(nLinea,4,"","l�xico"));
					leido = true;
					token = null;
				}
				break;
			case 9 :
				if(!caracter.equals("\"")&& !fin) {
					lexema += caracter;
					caracter= leerCaracter();
					estado = 9;
				}else if(caracter.equals("\"")){
					lexema+= caracter;
					caracter= leerCaracter();
					estado = 17;

				}else {
					this.gestorErrores.addError(new Errores(nLinea,28,"","l�xico"));
					leido=true;
					token=null;
				}
				break;
			case 10:
				int id = gestorTS.buscarId(lexema,this.tablaActiva);
				int tabla=this.tablaActiva;
				int dEnc= this.tablaActiva;
				if(id<0 && this.tablaActiva>0 && !this.zonaDecl) { 
					dEnc=0;
					id= gestorTS.buscarId(lexema,0);

				}
				if(palabrasReservadas.contains(lexema)) {
					token = new Token(codigoPalabraReservada(lexema),"");
					leido = true;
				}
				else if(id != -1) {
					if(dEnc>0 ) {
						token = new Token(Codigos.getIdentificador(),""+(id+1)*(-1));
					}else {
						token = new Token(Codigos.getIdentificador(),""+(id+1));
					}
					leido=true;
				}else {
					if(tabla==0 || !this.zonaDecl) {
						gestorTS.anadirId(0, lexema);
						token = new Token(Codigos.getIdentificador(),""+((gestorTS.buscarId(lexema,0)+1)));
					}else {
						gestorTS.anadirId(this.tablaActiva, lexema);
						token = new Token(Codigos.getIdentificador(),""+((gestorTS.buscarId(lexema,tabla)+1))*(-1));
					}
					leido=true;
				}

				break;
			case 11 :
				if(valor < (int) (Math.pow(2, 16)-1)) {
					token = new Token(Codigos.getConstEntera(), "" + valor);
					leido = true;
				}else {
					this.gestorErrores.addError(new Errores(nLinea,5,"","l�xico"));
					leido = true;
					token = null;
				}
				break;
			case 12 :
				token = new Token(Codigos.getSuma(),"");
				leido = true;
				break;
			case 13 :
				token = new Token(Codigos.getPreincremento(),"");
				leido = true;
				break;
			case 14 :
				token = new Token(Codigos.getAnd(),"");
				leido = true;
				break;
			case 15 :
				token = new Token (Codigos.getIgual(),"");
				leido=true;
				break;
			case 16 :
				estado=0;
				lexema="";
				break;
			case 17 :
				if(lexema.length()<=66) {
					token = new Token(Codigos.getCadena(),lexema);
				}else {
					token = null;
					this.gestorErrores.addError(new Errores(nLinea,4,"","l�xico"));
					caracter = leerCaracter();
				}
				leido = true;
				break;
			case 18 :
				token = new Token (Codigos.getAsigancion(),"");
				leido=true;
				break;
			case 19 :
				if(lexema.equals("{")){
					token = new Token(Codigos.getAbcor(),"");
					leido = true;
				}
				else if(lexema.equals("}")) {
					token = new Token(Codigos.getCecor(),"");
					leido = true;
				}
				else if(lexema.equals("(")) {
					token = new Token(Codigos.getAbpar(),"");
					leido = true;
				}
				else if(lexema.equals(")")) {
					token = new Token(Codigos.getCepar(),"");
					leido = true;
				}
				else if(lexema.equals(";")) {
					token = new Token(Codigos.getPuntoycoma(),"");
					leido = true;
				}
				else if(lexema.equals(",")) {
					token = new Token(Codigos.getComa(),"");
					leido = true;
				}
				break;


			}
		}
		return token;
	}


	private int codigoPalabraReservada(String lexema) {

		int cod = -1;
		if(lexema.equals("if")) {
			cod = Codigos.getIf();
		}
		else if(lexema.equals("boolean")) {
			cod = Codigos.getBoolean();
		}
		else if(lexema.equals("while")) {
			cod = Codigos.getWhile();
		}
		else if(lexema.equals("do")) {
			cod = Codigos.getDo();
		}
		else if(lexema.equals("function")) {
			cod = Codigos.getFunction();
		}
		else if(lexema.equals("input")) {
			cod = Codigos.getInput();
		}
		else if(lexema.equals("int")) {
			cod = Codigos.getInt();
		}
		else if(lexema.equals("let")) {
			cod = Codigos.getLet();
		}
		else if(lexema.equals("print")) {
			cod = Codigos.getPrint();
		}
		else if(lexema.equals("return")) {
			cod = Codigos.getReturn();
		}
		else if(lexema.equals("string")) {
			cod = Codigos.getString();
		}
		else if(lexema.equals("false")) {
			cod = Codigos.getFalse();
		}
		else if(lexema.equals("true")) {
			cod = Codigos.getTrue();
		}
		return cod;
	}


	private void escribirTokens(){
		String nombre = resultsPath + "\\tokens"+nPrueba+".txt";
		File fichero = new File(nombre);
		try {
			if (fichero.exists()) {
				fichero.delete();
			}
			fichero.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(nombre));
			for(int i=0; i<tokensLeidos.size();i++)
				bw.write("<" +tokensLeidos.get(i).getCod() + "," + tokensLeidos.get(i).getAtr() + ">\n");
			bw.close();
		} catch (IOException e) {

		}	
	}

	public void setTablaActiva(int tabla) {
		this.tablaActiva=tabla;
	}

	private String leerCaracter(){
		String res = ""; 
		try {
			int caracterLeido = fileReader.read();
			res += (char) caracterLeido;
			if(caracterLeido == -1 && !this.eof) {
				res="FinFicher@";
				fin = true;
				System.out.println("Fin de lectura del fichero.");
			}
		} catch (IOException e) {
			System.err.println("Erorr al leer el archivo");
		}
		return res;
	}

	private void rellenarPalabrasReservadas(ArrayList<String> palabrasReservadas) {
		try {
			String cadena;
			FileReader palabras = new FileReader("src//analizador_pdl//PalabrasReservadas.txt");
			BufferedReader b = new BufferedReader(palabras);
			while((cadena = b.readLine())!=null) { 
				palabrasReservadas.add(cadena);
			} 
			b.close();
		} catch (IOException e) {

			System.err.println("Erorr al leer el archivo");
		}


	}

	private void guardarToken(Token token){
		if(token != null)
			tokensLeidos.add(token);
	}


	public ArrayList<Token> getTokens(){
		return this.tokensLeidos;
	}
	public  ArrayList<Integer> getLinea(){
		return this.lineas;
	}

	public boolean isZonaDecl() {
		return zonaDecl;
	}

	public void setZonaDecl(boolean zonaDecl) {
		this.zonaDecl = zonaDecl;
		
	}




}
