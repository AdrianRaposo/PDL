package analizador_pdl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Analizador_Sintactico_Semantico {

	private ArrayList<Integer> parse;
	private Analizador_Lexico al;
	private Token tokenActivo;
	private int nToken;
	private String resultsPath;
	private String nPrueba;
	private GestorErrores gestorErrores;
	private GestorTS gestorTS;
	private int tablaActiva;
	private String Htipo, S1tipo, Xtipo, Etipo, E1tipo, Rtipo, R1tipo, Utipo, U1tipo, Vtipo, V1tipo ,Ttipo;
	private int  Tancho;
	private String CRet, Bret, Sret;
	private ArrayList<ArrayList<String>> LArgm ;
	private int despG, despL, nTablas;
	private ArrayList<String> tablasFunciones, V1Argm;
	private int funLlamad;
	private boolean fin;


	public Analizador_Sintactico_Semantico(String resultsPath,String nPrueba,Analizador_Lexico al,GestorErrores gestorErrores,GestorTS gestorTS) {
		this.al = al;
		this.nToken= 0;
		this.tokenActivo = getNextToken();
		this.parse= new ArrayList<>();
		this.tablasFunciones= new ArrayList<>();
		this.LArgm= new ArrayList<>();
		this.V1Argm= new ArrayList<>();
		this.nPrueba = nPrueba;
		this.resultsPath= resultsPath;
		this.gestorErrores = gestorErrores;
		this.gestorTS=gestorTS;
		this.tablaActiva=0;
		this.despG=0;
		this.despL=0;
		this.nTablas=1;
		this.funLlamad=0;
		this.fin=false;
	}

	public void generarParse() {
		P0();
		this.fin=true;
		this.tokenActivo = getNextToken();
		if(this.tokenActivo != null) {
			System.out.println("Error no se han leido todos los tokens");
		}else {
			System.out.println("Se ha generado el parse correctamente");
		}
		escribirParse();
	}

	private void P0() {
		if(this.tokenActivo == null) {
			return ;
		}
		if(this.tokenActivo.getCod() == 4) {
			this.parse.add(1);
			P();
		}
		else if(this.tokenActivo.getCod() == 7) {
			this.parse.add(1);
			P();
		}
		else if(this.tokenActivo.getCod() == 2) {
			this.parse.add(1);
			P();
		}
		else if(this.tokenActivo.getCod() == 14) {
			this.parse.add(1);
			P();
		}
		else if(this.tokenActivo.getCod() == 8) {
			this.parse.add(1);
			P();
		}
		else if(this.tokenActivo.getCod() == 5) {
			this.parse.add(1);
			P();
		}
		else if(this.tokenActivo.getCod() == 9) {
			this.parse.add(1);
			P();
		}
		else if(this.tokenActivo.getCod() == 3) {
			this.parse.add(1);
			P();
		}
		else if(this.tokenActivo.getCod() == 28) {
			this.parse.add(1);
			P();
		}else {
			System.out.println("Se esperaba una setencia o una funcion.");
		}

	}


	private void P() {
		if(this.tokenActivo.getCod() == 4) {
			this.parse.add(2);
			B();
			P();
		}
		else if(this.tokenActivo.getCod() == 7) {
			this.parse.add(2);
			B();
			P();

		}
		else if(this.tokenActivo.getCod() == 2) {
			this.parse.add(2);
			B();
			P();
		}
		else if(this.tokenActivo.getCod() == 14) {
			this.parse.add(2);
			B();
			P();

		}
		else if(this.tokenActivo.getCod() == 8) {
			this.parse.add(2);
			B();
			P();

		}
		else if(this.tokenActivo.getCod() == 5) {
			this.parse.add(2);
			B();
			P();

		}
		else if(this.tokenActivo.getCod() == 9) {
			this.parse.add(2);
			B();
			P();

		}
		else if(this.tokenActivo.getCod() == 3) {
			this.parse.add(3);
			F();
			P();

		}
		else if(this.tokenActivo.getCod() == 28) {
			this.parse.add(4);
		}else {
			System.out.println("Se esperaba una setencia o una funcion.");
		}

	}

	private void F() {
		if(this.tokenActivo.getCod() == 3) {
			this.parse.add(5);
			this.tokenActivo = getNextToken(); 
			if(this.tokenActivo.getCod() == 14) {
				int pos = Integer.valueOf(this.tokenActivo.getAtr());
				pos-=1;
				this.tokenActivo = getNextToken();
				this.gestorTS.crearTS();
				this.gestorTS.getGestor().get(0).get(pos).setTipo("funcion");
				this.gestorTS.getGestor().get(0).get(pos).setEtiqueta("funcion"+this.nTablas);
				this.tablasFunciones.add(this.gestorTS.buscarLexema(this.tablaActiva, pos));
				int preTabla= 0;
				this.tablaActiva=this.nTablas;
				al.setTablaActiva(this.tablaActiva);
				this.nTablas++;
				this.despL=0;
				H();
				if(this.tokenActivo.getCod() == 19) {
					this.tokenActivo = getNextToken();
					this.al.setZonaDecl(true);
					A();
					if(this.tokenActivo.getCod() == 20) {
						if(this.Htipo!=null) {
							this.gestorTS.getGestor().get(preTabla).get(pos).setRetorno(this.Htipo);
							this.gestorTS.getGestor().get(preTabla).get(pos).setParam(this.gestorTS.getGestor().get(this.tablaActiva).size());
						}
						this.al.setZonaDecl(false);
						this.tokenActivo = getNextToken();
						if(this.tokenActivo.getCod() == 21) {
							this.tokenActivo = getNextToken(); 
							C();
							int nTokenAux=this.nToken;
							if(this.tokenActivo.getCod() == 22) {
								this.tablaActiva= preTabla;
								al.setTablaActiva(preTabla);
								this.tokenActivo = getNextToken(); 
								if(!this.CRet.equals(this.Htipo) && !this.CRet.equals("vacio")) {
									this.gestorErrores.addError(new Errores(this.al.getLinea().get(nTokenAux-2),26,"","semántico"));
									
								}
								
							}
							else {
								this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),6,this.tokenActivo.codeToString(),"sintáctico"));

							}
						}else {
							this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),7,this.tokenActivo.codeToString(),"sintáctico"));
						}
					}else {
						this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),8,this.tokenActivo.codeToString(),"sintáctico"));
					}
				}else {
					this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),9,this.tokenActivo.codeToString(),"sintáctico"));
				}
			}else {
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),10,this.tokenActivo.codeToString(),"sintáctico"));
			}
		}else {
			System.out.println("Error no esperado");
		}

	}
	private void H() {
		if(this.tokenActivo.getCod() == 6) {
			this.parse.add(6);
			T();
			this.Htipo=this.Ttipo;
		}
		else if(this.tokenActivo.getCod() == 1) {
			this.parse.add(6);
			T();	
			this.Htipo=this.Ttipo;
		}
		else if(this.tokenActivo.getCod() == 10) {
			this.parse.add(6);
			T();
			this.Htipo=this.Ttipo;
		}
		else if(this.tokenActivo.getCod() == 19) {
			this.parse.add(7);
			this.Htipo="vacio";
		}else {
			System.out.println("Se esperaba un tipo.");
		}


	}
	private void A() {
		if(this.tokenActivo.getCod() == 6) {
			this.parse.add(8);
			T();
			String TtipoAux=this.Ttipo;
			if(this.tokenActivo.getCod() == 14) {
				int pos = Integer.valueOf(this.tokenActivo.getAtr());
				if(pos<0) {
					pos= pos*(-1);
				}
				pos-=1;
				this.tokenActivo = getNextToken();
				int despAux = this.despL;
				this.despL+= this.Tancho;
				K();
				if(this.gestorTS.buscarTipo(this.tablaActiva, pos)==null) {
					this.gestorTS.getGestor().get(this.tablaActiva).get(pos).setTipo(TtipoAux);
					this.gestorTS.getGestor().get(this.tablaActiva).get(pos).setDesp(despAux);
				}else {
					this.gestorErrores.addError(new Errores(this.al.getLinea().get(this.nToken-1),13,this.gestorTS.buscarLexema(this.tablaActiva, pos),"semántico"));
				}
			}else {
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),10,this.tokenActivo.codeToString(),"sintáctico"));
			}
		}
		else if(this.tokenActivo.getCod() == 1) {
			this.parse.add(8);
			T();
			String TtipoAux=this.Ttipo;
			if(this.tokenActivo.getCod() == 14) {
				int pos = Integer.valueOf(this.tokenActivo.getAtr());
				if(pos<0) {
					pos=(-1)*pos;
				}
				pos-=1;
				this.tokenActivo = getNextToken();
				int despAux = this.despL;
				this.despL+= this.Tancho;
				K();
				if(this.gestorTS.buscarTipo(this.tablaActiva, pos)==null) {
					this.gestorTS.getGestor().get(this.tablaActiva).get(pos).setTipo(TtipoAux);
					this.gestorTS.getGestor().get(this.tablaActiva).get(pos).setDesp(despAux);
				}else {
					this.gestorErrores.addError(new Errores(this.al.getLinea().get(this.nToken-1),13,this.gestorTS.buscarLexema(this.tablaActiva, pos),"semántico"));
				}
			}else {
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),10,this.tokenActivo.codeToString(),"sintáctico"));
			}

		}
		else if(this.tokenActivo.getCod() == 10) {
			this.parse.add(8);
			T();
			String TtipoAux=this.Ttipo;
			if(this.tokenActivo.getCod() == 14) {
				int pos = Integer.valueOf(this.tokenActivo.getAtr());
				if(pos<0) {
					pos= pos*(-1);
				}
				pos-=1;
				this.tokenActivo = getNextToken();
				int despAux = this.despL;
				this.despL+= this.Tancho;
				K();
				
				if(this.gestorTS.buscarTipo(this.tablaActiva, pos)==null) {
					this.gestorTS.getGestor().get(this.tablaActiva).get(pos).setTipo(TtipoAux);
					this.gestorTS.getGestor().get(this.tablaActiva).get(pos).setDesp(despAux);
				}else {
					this.gestorErrores.addError(new Errores(this.al.getLinea().get(this.nToken-1),13,this.gestorTS.buscarLexema(this.tablaActiva, pos),"semántico"));
				}
			}else {
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),10,this.tokenActivo.codeToString(),"sintáctico"));
			}
		}
		else if(this.tokenActivo.getCod() == 20) {
			this.parse.add(9);	
		}else {
			System.out.println("Se esperaba un tipo.");
		}

	}
	private void K() {
		if(this.tokenActivo.getCod() == 17) {
			this.parse.add(10);
			this.tokenActivo = getNextToken();
			T();
			String TtipoAux=this.Ttipo;
			if(this.tokenActivo.getCod() == 14) {
				int pos = Integer.valueOf(this.tokenActivo.getAtr());
				if(pos<0) {
					pos= pos*(-1);
				}
				pos-=1;
				this.tokenActivo = getNextToken();
				int despAux = this.despL;
				this.despL+= this.Tancho;
				K();
				if(this.gestorTS.buscarTipo(this.tablaActiva, pos)==null) {
					this.gestorTS.getGestor().get(this.tablaActiva).get(pos).setTipo(TtipoAux);
					this.gestorTS.getGestor().get(this.tablaActiva).get(pos).setDesp(despAux);
				}else {
					this.gestorErrores.addError(new Errores(this.al.getLinea().get(this.nToken-1),13,this.gestorTS.buscarLexema(this.tablaActiva, pos),"semántico"));
				}
			}else {
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),10,this.tokenActivo.codeToString(),"sintáctico"));

			}
		}
		else if(this.tokenActivo.getCod() == 20) {
			this.parse.add(11);
		}else {
			this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),27,"","sintáctico"));
		}

	}
	private void C() {
		if(this.tokenActivo.getCod() == 4) {
			this.parse.add(12);
			B();
			C();
			if(this.CRet.equals(Bret)) {
				this.CRet=this.Bret;
			}
			else if(this.Bret.equals("vacio")) {
				//Se conserva el tipo de C.
			}
			else if(this.CRet.equals("vacio")){
				this.CRet=this.Bret;
			}else {
				this.CRet=null;
				System.out.println("ERROR SEMANTICO.");
			}
		}
		else if(this.tokenActivo.getCod() == 7) {
			this.parse.add(12);
			B();
			C();	
		}
		else if(this.tokenActivo.getCod() == 2) {
			this.parse.add(12);
			B();
			C();	
		}
		else if(this.tokenActivo.getCod() == 14) {
			this.parse.add(12);
			B();
			C();
		}
		else if(this.tokenActivo.getCod() == 8) {
			this.parse.add(12);
			B();
			C();
		}
		else if(this.tokenActivo.getCod() == 5) {
			this.parse.add(12);
			B();
			C();
		}
		else if(this.tokenActivo.getCod() == 9) {
			this.parse.add(12);
			B();
			C();
		}
		else if(this.tokenActivo.getCod() == 22) {
			this.parse.add(13);
			this.CRet="vacio";
		}else {
			System.out.println("Se esperaba una setencia o función.");
		}


	}
	private void B() {
		if(this.tokenActivo.getCod() == 4) {
			this.parse.add(14);
			this.tokenActivo = getNextToken();
			if(this.tokenActivo.getCod() == 19) {
				this.tokenActivo = getNextToken();
				E();
				String aux = this.Etipo;
				int nTokenAux = this.nToken;
				if(this.tokenActivo.getCod() == 20) {
					this.tokenActivo = getNextToken();
					S();
					if(this.tokenActivo.getCod() == 18) {
						this.tokenActivo = getNextToken();
						if(aux!=null && aux.equals("logico")) {
							this.Bret=this.Sret;
						}else {
							this.Bret=null;
							this.gestorErrores.addError(new Errores(this.al.getLinea().get(nTokenAux-1),14,"","semántico"));
						}
					}else {
						this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),11,this.tokenActivo.codeToString(),"sintáctico"));
					}
				}else {
					this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),8,this.tokenActivo.codeToString(),"sintáctico"));
				}
			}else {
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),9,this.tokenActivo.codeToString(),"sintáctico"));
			}
		}
		else if(this.tokenActivo.getCod() == 7) {
			this.al.setZonaDecl(true);
			this.parse.add(15);
			this.tokenActivo = getNextToken();
			if(this.tokenActivo.getCod() == 14) {
				int pos = Integer.valueOf(this.tokenActivo.getAtr());
				if(pos<0) {
					pos= pos*(-1);
				}
				pos-=1;
				int nTokenAux = this.nToken; 
				this.tokenActivo = getNextToken();
				T();
				if(this.tokenActivo.getCod() == 18) {
					this.al.setZonaDecl(false);
					this.tokenActivo = getNextToken();
					if(this.tablaActiva==0) {
						if(this.gestorTS.buscarTipo(0, pos)==null) {
							this.gestorTS.getGestor().get(0).get(pos).setTipo(this.Ttipo);
							this.gestorTS.getGestor().get(0).get(pos).setDesp(this.despG);
							this.despG+=this.Tancho;
							this.Bret="vacio";
						}else {
							this.Bret=null;
							this.gestorErrores.addError(new Errores(this.al.getLinea().get(nTokenAux-1),15,this.gestorTS.buscarLexema(0, pos),"semántico"));
						}
					}else {
						if(this.gestorTS.buscarTipo(this.tablaActiva, pos)==null) {
							this.gestorTS.getGestor().get(this.tablaActiva).get(pos).setTipo(this.Ttipo);
							this.gestorTS.getGestor().get(this.tablaActiva).get(pos).setDesp(this.despL);
							this.despL+=this.Tancho;
							this.Bret="vacio";
						}else {
							this.Bret=null;
							this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),15,this.gestorTS.buscarLexema(this.tablaActiva, pos),"semántico"));
						}
					}

				}else {
					this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),11,this.tokenActivo.codeToString(),"sintáctico"));

				}
			}else {
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),10,this.tokenActivo.codeToString(),"sintáctico"));
			}
		}
		else if(this.tokenActivo.getCod() == 2) {
			this.parse.add(16);
			this.tokenActivo = getNextToken();
			if(this.tokenActivo.getCod() == 21) {
				this.tokenActivo = getNextToken();
				C();
				if(this.tokenActivo.getCod() == 22) {
					this.tokenActivo = getNextToken();
					if(this.tokenActivo.getCod() == 11) {
						this.tokenActivo = getNextToken();
						if(this.tokenActivo.getCod() == 19) {
							this.tokenActivo = getNextToken();
							E();
							int nTokenAux = this.nToken;
							if(this.tokenActivo.getCod() == 20) {
								this.tokenActivo = getNextToken();
								if(this.tokenActivo.getCod() == 18) {
									this.tokenActivo = getNextToken();
									if(this.Etipo!=null && this.Etipo.equals("logico")) {
										this.CRet=this.Bret;
									}else {
										this.CRet=null;
										this.gestorErrores.addError(new Errores(this.al.getLinea().get(nTokenAux-1),16,"","semántico"));
									}
								}else {
									this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),11,this.tokenActivo.codeToString(),"sintáctico"));

								}
							}else {
								this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),8,this.tokenActivo.codeToString(),"sintáctico"));
							}
						}else {
							this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),9,this.tokenActivo.codeToString(),"sintáctico"));
						}
					}else {
						this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),12,this.tokenActivo.codeToString(),"sintáctico"));
					}

				}else {
					this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),6,this.tokenActivo.codeToString(),"sintáctico"));
				}
			}else {
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),7,this.tokenActivo.codeToString(),"sintáctico"));
			}
		}
		else if(this.tokenActivo.getCod() == 14) {
			this.parse.add(17);
			S();
			if(this.tokenActivo.getCod() == 18) {
				this.tokenActivo = getNextToken(); 
				this.Bret=this.Sret;
			}else {
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),11,this.tokenActivo.codeToString(),"sintáctico"));

			}
		}
		else if(this.tokenActivo.getCod() == 8) {
			this.parse.add(17);
			S();
			if(this.tokenActivo.getCod() == 18) {
				this.tokenActivo = getNextToken();
				this.Bret=this.Sret;
			}else {
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),11,this.tokenActivo.codeToString(),"sintáctico"));

			}
		}
		else if(this.tokenActivo.getCod() == 5) {
			this.parse.add(17);
			S();
			if(this.tokenActivo.getCod() == 18) {
				this.tokenActivo = getNextToken();
				this.Bret=this.Sret;
			}else {
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),11,this.tokenActivo.codeToString(),"sintáctico"));
			}
		}
		else if(this.tokenActivo.getCod() == 9) {
			this.parse.add(17);
			S();
			if(this.tokenActivo.getCod() == 18) {
				this.tokenActivo = getNextToken();
				this.Bret=this.Sret;
			}else {
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),11,this.tokenActivo.codeToString(),"sintáctico"));

			}
		}else {
			System.out.println("Se esperaba una sentencia.");
		}

	}
	private void S() {
		if(this.tokenActivo.getCod() == 14) {
			this.parse.add(18);
			int pos = Integer.valueOf(tokenActivo.getAtr());
			String tabla="global";
			if(pos<0) {
				tabla="local";
				pos=(-1)*pos;
			}
			pos-=1;
			this.tokenActivo = getNextToken();
			S1();
			if(tabla.equals("local")) {
				String tipoAux = this.gestorTS.buscarTipo(tablaActiva, pos);
				if(tipoAux!=null && this.S1tipo!=null&& tipoAux!="funcion" &&  this.S1tipo.equals(tipoAux)) {
					this.Sret="vacio";
				}
				else if(tipoAux != null && this.S1tipo!=null&& this.S1tipo.equals("argumentos")){
					int posFun = buscarFuncion(this.gestorTS.buscarLexema(tablaActiva, pos));
					if(posFun>0 && this.gestorTS.buscarNArg(tablaActiva, pos) == this.LArgm.size() && argumentosIgual(posFun,0)) {
						this.Sret="vacio";
					}else {
						this.Sret= null;
						this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-2),25,this.gestorTS.buscarLexema(this.tablaActiva, pos),"semántico"));
					}
				}else {
					if( this.S1tipo!=null&& !this.S1tipo.equals("argumentos")) {
						if(tipoAux==null) {
							this.gestorTS.gestor.get(0).get(pos).setTipo("entero");
							tipoAux="entero";
							this.gestorTS.gestor.get(0).get(pos).setDesp(this.despG);
							this.despG++;
						}
						if(this.S1tipo==null || !this.S1tipo.equals(tipoAux)) {
							this.Sret=null;
							this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),17,"","semántico"));
						}
					}else {
						this.Sret=null;
						this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),18,this.gestorTS.buscarLexema(tablaActiva, pos),"semántico"));
					}
				}

			}
			else{
				String tipoAux = this.gestorTS.buscarTipo(0, pos);
				if(tipoAux!=null && this.S1tipo!=null && tipoAux!="funcion" &&  this.S1tipo.equals(tipoAux)) {
					this.Sret= "vacio";
				}
				else if(tipoAux != null&& this.S1tipo!=null&& this.S1tipo.equals("argumentos")){
					int posFun = buscarFuncion(this.gestorTS.buscarLexema(0, pos));
					if(posFun>0 && this.gestorTS.buscarNArg(0, pos) == this.LArgm.get(funLlamad).size() && argumentosIgual(posFun,0)) {
						this.Sret= "vacio";
					}else {
						this.Sret= null;
						this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-2),25,this.gestorTS.buscarLexema(0, pos),"semántico"));
					}
				}else {
					if(this.S1tipo!=null && !this.S1tipo.equals("argumentos")) {
						if(tipoAux==null) {
							this.gestorTS.gestor.get(0).get(pos).setTipo("entero");
							tipoAux="entero";
							this.gestorTS.gestor.get(0).get(pos).setDesp(this.despG);
							this.despG++;
						}
						if(this.S1tipo==null || !this.S1tipo.equals(tipoAux)) {
							this.Sret=null;
							this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),17,"","semántico"));
						}
					}else {
						this.Sret=null;
					}
				}
			}
			if(this.funLlamad==0) {
				this.LArgm.clear();
			}
		}
		else if(this.tokenActivo.getCod() == 8) {
			this.parse.add(19);
			this.tokenActivo = getNextToken();
			E();
			if(this.Etipo==null) {
				this.Sret= null;
				System.out.println("ERROR SEMANTICO");
			}
			else if(this.Etipo.equals("logico") || this.Etipo.equals("vacio")) {
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),19,"","semántico"));
				this.Sret= null;
			}else {
				this.Sret="vacio";
			}
		}
		else if(this.tokenActivo.getCod() == 5) {
			this.parse.add(20);
			this.tokenActivo = getNextToken();
			int tabla =0;
			if(this.tokenActivo.getCod() == 14) {
				int pos = Integer.valueOf(this.tokenActivo.getAtr());
				if(pos<0) {
					pos= pos*(-1);
					tabla=this.tablaActiva;
				}
				pos-=1;
				this.tokenActivo = getNextToken();
				if((this.gestorTS.buscarTipo(tabla, pos)==null) || this.gestorTS.buscarTipo(tabla, pos).equals("funcion") || this.gestorTS.buscarTipo(tabla, pos).equals("logico")) {
					if(this.gestorTS.buscarTipo(tabla, pos)==null) {
						this.gestorTS.gestor.get(tabla).get(pos).setTipo("entero");
						this.gestorTS.gestor.get(tabla).get(pos).setDesp(despG);
						this.despG++;
						this.Sret="vacio";
					}else {
						this.Sret=null;
						this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),20,"","semántico"));
					}

				}else {
					this.Sret="vacio";
				}
			}else {
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),10,this.tokenActivo.codeToString(),"sintáctico"));
			}
		}
		else if(this.tokenActivo.getCod() == 9) {
			this.parse.add(21);
			this.tokenActivo = getNextToken();
			X();
			if(this.Xtipo!=null) {
				this.Sret =this.Xtipo;
			}else {
				this.Sret=null;
				System.out.println("ERROR SEMANTICO");
			}
		}else {
			System.out.println("Se esperaba una sentencia.");
		}

	}
	private void S1() {
		if(this.tokenActivo.getCod() == 16) {
			this.parse.add(22);
			this.tokenActivo = getNextToken();
			E();
			this.S1tipo=this.Etipo;
		}
		else if(this.tokenActivo.getCod() == 19) {
			this.parse.add(23);
			this.tokenActivo = getNextToken();
			L();
			if(this.tokenActivo.getCod() == 20) {
				this.tokenActivo = getNextToken();
			}else {
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),8,this.tokenActivo.codeToString(),"sintáctico"));

			}
			this.S1tipo="argumentos";
			
		}
		else if(this.tokenActivo.getCod() == 15) {
			this.parse.add(24);
			this.tokenActivo = getNextToken();
			E();
			if(this.Etipo.equals("entero")) {
				this.S1tipo=this.Etipo;
			}else {
				this.S1tipo=null;
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),21,"","semántico"));
			}
		}else {
			System.out.println("Se esperaba asignacion, asignacion con suma o llamada a una funcion.");
		}

	}
	private void X() {
		if(this.tokenActivo.getCod() == 14) {
			this.parse.add(25);
			E();
			this.Xtipo=this.Etipo;
		}
		else if(this.tokenActivo.getCod() == 19) {
			this.parse.add(25);
			E();
			this.Xtipo=this.Etipo;
		}
		else if(this.tokenActivo.getCod() == 12) {
			this.parse.add(25);
			E();
			this.Xtipo=this.Etipo;
		}
		else if(this.tokenActivo.getCod() == 13) {
			this.parse.add(25);
			E();
			this.Xtipo=this.Etipo;
		}
		else if(this.tokenActivo.getCod() == 26) {
			this.parse.add(25);
			E();
			this.Xtipo=this.Etipo;
		}
		else if(this.tokenActivo.getCod() == 27) {
			this.parse.add(25);
			E();
			this.Xtipo=this.Etipo;
		}
		else if(this.tokenActivo.getCod() == 18) {
			this.parse.add(26);
			this.Xtipo="vacio";
		}else {
			System.out.println("Se esperaba una expresión.");
		}

	}
	private void L() {
		this.LArgm.add(new ArrayList<String>());
		this.funLlamad++;
		if(this.tokenActivo.getCod() == 14) {
			this.parse.add(27);
			int funLlamadAux= this.funLlamad;
			E();
			String ETipoAux = this.Etipo;
			Q(funLlamadAux);
			if(ETipoAux!=null)
				this.LArgm.get(funLlamadAux-1).add(0,ETipoAux);
		}
		else if(this.tokenActivo.getCod() == 19) {
			this.parse.add(27);
			int funLlamadAux= this.funLlamad;
			E();
			String ETipoAux = this.Etipo;
			Q(funLlamadAux);
			if(ETipoAux!=null)
				this.LArgm.get(funLlamadAux-1).add(0,ETipoAux);
		}
		else if(this.tokenActivo.getCod() == 12) {
			this.parse.add(27);
			int funLlamadAux= this.funLlamad;
			E();
			String ETipoAux = this.Etipo;
			Q(funLlamadAux);
			if(ETipoAux!=null)
				this.LArgm.get(funLlamadAux-1).add(0,ETipoAux);
		}
		else if(this.tokenActivo.getCod() == 13) {
			this.parse.add(27);
			int funLlamadAux= this.funLlamad;
			E();
			String ETipoAux = this.Etipo;
			Q(funLlamadAux);
			if(ETipoAux!=null)
				this.LArgm.get(funLlamadAux-1).add(0,ETipoAux);
		}
		else if(this.tokenActivo.getCod() == 26) {
			this.parse.add(27);
			int funLlamadAux= this.funLlamad;
			E();
			String ETipoAux = this.Etipo;
			Q(funLlamadAux);
			if(ETipoAux!=null)
				this.LArgm.get(funLlamadAux-1).add(0,ETipoAux);
		}
		else if(this.tokenActivo.getCod() == 27) {
			this.parse.add(27);
			int funLlamadAux= this.funLlamad;
			E();
			String ETipoAux = this.Etipo;
			Q(funLlamadAux);
			if(ETipoAux!=null)
				this.LArgm.get(funLlamadAux-1).add(0,ETipoAux);
		}
		else if(this.tokenActivo.getCod() == 20) {
			this.parse.add(28);
		}else {
			System.out.println("Se esperaba una expresión.");
		}
		this.funLlamad--;

	}
	private void Q(int funLlamadAux) {
		if(this.tokenActivo.getCod() == 17) {
			this.parse.add(29);
			this.tokenActivo = getNextToken();
			E();
			String ETipoAux = this.Etipo;
			Q(funLlamadAux);
			if(ETipoAux!=null)
				this.LArgm.get(funLlamadAux-1).add(0,ETipoAux);
		}
		else if(this.tokenActivo.getCod() == 20) {
			this.parse.add(30);
		}else {
			System.out.println("Se esperaba una expresión.");
		}

	}
	private void E() {
		if(this.tokenActivo.getCod() == 14) {
			this.parse.add(31);
			R();
			String RTipoAux=this.Rtipo;
			E1();
			if((RTipoAux!=null && this.E1tipo!=null && RTipoAux.equals(this.E1tipo)) || (this.E1tipo!=null && this.E1tipo.equals("ok"))) {
				this.Etipo=RTipoAux;
			}else {
				this.Etipo= null;
				if(this.E1tipo!=null)
					this.gestorErrores.addError(new Errores(this.al.getLinea().get(this.nToken-1),22,"","semántico"));
			}
		}
		else if(this.tokenActivo.getCod() == 19) {
			this.parse.add(31);
			R();
			String RTipoAux=this.Rtipo;
			E1();
			if((RTipoAux!=null && this.E1tipo!=null && RTipoAux.equals(this.E1tipo)) || (this.E1tipo!=null && this.E1tipo.equals("ok"))) {
				this.Etipo=RTipoAux;
			}else {
				this.Etipo= null;
				if(this.E1tipo!=null)
					this.gestorErrores.addError(new Errores(this.al.getLinea().get(this.nToken-1),22,"","semántico"));
			}
		}
		else if(this.tokenActivo.getCod() == 12) {
			this.parse.add(31);
			R();
			String RTipoAux=this.Rtipo;
			E1();
			if((RTipoAux!=null && this.E1tipo!=null && RTipoAux.equals(this.E1tipo)) || (this.E1tipo!=null && this.E1tipo.equals("ok"))) {
				this.Etipo=RTipoAux;
			}else {
				this.Etipo= null;
				if(this.E1tipo!=null)
					this.gestorErrores.addError(new Errores(this.al.getLinea().get(this.nToken-1),22,"","semántico"));
			}
		}
		else if(this.tokenActivo.getCod() == 13) {
			this.parse.add(31);
			R();
			String RTipoAux=this.Rtipo;
			E1();
			if((RTipoAux!=null && this.E1tipo!=null && RTipoAux.equals(this.E1tipo)) || (this.E1tipo!=null && this.E1tipo.equals("ok"))) {
				this.Etipo=RTipoAux;
			}else {
				this.Etipo= null;
				if(this.E1tipo!=null)
					this.gestorErrores.addError(new Errores(this.al.getLinea().get(this.nToken-1),22,"","semántico"));
			}
		}
		else if(this.tokenActivo.getCod() == 26) {
			this.parse.add(31);
			R();
			String RTipoAux=this.Rtipo;
			E1();
			if((RTipoAux!=null && this.E1tipo!=null && RTipoAux.equals(this.E1tipo)) || (this.E1tipo!=null && this.E1tipo.equals("ok"))) {
				this.Etipo=RTipoAux;
			}else {
				this.Etipo= null;
				if(this.E1tipo!=null)
					this.gestorErrores.addError(new Errores(this.al.getLinea().get(this.nToken-1),22,"","semántico"));
			}
		}
		else if(this.tokenActivo.getCod() == 27) {
			this.parse.add(31);
			R();
			String RTipoAux=this.Rtipo;
			E1();
			if((RTipoAux!=null && this.E1tipo!=null && RTipoAux.equals(this.E1tipo)) || (this.E1tipo!=null && this.E1tipo.equals("ok"))) {
				this.Etipo=RTipoAux;
			}else {
				this.Etipo= null;
				if(this.E1tipo!=null)
					this.gestorErrores.addError(new Errores(this.al.getLinea().get(this.nToken-1),22,"","semántico"));
			}
		}else {
			System.out.println("Se esperaba una expresion.");
		}

	}
	private void E1() {
		if(this.tokenActivo.getCod() == 24) {
			this.parse.add(32);
			this.tokenActivo = getNextToken();
			R();
			String RTipoAux = this.Rtipo;
			E1();
			if(this.E1tipo !=null && RTipoAux !=null && (this.E1tipo.equals("ok") || (this.E1tipo !=null && RTipoAux !=null && this.Rtipo.equals(E1tipo) && RTipoAux.equals("logico")))) {
				this.E1tipo= RTipoAux;
			}else {
				this.E1tipo= null;
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(this.nToken-1),22,"","semántico"));
			}

		}
		else if(this.tokenActivo.getCod() == 17) {
			this.parse.add(33);
			this.E1tipo="ok";
		}
		else if(this.tokenActivo.getCod() == 18) {
			this.parse.add(33);
			this.E1tipo="ok";
		}
		else if(this.tokenActivo.getCod() == 20) {
			this.parse.add(33);
			this.E1tipo="ok";
		}else {
			System.out.println("Expresion no válida.");
		}


	}
	private void R() {
		if(this.tokenActivo.getCod() == 14) {
			this.parse.add(34);
			U();
			String UTipoAux = this.Utipo;
			R1();
			if(UTipoAux != null && this.R1tipo != null && UTipoAux.equals("entero") && this.R1tipo.equals("logico")) {
				this.Rtipo = "logico"; 
			}else if(this.R1tipo != null && this.R1tipo.equals("ok")) {
				this.Rtipo = UTipoAux; 
			}else {
				this.Rtipo=null;
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(this.nToken-1),23,"","semántico"));
			}
		}
		else if(this.tokenActivo.getCod() == 19) {
			this.parse.add(34);
			U();
			String UTipoAux = this.Utipo;
			R1();
			if(UTipoAux != null && this.R1tipo != null && UTipoAux.equals("entero") && this.R1tipo.equals("logico")) {
				this.Rtipo = "logico"; 
			}else if(this.R1tipo != null && this.R1tipo.equals("ok")) {
				this.Rtipo = UTipoAux; 
			}else {
				this.Rtipo=null;
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(this.nToken-1),23,"","semántico"));
			}
		}
		else if(this.tokenActivo.getCod() == 12) {
			this.parse.add(34);
			U();
			String UTipoAux = this.Utipo;
			R1();
			if(UTipoAux != null && this.R1tipo != null && UTipoAux.equals("entero") && this.R1tipo.equals("logico")) {
				this.Rtipo = "logico"; 
			}else if(this.R1tipo != null && this.R1tipo.equals("ok")) {
				this.Rtipo = UTipoAux; 
			}else {
				this.Rtipo=null;
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(this.nToken-1),23,"","semántico"));
			}
		}
		else if(this.tokenActivo.getCod() == 13) {
			this.parse.add(34);
			U();
			String UTipoAux = this.Utipo;
			R1();
			if(UTipoAux != null && this.R1tipo != null && UTipoAux.equals("entero") && this.R1tipo.equals("logico")) {
				this.Rtipo = "logico"; 
			}else if(this.R1tipo != null && this.R1tipo.equals("ok")) {
				this.Rtipo = UTipoAux; 
			}else {
				this.Rtipo=null;
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(this.nToken-1),23,"","semántico"));
			}
		}
		else if(this.tokenActivo.getCod() == 26) {
			this.parse.add(34);
			U();
			String UTipoAux = this.Utipo;
			R1();
			if(UTipoAux != null && this.R1tipo != null && UTipoAux.equals("entero") && this.R1tipo.equals("logico")) {
				this.Rtipo = "logico"; 
			}else if(this.R1tipo != null && this.R1tipo.equals("ok")) {
				this.Rtipo = UTipoAux; 
			}else {
				this.Rtipo=null;
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(this.nToken-1),23,"","semántico"));
			}
		}
		else if(this.tokenActivo.getCod() == 27) {
			this.parse.add(34);
			U();
			String UTipoAux = this.Utipo;
			R1();
			if(UTipoAux != null && this.R1tipo != null && UTipoAux.equals("entero") && this.R1tipo.equals("logico")) {
				this.Rtipo = "logico"; 
			}else if(this.R1tipo != null && this.R1tipo.equals("ok")) {
				this.Rtipo = UTipoAux; 
			}else {
				this.Rtipo=null;
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(this.nToken-1),23,"","semántico"));
			}
		}else {
			System.out.println("Se esperaba una expresión.");
		}

	}
	private void R1() {
		if(this.tokenActivo.getCod() == 25) {
			this.parse.add(35);
			this.tokenActivo = getNextToken();
			U();
			String UTipoAux = this.Utipo;
			R1();
			if(this.R1tipo !=null && UTipoAux  !=null && (this.R1tipo.equals("ok") || (UTipoAux .equals("entero")) && this.R1tipo.equals("logico"))) {
				this.R1tipo= "logico";
			}else {
				this.R1tipo= null;
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(this.nToken-1),23,"","semántico"));
			}
		}
		else if(this.tokenActivo.getCod() == 24) {
			this.parse.add(36);
			this.R1tipo="ok";
		}
		else if(this.tokenActivo.getCod() == 17) {
			this.parse.add(36);
			this.R1tipo="ok";
		}
		else if(this.tokenActivo.getCod() == 18) {
			this.parse.add(36);
			this.R1tipo="ok";
		}
		else if(this.tokenActivo.getCod() == 20) {
			this.parse.add(36);
			this.R1tipo="ok";
		}else {
			System.out.println("Expresion no válida.");
		}

	}
	private void U() {
		if(this.tokenActivo.getCod() == 14) {
			this.parse.add(37);
			V();
			String VTipoAux = this.Vtipo;
			U1();
			if((VTipoAux!=null && this.U1tipo!=null && VTipoAux.equals(this.U1tipo)) || (this.U1tipo!=null && this.U1tipo.equals("ok"))) {
				this.Utipo=VTipoAux;
			}else {
				this.Utipo= null;
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(this.nToken-1),24,"","semántico"));
			}
		}
		else if(this.tokenActivo.getCod() == 19) {
			this.parse.add(37);
			V();
			String VTipoAux = this.Vtipo;
			U1();
			if((VTipoAux!=null && this.U1tipo!=null && VTipoAux.equals(this.U1tipo)) || (this.U1tipo!=null && this.U1tipo.equals("ok"))) {
				this.Utipo=VTipoAux;
			}else {
				this.Utipo= null;
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(this.nToken-1),24,"","semántico"));			
			}
		}
		else if(this.tokenActivo.getCod() == 12) {
			this.parse.add(37);
			V();
			String VTipoAux = this.Vtipo;
			U1();
			if((VTipoAux!=null && this.U1tipo!=null && VTipoAux.equals(this.U1tipo)) || (this.U1tipo!=null && this.U1tipo.equals("ok"))) {
				this.Utipo=VTipoAux;
			}else {
				this.Utipo= null;
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(this.nToken-1),24,"","semántico"));			
			}
		}
		else if(this.tokenActivo.getCod() == 13) {
			this.parse.add(37);
			V();
			String VTipoAux = this.Vtipo;
			U1();
			if((VTipoAux!=null && this.U1tipo!=null && VTipoAux.equals(this.U1tipo)) || (this.U1tipo!=null && this.U1tipo.equals("ok"))) {
				this.Utipo=VTipoAux;
			}else {
				this.Utipo= null;
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(this.nToken-1),24,"","semántico"));			
			}
		}
		else if(this.tokenActivo.getCod() == 26) {
			this.parse.add(37);
			V();
			String VTipoAux = this.Vtipo;
			U1();
			if((VTipoAux!=null && this.U1tipo!=null && VTipoAux.equals(this.U1tipo)) || (this.U1tipo!=null && this.U1tipo.equals("ok"))) {
				this.Utipo=VTipoAux;
			}else {
				this.Utipo= null;
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(this.nToken-1),24,"","semántico"));			
			}
		}
		else if(this.tokenActivo.getCod() == 27) {
			this.parse.add(37);
			V();
			String VTipoAux = this.Vtipo;
			U1();
			if((VTipoAux!=null && this.U1tipo!=null && VTipoAux.equals(this.U1tipo)) || (this.U1tipo!=null && this.U1tipo.equals("ok"))) {
				this.Utipo=VTipoAux;
			}else {
				this.Utipo= null;
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(this.nToken-1),24,"","semántico"));			
			}
		}else {
			System.out.println("Se esperaba una expresión.");
		}

	}
	private void U1() {
		if(this.tokenActivo.getCod() == 23) {
			this.parse.add(38);
			this.tokenActivo = getNextToken();
			V();
			String VTipoAux= this.Vtipo;
			U1();
			if(this.U1tipo !=null && VTipoAux !=null && (this.U1tipo.equals("ok") || (VTipoAux.equals(U1tipo) && VTipoAux.equals("entero")))) {
				this.U1tipo= VTipoAux;
			}else {
				this.U1tipo= null;
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(this.nToken-1),24,"","semántico"));			
			}
		}
		else if(this.tokenActivo.getCod() == 25) {
			this.parse.add(39);
			this.U1tipo="ok";
		}
		else if(this.tokenActivo.getCod() == 24) {
			this.parse.add(39);
			this.U1tipo="ok";
		}
		else if(this.tokenActivo.getCod() == 17) {
			this.parse.add(39);
			this.U1tipo="ok";
		}
		else if(this.tokenActivo.getCod() == 18) {
			this.parse.add(39);
			this.U1tipo="ok";
		}
		else if(this.tokenActivo.getCod() == 20) {
			this.parse.add(39);
			this.U1tipo="ok";
		}else {
			System.out.println("Expresion no válida.");
		}


	}
	private void V() {
		if(this.tokenActivo.getCod() == 14) {
			this.parse.add(40);
			int pos = Integer.valueOf(tokenActivo.getAtr());
			String tabla="global";
			if(pos<0) {
				tabla="local";
				pos=(-1)*pos;
			}
			pos-=1;
			this.tokenActivo = getNextToken();
			V1();
			if(tabla.equals("local")) {
				String tipoAux = this.gestorTS.buscarTipo(tablaActiva, pos);
				if(tipoAux!=null && tipoAux!="funcion") {
					this.Vtipo= tipoAux;
				}
				else if(tipoAux != null){
					int posFun = buscarFuncion(this.gestorTS.buscarLexema(tablaActiva, pos));
					if(posFun>0 && this.gestorTS.buscarNArg(tablaActiva, pos) == this.V1Argm.size() && argumentosIgual(posFun,1)) {
						this.Vtipo = this.gestorTS.buscarTipoReturn(this.tablaActiva, pos);
					}else {
						this.Vtipo=null;
						this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-2),25,this.gestorTS.buscarLexema(this.tablaActiva, pos),"semántico"));
					}
				}else {
					if(this.V1tipo.equals("ok")) {
						this.gestorTS.gestor.get(0).get(pos).setTipo("entero");
						this.gestorTS.gestor.get(0).get(pos).setDesp(this.despG);
						this.despG++;
						this.Vtipo="entero";
					}else{
						this.Vtipo=null;
						this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),18,this.gestorTS.buscarLexema(tablaActiva, pos),"semántico"));
					}

				}

			}
			else{
				String tipoAux = this.gestorTS.buscarTipo(0, pos);
				if(tipoAux!=null && tipoAux!="funcion") {
					this.Vtipo= tipoAux;
				}
				else if(tipoAux != null){
					int posFun = buscarFuncion(this.gestorTS.buscarLexema(0, pos));
					if(posFun>0 && this.gestorTS.buscarNArg(0, pos) == this.V1Argm.size() && argumentosIgual(posFun,1)) {
						this.Vtipo = this.gestorTS.buscarTipoReturn(0, pos);;
					}else {
						this.Vtipo=null;
						this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-2),25,this.gestorTS.buscarLexema(0, pos),"semántico"));
					}
				}else {
					if(this.V1tipo.equals("ok")) {
						this.gestorTS.gestor.get(0).get(pos).setTipo("entero");
						this.gestorTS.gestor.get(0).get(pos).setDesp(this.despG);
						this.despG++;
						this.Vtipo="entero";
					}else{
						this.Vtipo=null;
						this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),18,this.gestorTS.buscarLexema(tablaActiva, pos),"semántico"));
					}
				}
				this.V1Argm.clear();
			}

		}
		else if(this.tokenActivo.getCod() == 19) {
			this.parse.add(41);
			this.tokenActivo = getNextToken();
			E();
			if(this.tokenActivo.getCod() == 20) {
				this.tokenActivo = getNextToken();
			}else {
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),8,this.tokenActivo.codeToString(),"sintáctico"));
			}
			if(this.Etipo != null)
				this.Vtipo=this.Etipo;
			else {
				this.Vtipo=null;
				System.out.println("Error Semántico");//Mejorar error (Linea)
			}

		}
		else if(this.tokenActivo.getCod() == 12) {
			this.parse.add(42);
			this.Vtipo="entero";
			this.tokenActivo = getNextToken();
		}
		else if(this.tokenActivo.getCod() == 13) {
			this.parse.add(43);
			this.Vtipo="cadena";
			this.tokenActivo = getNextToken();
		}
		else if(this.tokenActivo.getCod() == 26) {
			this.parse.add(44);
			this.Vtipo="logico";
			this.tokenActivo = getNextToken();
		}
		else if(this.tokenActivo.getCod() == 27) {
			this.parse.add(45);
			this.Vtipo="logico";
			this.tokenActivo = getNextToken();
		}else {
			System.out.println("Se esperaba una expresión.");
		}

	}

	private void V1() {
		if(this.tokenActivo.getCod() == 19) {
			this.parse.add(46);
			this.tokenActivo = getNextToken();
			L();
			if(this.tokenActivo.getCod() == 20) {
				this.tokenActivo = getNextToken();
			}else {
				this.gestorErrores.addError(new Errores(this.al.getLinea().get(nToken-1),8,this.tokenActivo.codeToString(),"sintáctico"));
			}
			this.V1Argm = this.LArgm.get(this.funLlamad);
			this.V1tipo="argumento";
			
			if(this.funLlamad==0) {
				this.LArgm.clear();
			}
		}
		else if(this.tokenActivo.getCod() == 23) {
			this.V1tipo="ok";
			this.parse.add(47);
		}
		else if(this.tokenActivo.getCod() == 25) {
			this.V1tipo="ok";
			this.parse.add(47);
		}
		else if(this.tokenActivo.getCod() == 24) {
			this.V1tipo="ok";
			this.parse.add(47);

		}
		else if(this.tokenActivo.getCod() == 17) {
			this.V1tipo="ok";
			this.parse.add(47);

		}
		else if(this.tokenActivo.getCod() == 18) {
			this.V1tipo="ok";
			this.parse.add(47);

		}
		else if(this.tokenActivo.getCod() == 20) {
			this.V1tipo="ok";
			this.parse.add(47);
		}
		else {
			System.out.println("Expresión no valida.");
		}

	}
	private void T() {
		if(this.tokenActivo.getCod() == 6) {
			this.parse.add(48);
			this.Ttipo="entero";
			this.Tancho= 1;
			this.tokenActivo = getNextToken();
		}
		else if(this.tokenActivo.getCod() == 1) {
			this.parse.add(49);
			this.Ttipo="logico";
			this.Tancho= 1;
			this.tokenActivo = getNextToken();
		}
		else if(this.tokenActivo.getCod() == 10) {
			this.parse.add(50);
			this.Ttipo="cadena";
			this.Tancho= 64;
			this.tokenActivo = getNextToken();
		}else {
			System.out.println("Se esperaba un tipo.");
		}


	}


	private Token getNextToken() {
		Token siguiente = al.sigToken();
		while(siguiente==null && !this.fin) {
			siguiente=al.sigToken();
		}
		this.nToken++;
		return siguiente;
	}


	private void escribirParse(){
		String nombre = resultsPath + "\\parse"+nPrueba+".txt";
		File fichero = new File(nombre);
		try {
			if (fichero.exists()) {
				fichero.delete();
			}
			fichero.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(nombre));
			bw.write("Descendente ");
			for(int i=0; i<this.parse.size();i++)
				bw.write(this.parse.get(i) + " ");
			bw.close();
		} catch (IOException e) {
		}	
	}


	private int buscarFuncion(String nombre) {
		for(int i=0; i<this.tablasFunciones.size();i++) {
			if(this.tablasFunciones.get(i).equals(nombre)) {
				return i+1;
			}
		}
		return -1;
	}


	private boolean argumentosIgual(int tabla,int tipo) {
		ArrayList<String> listaAux;
		if(tipo==0) {
			listaAux=this.LArgm.get(this.funLlamad);
		}else {
			listaAux=this.V1Argm;
		}
		boolean iguales = true;

		for(int i=0; i<listaAux.size(); i++) {
			if(this.gestorTS.buscarTipo(tabla, i)== null || !listaAux.get(i).equals(this.gestorTS.buscarTipo(tabla, i)))
				return false; 
		}
		return iguales;
	}

}
