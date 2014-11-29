package attSelection;

import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.Utils;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;

import java.io.FileReader;
import java.util.Random;

/**
 * performs attribute selection using CfsSubsetEval and GreedyStepwise
 * and trains RandomForest with that.
 *
 * @author ArkaitzMarcos
 */

public class AttSelection
{
	/*
	 * Método Main
	 */
	public static void main(String[] args) throws Exception
	{
		// Cargamos el arff
		System.out.println("Cargando datos...");
		Instances data = null;
		data = AttSelection.loadFile(args[0]);
		
		// FilteredClassifier
		AttSelection.useClassifier(data);
		
		// low-level
		AttSelection.useLowLevel(data);
		System.out.println("\nTERMINADO");
	}
	
	/*
	 * FilteredClassifier
	 */
	private static void useClassifier(Instances data) throws Exception
	{
		FilteredClassifier classifier = new FilteredClassifier();
		AttributeSelection filter = new AttributeSelection();
		InfoGainAttributeEval eval = new InfoGainAttributeEval();
		Ranker search = new Ranker();
		RandomForest base = new RandomForest();
		
		filter.setEvaluator(eval);
		filter.setSearch(search);
		filter.setInputFormat(data);
		classifier.setClassifier(base);
		classifier.setFilter(filter);
		
		Evaluation evaluation = new Evaluation(data);
		evaluation.crossValidateModel(classifier, data, 10, new Random(1));
		System.out.println(evaluation.toSummaryString());
		
		Instances newData = Filter.useFilter(data, filter);
		//Imprime los nuevos datos, descomentar si quereis que la pantalla se os llene de kk
		//System.out.println(newData);
	}
	
	/*
	 * uses the low level approach
	 */
	private static void useLowLevel(Instances data) throws Exception
	{
		weka.attributeSelection.AttributeSelection attsel = new weka.attributeSelection.AttributeSelection();
		InfoGainAttributeEval eval = new InfoGainAttributeEval();
		Ranker search = new Ranker();
		attsel.setEvaluator(eval);
		attsel.setSearch(search);
		attsel.SelectAttributes(data);
		int[] indices = attsel.selectedAttributes();
		System.out.println("selected attribute indices (starting with 0):\n" + Utils.arrayToString(indices));
	}
	
	/*
	 * Cargamos los datos
	 */
	private static Instances loadFile(String path) throws Exception
	{
		FileReader fi = new FileReader(path);
		Instances data = new Instances(fi);
		fi.close();
		
		if (data.classIndex() == -1)
		{
			data.setClassIndex(data.numAttributes() - 1);
		}
		
		return data;
	}
}