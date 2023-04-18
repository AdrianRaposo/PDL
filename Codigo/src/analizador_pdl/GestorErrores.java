package analizador_pdl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GestorErrores {
	private ArrayList<Errores> gestor;
	private  String path;
	private  String  nPrueba;;

	public GestorErrores(String path, String nPrueba) {
		this.gestor = new ArrayList<>();
		this.path = path;
		this.nPrueba = nPrueba;
	}

	public void addError(Errores error) {
		gestor.add(error);
	}

	public void crearErrores() {
		System.out.println("Creando fichero errores...");
		String nombre = path + "\\errores"+nPrueba+".txt";
		File fichero = new File(nombre);
		try {
			if (fichero.exists()) {
				fichero.delete();
			}
			fichero.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(nombre));
			if(gestor.size() == 0) {
				bw.write("No se ha detectado ning�n error.\n");
			}else {
				for(int i =0 ; i< gestor.size(); i++) {
					bw.write(gestor.get(i).toString()+ "\n");
				}
			}
			bw.close();
			System.out.println("Fichero errores creado con exito.");

		}catch(IOException e) {
			System.err.println("Error al crear fichero errores.");
		}
	}

}
