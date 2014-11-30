package org.mdd.practica5;
import weka.core.*;
import weka.filters.Filter;
import weka.filters.supervised.instance.Resample;
import weka.filters.supervised.instance.SMOTE;

public class BalanceInstances {
		
	public Instances aplicarSMOTE(Instances instancias, double porcentaje, String valorClase) throws Exception {
		
		SMOTE filtro = new SMOTE();
		
		instancias.setClass(instancias.attribute("Class"));
		filtro.setInputFormat(instancias);
		filtro.setPercentage(porcentaje);
		filtro.setClassValue(valorClase);
		Instances NuevasInstancias = Filter.useFilter(instancias, filtro);
		
		return (NuevasInstancias);
	}
	
	public Instances aplicarResample(Instances instancias, double porcentaje) throws Exception {
		
		Resample filtro = new Resample();
		
		instancias.setClass(instancias.attribute("Class"));
		filtro.setInputFormat(instancias);
		filtro.setBiasToUniformClass(1);
		filtro.setSampleSizePercent(porcentaje);
		Instances NuevasInstancias = Filter.useFilter(instancias, filtro);
		
		return (NuevasInstancias);
	}
}
