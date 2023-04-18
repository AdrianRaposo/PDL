package analizador_pdl;

import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;



public class Gui extends JFrame  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton selec;
	private JButton analizar;
	private JButton save;
	private JButton analisis;
	private JTextArea textArea ;
	private boolean guardado=false, analizado=false;
	private String nombre="";
	private JLabel label;

	public Gui() {
		// Crea una etiqueta y un botón
		selec = new JButton();
		analizar= new JButton();
		textArea = new JTextArea();
		save= new JButton();
		analisis = new JButton();


		analisis.setIcon(new ImageIcon("src//analizador_pdl//GuiFiles\\analisis.png"));
		analisis.setBounds(0,410,50,50);
		analisis.addActionListener(event -> openNewWindow());
		analisis.setToolTipText("Ver analisis");
		analisis.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		save.setIcon(new ImageIcon("src//analizador_pdl//GuiFiles\\guardar.png"));
		save.setBounds(0,100,50,50);
		save.addActionListener(event -> saveFile());
		save.setToolTipText("Guardar código");
		save.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
		textArea.setBackground(Color.lightGray);
		JScrollPane scrollPane= new JScrollPane(textArea);
		BufferedReader reader;
		scrollPane.setBounds(50,0,445,480);
		try {
			reader = new BufferedReader(new FileReader("src//analizador_pdl//PruebasTexto.txt"));
			String linea;
			while ((linea = reader.readLine()) != null) {
				textArea.append(linea + "\n");
			}
			reader.close();
		} catch (Exception e) {
			//Error al leer el archivo
		}

		label = new JLabel();
		label.setIcon(new ImageIcon("src//analizador_pdl//GuiFiles\\drag.png"));
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
		label.setBorder(border);
		label.setToolTipText("Arrastrar archivo (.txt)");

		label.setDropTarget(new DropTarget() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public synchronized void drop(DropTargetDropEvent evt) {
				try {
					// Obtiene el archivo arrastrado

					if (evt.getTransferable().isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
						// Obtiene el archivo arrastrado
						evt.acceptDrop(DnDConstants.ACTION_COPY);
						List<File> files = (List<File>)evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
						File file = files.get(0);

						if (file.getName().endsWith(".txt")) {
							textArea.setText("");
							BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
							String linea;

							while ((linea = reader.readLine()) != null) {

								textArea.append(linea + "\n");
							}
							reader.close();
						} else {
							JOptionPane.showMessageDialog(null, "Solo se aceptan archivos de texto", "Error", JOptionPane.ERROR_MESSAGE);

						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		label.setBounds(2,155,45,100);   

		selec.addActionListener(event -> seleccionarArchivo());
		selec.setIcon(new ImageIcon("src//analizador_pdl//GuiFiles//carpeta-vacia.png"));
		selec.setBounds(0,0,50,50);
		selec.setToolTipText("Sleccionar archivo (.txt)");
		selec.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


		analizar.setIcon(new ImageIcon("src//analizador_pdl//GuiFiles\\lupa.png"));
		analizar.setBounds(0,50,50,50);
		analizar.addActionListener(event -> analizar());
		analizar.setToolTipText("Analizar código");
		analizar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		// Crea un panel y establece el layout BoxLayout
		JPanel panel = new JPanel(null);
		add(panel);
		// Agrega los botones al panel
		panel.add(selec);
		panel.add(analizar);
		panel.add(scrollPane);
		panel.add(this.save);
		panel.add(this.analisis);
		panel.add(label);


		setTitle("Analizador Javascript-Pdl");
		setSize(500, 510);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);	
	}
	private void openNewWindow() {
		if(!this.analizado){
			JOptionPane.showMessageDialog(this, "Tienes que analizar el codigo antes.", "Error", JOptionPane.ERROR_MESSAGE);
		}else {
			String directoryName ="PruebasAnalizador";

			JFrame newWindow = new JFrame();
			JButton tokens = new JButton("TOKENS");
			JButton parse= new JButton("PARSE");
			JButton ts= new JButton("TABLA DE SIMBOLOS");
			JButton prueba= new JButton("PRUEBA");
			JButton errores = new JButton("ERRORES");
			JTextArea resultados = new JTextArea("Selecciona el archivo que deseas ver.");
			JScrollPane scrollPane;

			resultados.setEditable(false);
			resultados.setBackground(Color.lightGray);
			resultados.setLineWrap(true);

			tokens.setBounds(0,20,100,20);
			tokens.addActionListener(event -> this.botonesVentana(directoryName+"\\tokens"+this.nombre+".txt", resultados));
			tokens.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			ts.setBounds(0,40,100,20);
			ts.addActionListener(event -> this.botonesVentana(directoryName+"\\ts"+this.nombre+".txt", resultados));
			ts.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			parse.setBounds(0,60,100,20);
			parse.addActionListener(event -> this.botonesVentana(directoryName+"\\parse"+this.nombre+".txt", resultados));
			parse.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			prueba.setBounds(0,0,100,20);
			prueba.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			prueba.addActionListener(event -> this.botonesVentana(directoryName+"\\Prueba"+this.nombre+".txt", resultados));
			errores.setBounds(0,80,100,20);
			errores.addActionListener(event -> this.botonesVentana(directoryName+"\\errores"+this.nombre+".txt", resultados));
			errores.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			scrollPane= new JScrollPane(resultados);
			scrollPane.setBounds(100,10,375,450);



			JPanel panel = new JPanel(null);
			newWindow.add(panel);
			// Agrega los botones al panel
			panel.add(prueba);
			panel.add(tokens);
			panel.add(ts);
			panel.add(parse);
			panel.add(errores);
			panel.add(scrollPane);


			newWindow.setTitle("Analisis");
			newWindow.setResizable(false);
			newWindow.setSize(500, 500);
			newWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			newWindow.setVisible(true);
		}
	}
	private void saveFile() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("src//analizador_pdl//PruebasTexto.txt",false));
			String text = textArea.getText();
			String[] lines = text.split("\n");
			for (String line : lines) {
				writer.write(line);
				writer.newLine();
			}

			writer.close();
			this.guardado=true;
		}catch(Exception e2) {

		}
	}
	public void analizar() {
		if(!this.guardado) {
			JOptionPane.showMessageDialog(this, "Tienes que guardar el codigo antes.", "Error", JOptionPane.ERROR_MESSAGE);
		}else {
			String input = JOptionPane.showInputDialog(this, "Introduce el nombre con el que quieras guardar el análisis.\n(Se sobrescribirá si ya existe el analisis).");
			this.nombre=input;
			JOptionPane.showMessageDialog(this, "Se guradará como: " + input);
			Analizador an = new Analizador(input);
			an.start();
			this.analizado=true;
		}
		this.guardado=false;

	}

	public void seleccionarArchivo() {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto (\"(*.txt)\" )", "txt");
		fileChooser.setFileFilter(filter);
		int returnValue = fileChooser.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			this.textArea.setText("");
			File selectedFile = fileChooser.getSelectedFile();
			try {
				BufferedReader reader = new BufferedReader(new FileReader( selectedFile.getAbsolutePath()));
				String linea;

				while ((linea = reader.readLine()) != null) {

					textArea.append(linea + "\n");
				}
				reader.close();
			} catch(Exception e1){

			}
		}

	}
	public void botonesVentana(String path, JTextArea jtext) {
		try {
			jtext.setText("");
			BufferedReader reader = new BufferedReader(new FileReader( path));
			String linea;
			while ((linea = reader.readLine()) != null) {
				jtext.append(linea + "\n");
			}
			reader.close();
		} catch(Exception e1){

		}

	}


	public static void main(String[] args) {
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
		}
		Gui gui=new Gui();
		gui.setVisible(true);
	}
}
