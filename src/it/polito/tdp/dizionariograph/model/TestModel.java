package it.polito.tdp.dizionariograph.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		long start = System.currentTimeMillis();
		model.createGraph(5);
		System.out.println(String.format("**Grafo creato**\n" + (System.currentTimeMillis()-start)));
		System.out.println(String.format("**Rispettivamente vertici: "+model.getGrafo().vertexSet().size()+" e archi: "+model.getGrafo().edgeSet().size()+"**\n"));
		
		List<String> vicini = model.displayNeighbours("santo");
		System.out.println("Neighbours di casa: " + vicini + "\n");
		
		System.out.println("Cerco il vertice con grado massimo...");
		System.out.println(model.findMaxDegree());
	}

}
