package it.polito.tdp.dizionariograph.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.dizionariograph.db.WordDAO;




public class Model {

	private class TraversalListenerParole implements TraversalListener<String, DefaultEdge> {

		private String parolaInserita ;
		
		public TraversalListenerParole(String parolaInserita) {
			this.parolaInserita=parolaInserita;
		}

		@Override
		public void connectedComponentFinished(ConnectedComponentTraversalEvent ev) {
			// TODO Auto-generated method stub

		}

		@Override
		public void connectedComponentStarted(ConnectedComponentTraversalEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> ev) {
			// TODO Auto-generated method stub
			String vertexSource = grafo.getEdgeSource(ev.getEdge());
			String vertexTarget = grafo.getEdgeTarget(ev.getEdge());
			
			if(vertexSource.equals(parolaInserita)) 
				vicini.add(vertexTarget);

			if(vertexTarget.equals(parolaInserita)) 
				vicini.add(vertexSource);
		}

		@Override
		public void vertexFinished(VertexTraversalEvent<String> arg0) {
			// TODO Auto-generated method stub
	        
		}

		@Override
		public void vertexTraversed(VertexTraversalEvent<String> arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private Graph<String, DefaultEdge> grafo;
	private List <String> vertici;
	private Set <String> cercaVertex;
	private WordDAO dao;
	private Set <String> vicini;
	
	public Model() {
		
		this.dao = new WordDAO();
		
	}

	public void createGraph(int numeroLettere) {

	//creo il grafo
    this.grafo = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
    
    /*
     * aggiungo i vertici
     * 
     * Tali vertici altro non sono che il numero di parole aventi la lunghezza dell'intero passato come parametro
     */
    
    this.vertici=dao.getAllWordsFixedLength(numeroLettere);
    Graphs.addAllVertices(grafo, vertici);
    this.cercaVertex = new HashSet<>(this.vertici);
    
    /*
     * ora devo aggiungere gli archi e devo considerare che due parole sono connesse solo se differiscono per una lettera
     * 
     * in tal senso è possibile chiedere direttamente al dao, per ogni vertice, la lista delle parole al quale esso è collegato
     * 
     */
    
     
    
    for(String vertice : this.grafo.vertexSet()) {
    
    	
    	//System.out.println();
    	//System.out.println(vertice);  
    	//System.out.println();
        
    	
    	
    	
    	
    	 for(int i=0; i<numeroLettere;i++) {
    	   	
    		 char car[] = vertice.toCharArray();
    		 
    		  for(char k='a'; k<='z';k++) {
    		       car[i]=k;
    		       String cerco = String.valueOf(car);
    		     
    		       if(cerco.compareTo(vertice)>0 && this.cercaVertex.contains(cerco)) {
    		    	   this.grafo.addEdge(vertice, cerco); 
    		    	   
    		       }
    		  }
    	}
         
    	   
    	
    	
    }
    
		
	}

	
	public List <String> displayNeighbours(String parolaInserita) {

		
		vicini = new HashSet<String>();
		
		GraphIterator<String, DefaultEdge> it = new BreadthFirstIterator<>(this.grafo, parolaInserita);
		
		it.addTraversalListener(new Model.TraversalListenerParole(parolaInserita));
		
		while(it.hasNext()) {
			it.next();	
		}
		
		List <String> result = new ArrayList<>(vicini);
		Collections.sort(result);
		
		return result;
	}

	public List<String> findMaxDegree() {
	
		List<String> result = new LinkedList<>();		
		int degree = 0;
		String maxDegree = "";
		for(String cerco : this.cercaVertex) {
		   if(this.grafo.degreeOf(cerco)>degree) {
			   degree=this.grafo.degreeOf(cerco);
			   maxDegree=cerco;
		   }
		   
		}
		  
		result.addAll(this.displayNeighbours(maxDegree));
		
		String inizio = "Il vertice (parola) di grado più alto è "+maxDegree+".\nIl suo grado è "+degree+" e i suoi vicini sono: ";
		
		result.add(0, inizio);
		  
		
		return result;
	}

	public Graph<String, DefaultEdge> getGrafo() {
		return grafo;
	}

	
}
