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
			numTrees -- The number of trees to be generated.
			
			-No usados
			
			numFeatures -- The number of attributes to be used in random selection (see RandomTree).
			seed -- The random number seed to be used.
		 */
		
		RandomForest bosque = new RandomForest();
		KfoldEvaluator kfolder = new KfoldEvaluator();
		Evaluation evaluador=null;
		
		
		//Parametros temporales
		int numTrees=10;
		int numDepth=0;
		int contDepth=0;
		
		
		/*
		 * Parametros de resultado
		 */
		double fMeasure = 0,fMeasureTemp=0;
		int bestNumTrees=0,bestDepth=0;
		
		try {
			
			//Bucle con numTrees
			do
			{
				for(int i=0;i<numTrees;i++)
				{
					System.out.println("-----------------------------");
					System.out.println("NUmero de arboles usados "+i+" con "+contDepth+" de profundidad.");
					
							bosque.setNumTrees(i);
							bosque.setMaxDepth(contDepth);
						
							evaluador = kfolder.getAccuracy(pData, bosque);
							
							fMeasureTemp = evaluador.weightedFMeasure();
							
							if(fMeasure<fMeasureTemp)
							{
								System.out.println("-- Este Fmeasure es mejor: "+fMeasure);
								fMeasure=fMeasureTemp;
								bestNumTrees = i;
								bestDepth = contDepth;
							}
						
							
				}
			contDepth++;
			}while(contDepth<numDepth);
					
			showResults(evaluador,bestNumTrees,bestNumTrees);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void reentrenar(Instances pData,int pNumTrees,int pNumDepth)
	{
		
		Evaluation evaluador = null;
		
		RandomForest woods = new RandomForest();
		woods.setMaxDepth(pNumDepth);
		woods.setNumTrees(pNumTrees);
		
		KfoldEvaluator judge = new KfoldEvaluator();
		
		//blablabla
		try {
			
			 evaluador = judge.getAccuracy(pData, woods);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		judge.getAccuracy(pData, woods);
				
	}

	
	private void showResults(Evaluation pEvaluador,int pNumTrees,int pNumDepth)
	{
		System.out.println("Resultados; ");
		System.out.println("");
		System.out.println("FMeasure");
		
		
		
		
	}
	
	private void saveData(Evaluation pEvualuator,int pNumTrees,int pNumDepth)
	{
		
	}
}
