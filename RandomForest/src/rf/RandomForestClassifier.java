package rf;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class RandomForestClassifier {

	
	public RandomForestClassifier(){}
	
	public void hacerBarrido(Instances pData)
	{
		/*
		 * Parametros posibles
		 * ===================
			maxDepth -- The maximum depth of the trees, 0 for unlimited.
			numFeatures -- The number of attributes to be used in random selection (see RandomTree).
			numTrees -- The number of trees to be generated.
			seed -- The random number seed to be used.
		 */
		
		RandomForest bosque = new RandomForest();
		KfoldEvaluator kfolder = new KfoldEvaluator();
		Evaluation evaluador=null;
		
		
		//Parametros temporales
		int numTrees=30;
		int numDepth=10;
		
		
		/*
		 * Parametros de resultado
		 */
		double fMeasure = 0,fMeasureTemp=0;
		int bestNumTrees=0,bestDepth=0;
		
		try {
			
			//Bucle con numTrees
			
			for(int i=0;i<numTrees;i++)
			{
				System.out.println("-----------------------------");
				System.out.println("Number of trees being used: "+i);
				for(int j=0;j<numDepth;j++)
					{
						bosque.setNumTrees(i);
						bosque.setMaxDepth(j);
					
						evaluador = kfolder.getAccuracy(pData, bosque);
						
						fMeasureTemp = evaluador.weightedFMeasure();
						
						if(fMeasure<fMeasureTemp)
						{
							System.out.println("--This Fmeasure is better: "+fMeasure);
							System.out.println("--With this depth: "+j);
							fMeasure=fMeasureTemp;
							bestNumTrees = i;
							bestDepth = j;
						}
						else
						{
							System.out.println("--No improvement, depth; "+j);
						}
					}
					
			}
			
			System.out.println("El mejor Fmeasure: "+fMeasure);
			System.out.println("Con tantos arboles: "+bestNumTrees);
			System.out.println("Con tanta profundidad: "+bestDepth);
			

			saveData(pData, bestNumTrees, bestDepth, "Modelo");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void classificar(Instances pData,int pNumTrees,int pNumDepth)
	{
		
		Evaluation evaluador = null;
		
		RandomForest woods = new RandomForest();
		woods.setMaxDepth(pNumDepth);
		woods.setNumTrees(pNumTrees);
		
		KfoldEvaluator judge = new KfoldEvaluator();
		
		
		try {
			
			 evaluador = judge.getAccuracy(pData, woods);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		judge.getAccuracy(pData, woods);
		

				
	}

	
	private void saveData(Instances pData,int pNumTrees,int pNumDepth, String pName) throws Exception
	{
		//Creamos clasificador
		RandomForest cls = new RandomForest();
		
		cls.setNumTrees(pNumTrees);
		cls.setMaxDepth(pNumDepth);
		
		//train

		pData.setClassIndex(pData.numAttributes() - 1);
		cls.buildClassifier(pData);

		
		// serialize model		
		SerializationHelper.write("\\modelo\\" + pName + ".mdl", cls);
		System.out.println("he creado la carpeta");
	}
}
