package it.polito.tdp.dizionariograph;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.dizionariograph.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DizionarioGraphController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtNumber;

    @FXML
    private TextField txtWord;

    @FXML
    private Button btnGraph;

    @FXML
    private Button btnFind;

    @FXML
    private Button btnMaxDegree;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnReset;

    @FXML
    private Label txtMessage;

	private Model model;

    @FXML
    void generaGrafo(ActionEvent event) {
 
    	  this.txtMessage.setText("");
    	
    	  String input =  this.txtNumber.getText().trim();
    	
    	 
    	  if(input.matches("[0-9]+")){		  
    	
    	  if(Integer.parseInt(input)==27) {
    		  this.txtMessage.setText("Spiacenti, non esistono parole di 27 lettere");
    		  return;
    	  }
    	  	  
    	  this.txtNumber.setEditable(false);
          this.btnGraph.setDisable(true);
          this.btnMaxDegree.setDisable(false);
          this.btnFind.setDisable(false);
          this.txtWord.setDisable(false);
          
          model.createGraph(Integer.parseInt(input));
          
          this.txtMessage.setText("Selezionate parole aventi numero lettere pari a : " + input);
          
    		 }
    	  else {
    		  this.txtMessage.setText("Errore inserire un numero intero minore di 29");
    		  return;
    	  }
    				  
    }

    @FXML
    void reset(ActionEvent event) {
    	
    	this.txtMessage.setText("");
    	this.txtResult.clear();
    	this.txtNumber.clear();
    	this.txtWord.clear();
    	
    	this.btnGraph.setDisable(false);
    	this.btnFind.setDisable(true);
    	this.btnMaxDegree.setDisable(true);
    	this.txtNumber.setEditable(true);
    	this.txtWord.setDisable(true);

    }

    @FXML
    void trovaGradoMassimo(ActionEvent event) {
    	

    	this.txtMessage.setText("");
    	this.txtResult.clear();
   
    	
    	List <String> viciniMax = model.findMaxDegree();
    
    	
    	Collections.sort(viciniMax);
    	for(String s : viciniMax)
    	   this.txtResult.appendText(s+"\n");
    	
    	
    	
    
    	
    	

    }

    @FXML
    void trovaVicini(ActionEvent event) {
    	
    	this.txtMessage.setText("");
    	this.txtResult.clear();
    	
    	String input = this.txtWord.getText().toLowerCase().trim();
    	if(input.matches("[a-z]+")) {
    	
    	if(!model.getGrafo().vertexSet().contains(input)) {
    		  this.txtMessage.setText("Attenzione, la parola inserita non appartiene al dizionario \no non è del numero di lettere selezionato");
    		  return;
    	}
    	
    		
    	List <String> vicini = model.displayNeighbours(input);
    	
    	if(vicini.isEmpty()) {
    		this.txtResult.appendText("Spiacenti, non sono presenti parole vicine\n");
    		return;
    	}
    	
    	Collections.sort(vicini);
    	for(String s : vicini)
    	   this.txtResult.appendText(s+"\n");
    	}
    	  else {
    		  this.txtMessage.setText("Errore, inserire nel campo solo caratteri alfabetici");
    		  return;
    	  }
    }

    @FXML
    void initialize() {
        assert txtNumber != null : "fx:id=\"txtNumber\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert txtWord != null : "fx:id=\"txtWord\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btnGraph != null : "fx:id=\"btnGraph\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btnFind != null : "fx:id=\"btnFind\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btnMaxDegree != null : "fx:id=\"btnMaxDegree\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert txtMessage != null : "fx:id=\"txtMessage\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";

    }

	public void setModel(Model model) {
		this.model=model;
		
	}
}
