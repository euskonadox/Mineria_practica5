package rf;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

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

	
	private void saveData(Evaluation pEvualuator,int pNumTrees,int pNumDepth)
	{
		
	}
}
