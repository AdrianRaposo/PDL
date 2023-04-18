package analizador_pdl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GestorTS {

	ArrayList<ArrayList<ItemTS>> gestor;
	private String resultsPath;
	private String nPrueba;


	public GestorTS(String resultsPath, String nPrueba) {
		this.gestor = new ArrayList<>();
		gestor.add(new ArrayList<ItemTS>());
		this.resultsPath = resultsPath;
		this.nPrueba=nPrueba;
	}

	public void crearTS() {
		gestor.add(new ArrayList<ItemTS>());
	}

	public void anadirId(int ts, String nombre ){
		if(ts<gestor.size()) {
			gestor.get(ts).add(new ItemTS(nombre));
		}else {
			System.out.println("No se ha podido registrar el identificador;");
		}
	}
	public int buscarId(String nombre, int tabla) {
		int  posicion = -1;
		boolean encontrado=false;
		for(int j =0; j<gestor.get(tabla).size() && !encontrado ;j++) {
			if(gestor.get(tabla).get(j).getNombre().equals(nombre)) {
				posicion = j;
				encontrado=true;
			}
		}

		return posicion;

	}

	public String buscarTipo(int tabla, int pos) {
		return  this.gestor.get(tabla).get(pos).getTipo();
	}


	public String buscarTipoReturn(int tabla, int pos) {
		return this.gestor.get(tabla).get(pos).getRetorno();
	}
	public String buscarLexema(int tabla, int pos) {
		return this.gestor.get(tabla).get(pos).getNombre();
	}
	public int buscarNArg(int tabla, int pos) {
		return this.gestor.get(tabla).get(pos).getParam();
	}

	public ArrayList<ArrayList<ItemTS>> getGestor(){
		return gestor;
	}

	public void generarTS(){
		System.out.println("Creando tabla de s�mbolos...");
		String nombre = resultsPath + "\\ts"+nPrueba+".txt";
		File fichero = new File(nombre);
		try {
			if (fichero.exists()) {
				fichero.delete();
			}
			fichero.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(nombre));
			for (int i = 0; i<gestor.size(); i++) {
				if(i == 0) {
					bw.write("CONTENIDOS DE LA TABLA GLOBAL # "+(i+1) + " : \n\n");
				}else {
					bw.write("CONTENIDOS DE LA TABLA LOCAL # "+(i+1) + " : \n\n");
				}
				int contfunciones =0;
				for(int j =0; j<gestor.get(i).size();j++) {

					bw.write("* LEXEMA : '" + gestor.get(i).get(j).getNombre() + "'\n");
					bw.write("  Atributos :\n");
					if(gestor.get(i).get(j).getTipo() == null || !gestor.get(i).get(j).getTipo().equals("funcion")) {
						if(gestor.get(i).get(j).getTipo() == null) {
							bw.write("  + tipo : ''\n");
							bw.write("  + despl : \n");
						}else {
							bw.write("  + tipo : '" + gestor.get(i).get(j).getTipo() + "'\n");
							bw.write("  + despl : " + gestor.get(i).get(j).getDesp() + "\n");
						}
					}else {
						contfunciones++;
						bw.write("  + tipo : '" + gestor.get(i).get(j).getTipo() + "'\n");
						bw.write("  + numParam : " + gestor.get(i).get(j).getParam() + "\n");
						for(int k=0; k<gestor.get(i).get(j).getParam();k++) {
							bw.write("  + tipoParam"+(k+1)+" : '" + gestor.get(contfunciones).get(k).getTipo() + "'\n");
						}
						bw.write("  + tipoRetorno : '" + gestor.get(i).get(j).getRetorno() + "'\n");
						bw.write("  + etiqFuncion : '" + gestor.get(i).get(j).getEtiqueta() + "'\n");
					}
					bw.write("  --------- ----------\n");
				}

			}

			bw.close();
			System.out.println("Tabla de s�mbolos creada con exito.");
		} catch (IOException e) {
			System.err.println("Error al crear la tabla de s�mbolos.");

		}	

	}
}


