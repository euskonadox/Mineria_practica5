package org.mdd.practica5;

import weka.core.Instances;

public class Main {
	
	public static void main(String[] args) throws Exception {
		
		int numParams = args.length;
		String fileInputPath=null;
		String fileOutputPath=null;
		String tipoFiltro="SMOTE";
		double porcentaje=100;
		String valorClase = "0";
		Instances data, newData=null;

		if(numParams==0)
		{
			showIntro();
		}
		else
		{
			
			//Open and load data
			FileManager file = new FileManager();
			
			
			try{
				for (int parameter = 0; parameter < numParams; parameter++) {
					//File path
					if (args[parameter].equals("-i")) {
						parameter++;
						fileInputPath = args[parameter];
					}
					
					if (args[parameter].equals("-o")) {
						parameter++;
						fileOutputPath = args[parameter];
					}
					
					if (args[parameter].equals("-t")) {
						parameter++;
						tipoFiltro = args[parameter];
					}
					
					if (args[parameter].equals("-p")) {
						parameter++;
						porcentaje = Double.parseDouble(args[parameter]);
					}
					
					if (args[parameter].equals("-c")) {
						parameter++;
						valorClase = args[parameter];
					}
				}
				
				data = file.loadData(fileInputPath);
				
				BalanceInstances balanceo = new BalanceInstances();
				
				if (tipoFiltro.equals("SMOTE"))
				{
					newData = balanceo.aplicarSMOTE(data, porcentaje, valorClase);
				}
				
				if (tipoFiltro.equals("Resample"))
				{
					newData = balanceo.aplicarResample(data, porcentaje);
				}
				
				//Save modified data
				file.saveData(newData, fileOutputPath);
			} catch (Exception e) {
				System.out.println("========================");
				System.out.println("Some of the parameters don't match the requierements");
				System.out.println("The aplication will exit now.");
			}
		}			
	}
	
	private static void showIntro()
	{
		
		System.out.println("Filters to balance instances.");
		System.out.println("-------------------------------------------------------");
		System.out.println("These are the available parameters.");
		System.out.println("Check the instructions file to know available options for each parameter.");
		System.out.println("Parameters:");
		System.out.println("[-i path] Path to arff input file (Required).");
		System.out.println("[-o path] Path to arff output file (Required).");
		System.out.println("[-t String] The filter to balance instances. It can be 'SMOTE' or 'Resample' (Default 'SMOTE').");
		System.out.println("[-p double] Percentage of the new instances to create (Default 100%).");
		System.out.println("[-c int] ClassValue. The index of the class value to which SMOTE should be applied (Only for SMOTE filter) (Default 0 -> Auto-detect the non-empty minority class).");
		System.out.println("--");
	}
}