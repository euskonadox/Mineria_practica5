package rf;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import weka.core.Instances;

public class FileManager {
	
	private Instances instancias = null;
	
	public FileManager(){}
	
	public Instances loadData(String pPath)
	{
		String path=pPath;
		FileReader fichero=null;
		
		try {
			fichero= new FileReader(path);
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: Revisar ruta del fichero de datos:"+path);
		}
		
		// Load the instances
		try {
			instancias = new Instances(fichero);
		} catch (IOException e) {
			System.out.println("ERROR: Revisar contenido del fichero de datos: "+path);
		}
		
		// Close the file
		try {
			fichero.close();
		} catch (IOException e) {
			System.out.println("ERROR: Problema al cerrar el archivo: "+path);
		}
		
		
		instancias.setClassIndex(instancias.numAttributes()-1);
		return instancias;
	}
	
	//Needed?
	//Save test data with class
	public void saveData(Instances instancesToSafe,String fileName){
		 try {
			 //Creating new file and saving Test values with class
			 BufferedWriter writer = new BufferedWriter(new FileWriter("./"+fileName+".arff"));
			 writer.write(instancesToSafe.toString());
			 writer.flush();
			 writer.close();
			 System.out.println("Archivo creado con los resultados ");
		} catch (IOException e) {
			// Auto-generated catch block
			System.out.println("ERROR: No se pudo crear el archivo");
		}

	}
	
	
}
