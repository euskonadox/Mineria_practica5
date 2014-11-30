package org.mdd.practica5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;


import java.io.FileWriter;
import java.io.IOException;

import weka.core.Instances;

public class FileManager {
	
	public FileManager(){}
	
	
	public Instances loadData(String inPath) throws Exception
	{
		    // read arff file
		    Instances instancias = new Instances(new BufferedReader(new FileReader(inPath)));
		    instancias.setClassIndex(instancias.numAttributes() - 1);
		return instancias;
	}
	
	//Save instances in an arff file
	
	public void saveData(Instances instancias, String outPath) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(outPath));
	    writer.write(instancias.toString());
	    writer.newLine();
	    writer.flush();
	    writer.close();
	}
}