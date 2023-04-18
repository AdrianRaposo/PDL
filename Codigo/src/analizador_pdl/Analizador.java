package analizador_pdl;

import java.io.File;
import static java.nio.file.StandardCopyOption.*;
import java.nio.file.Files;

public class Analizador {
	private String nPrueba;
	public Analizador(String nPrueba) {
		this.nPrueba=nPrueba;
		
	}

	public void start () {
		String directoryName = "PruebasAnalizador";
		GestorTS gestorTS = new GestorTS(directoryName,nPrueba);
		GestorErrores gestorErrores = new GestorErrores(directoryName, nPrueba); 
		File directorio = new File(directoryName);
		if (!directorio.exists()) {
			if (directorio.mkdirs()) {
				System.out.println("Directorio creado");

			} else {
				System.out.println("Error al crear directorio");
			}
		}else {

			System.out.println("El directorio que intentas crear ya existe.");
		}
		Analizador_Lexico al = new Analizador_Lexico(directoryName, nPrueba , "src//analizador_pdl//PruebasTexto.txt", gestorTS, gestorErrores);
		Analizador_Sintactico_Semantico aSinSem = new Analizador_Sintactico_Semantico(directoryName, nPrueba ,al,gestorErrores, gestorTS);
		String destinationPath=directoryName+"\\Prueba"+nPrueba+".txt";  // destination file path
		File sourceFile = new File("src//analizador_pdl//PruebasTexto.txt");        // Creating A Source File
		File destinationFile = new File(destinationPath);   //Creating A Destination File. Name stays the same this way, referring to getName()
		System.out.println("Copiando archivo prueba...");
		try 
		{
			Files.copy(sourceFile.toPath(), destinationFile.toPath(),REPLACE_EXISTING);
			System.out.println("Copiado con exito al directorio.");
			// Static Methods To Copy Copy source path to destination path
		} catch(Exception e){
			
			System.out.println("Error copiando el archivo Prueba" + nPrueba + ".txt");  // printing in case of error.
		}
		aSinSem.generarParse();
		al.generarTokens();
		gestorTS.generarTS();
		gestorErrores.crearErrores();

	}
}	