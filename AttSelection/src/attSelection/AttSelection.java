package attSelection;

import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.Utils;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

/**
 * Módulo de Selección de Atributos
 *
 * por ArkaitzMarcos
 */

public class AttSelection
{
	/*
	 * Método Main
	 */
	public static void main(String[] args) throws Exception
	{
		// Cargamos el arff
		System.out.println("Cargando datos...\n");
		Instances data = null;
		data = AttSelection.loadFile(args[0]);
		
		// Selección de Atributos
		System.out.println("Seleccionando Atributos...\n");
		AttSelection.filtradoAtts(data);
		
		System.out.println("\nTERMINADO!!!");
	}
	
	/*
	 * FilteredClassifier
	 */
	private static void filtradoAtts(Instances data) throws Exception
	{
		// Creamos el clasificador FilteredClassifier, el clasificador base,
		// el filtro, el evaluador y el criterio de busqueda
		weka.attributeSelection.AttributeSelection attsel = new weka.attributeSelection.AttributeSelection();
		FilteredClassifier classifier = new FilteredClassifier();
		AttributeSelection filter = new AttributeSelection();
		CfsSubsetEval eval = new CfsSubsetEval();
		RandomForest base = new RandomForest();
		BestFirst search = new BestFirst();
		
		filter.setEvaluator(eval);
		filter.setSearch(search);
		filter.setInputFormat(data);
		classifier.setClassifier(base);
		classifier.setFilter(filter);
		
		// Realizamos la evaluación de los datos con el FilteredClassifier
		Evaluation evaluation = new Evaluation(data);
		evaluation.crossValidateModel(classifier, data, 10, new Random(1));
		
		// Ejecutamos el filtro
		Instances newData = Filter.useFilter(data, filter);
		
		// Realizamos la selección de atributos
		attsel.setEvaluator(eval);
		attsel.setSearch(search);
		attsel.SelectAttributes(data);
		int[] indices = attsel.selectedAttributes();
		Arrays.sort(indices);
		System.out.println("Indices de los atributos seleccionados (empezando desde 0):\n" + Utils.arrayToString(indices));
		
		// Guardamos los nuevos datos en otro arff
		File directorio = new File(".\\AttSelectionArff");
		directorio.mkdirs();
		PrintWriter printer = new PrintWriter(directorio.getPath() + "\\att_selection.arff");
		printer.println(newData);
		printer.close();
		System.out.println("Nuevo arff creado en:\n" + directorio.getCanonicalPath() + "\\att_selection.arff");
	}
	
	/*
	 * Cargador de datos
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