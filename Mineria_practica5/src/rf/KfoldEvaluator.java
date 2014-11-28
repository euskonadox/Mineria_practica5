package rf;

import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

public class KfoldEvaluator {

	
	
	public KfoldEvaluator()
	{
		
	}
	
	public Evaluation getAccuracy(Instances pData,RandomForest pClassifier)
	{
		
		Evaluation evaluador=null;
		
		try {
			
			evaluador = new Evaluation(pData);
			evaluador.crossValidateModel(pClassifier, pData, 10, new Random(1));
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return evaluador;
	}
}
