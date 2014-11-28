package rf;

import weka.core.Instances;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		

		//Needed parameters
		Instances data = null;
		int pNumTrees;
		int pMaxDepth;
		
		
		/*
		 * Temporal para probar el programa 
		 */
		
		String path = "C:/Users/Nadox/Desktop/colon.arff";
		FileManager file = new FileManager();
		data = file.loadData(path);
		
		/*
		 * Fin temporal
		 */
		

		try {

			RandomForestClassifier rf = new RandomForestClassifier();
			rf.hacerBarrido(data);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
