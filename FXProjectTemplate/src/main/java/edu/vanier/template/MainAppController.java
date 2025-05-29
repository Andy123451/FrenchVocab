/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author 15144
 */
public class MainAppController extends Stage implements Comparable<MapEntry>{

    @FXML
    ChoiceBox cbSort;
    
    @FXML
    TextField txtNumber;
    
    @FXML
    Button btnShowWords, btnQuizFR, btnQuizENG, btnAddWord;
        
    @FXML
    Label lbWarning, lbWords;
    
    TableView<MapEntry> tableView;
    
    public static LinkedHashMap<String, String> wordMap  = new LinkedHashMap<>();
    public static LinkedHashMap<String, String> alphaMap  = new LinkedHashMap<>();
        
    int wordCtr=0;
    
    String FRorENG = " ";
    
    public void initialize() throws Exception{        
        loadingMap();
        buttons();
        initModality(Modality.APPLICATION_MODAL);
        initStyle(StageStyle.UTILITY);
        lbWarning.setText(" ");     
        cbSort.setValue("Insertion order");//defaults the tableview to insertion order
        
        cbSort.getItems().addAll("Insertion order", "Alphabetical");
        txtNumber.setText("0");
    }
    
    public void loadingMap(){//loads all words and translations into a hashmap
        try (BufferedReader br = new BufferedReader(new InputStreamReader(MainApp.class.getResourceAsStream("/Vocab/Voc.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                //split the line into two parts by space
                String[] parts = line.split(" ");

                //ensure the line has at least two parts
                if (parts.length == 2) {
                    //replace underscores with spaces
                    String key = parts[0].replace("_", " ");
                    String value = parts[1].replace("_", " ");

                    //add the key-value pair to the HashMap
                    wordMap.put(key, value);
                } else {
                   // System.out.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print the HashMap to verify
        for (String key : wordMap.keySet()) {
           wordCtr++;
        }
        
        btnShowWords.setText("Show all the words there are");
        lbWords.setText("There are " + wordCtr + " words");      
        
        //BELOW IS SORTING BY ALPHABETICAL ORDER
        
        
    }

            int wordCount = 0;
    
    public void tables(ObservableList data){
    tableView = new TableView<>();

        // Create the Key column
        TableColumn<MapEntry, String> keyColumn = new TableColumn<>("French");
        keyColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        keyColumn.setPrefWidth(300);
        keyColumn.setCellFactory(column -> new TableCell<MapEntry, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                    // Apply custom font and styling
                    setStyle("-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-text-fill: black;");
                }
            }
        });

        // Create the Value column
        TableColumn<MapEntry, String> valueColumn = new TableColumn<>("English");
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        valueColumn.setPrefWidth(300);
        valueColumn.setCellFactory(column -> new TableCell<MapEntry, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                    // Apply custom font and styling
                    setStyle("-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-text-fill: black;");
                }
            }
        });

        // Add columns to the TableView
        tableView.getColumns().addAll(keyColumn, valueColumn);

        // Set the data in the TableView
        tableView.setItems(data);
        tableView.setPrefSize(600, 700);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
         
        tableView.setRowFactory(tv -> {
            TableRow<MapEntry> row = new TableRow<>();
            row.setPrefHeight(40); // Set row height
            return row;
        });
    }
    
    public void buttons(){               
     
     btnShowWords.setOnAction((event)->{
         
         if(cbSort.getValue().equals("Insertion order")){//goes by insertion order
         ObservableList<MapEntry> data = FXCollections.observableArrayList();
         wordMap.forEach((key, value) -> data.add(new MapEntry(key, value)));
         tables(data);
         }
         
         else if(cbSort.getValue().equals("Alphabetical")){
             
          ArrayList <String> keys2 = new ArrayList<>();
        
          for (String key : wordMap.keySet()) {//adds keys to arraylist
              keys2.add(key);
          
          }
        //below is considering french notations like ç are not counted as "c" but rather not a letter so they don't get sorted like regular letters properly
          ArrayList <String> weirdFrench = new ArrayList<>();
          ArrayList <String> fixedFrench = new ArrayList<>();

          for(int i=0; i<keys2.size();i++){
              
              char start = keys2.get(i).charAt(0);
              
            if(start == 'ç' || start == 'à' || start == 'è' || start == 'ù' || start == 'é' || start == 'â'  || start == 'ê' || start == 'î' || start == 'ô' || start == 'û' || start == 'ë' || start == 'ï' || start == 'ü' || start == 'æ' || start == 'œ'){
              String x = "";
                switch(start){
                  case 'à', 'â', 'æ': x = "a" + keys2.get(i).substring(1); break;
                  case 'ç': x = "c" + keys2.get(i).substring(1); break;
                  case 'è', 'é', 'ê', 'ë': x = "e" + keys2.get(i).substring(1); break;
                  case 'î', 'ï': x = "i" + keys2.get(i).substring(1); break;
                  case 'ô', 'œ': x = "o" + keys2.get(i).substring(1); break;
                  case 'ù', 'û', 'ü': x = "u" + keys2.get(i).substring(1); break;
                  default: System.out.println("bruh");
              }
              
              fixedFrench.add(x);//replaced ç with c
              weirdFrench.add(keys2.get(i));// adds the original word with ç
              
              keys2.remove(i);              
           }            
          }
          
          for (String x : fixedFrench) {
               keys2.add(x);
          }
          
          Collections.sort(keys2);//sorts the keys by alphabetical order
          
          for(int i=0; i<keys2.size(); i++){
          
              for(int j=0; j<fixedFrench.size(); j++){
                  if(keys2.get(i).equals(fixedFrench.get(j))){
                     keys2.set(i, weirdFrench.get(j));//replaces the replaced c and e and wtv with their original counterparts
                  }
              }
          }
          
          for(int i=0; i<wordCtr;i++){//making a second hashmap with alphabetically arranged words
             alphaMap.put(keys2.get(i), wordMap.get(keys2.get(i)));
          }
          
         ObservableList<MapEntry> data = FXCollections.observableArrayList();
         alphaMap.forEach((key, value) -> data.add(new MapEntry(key, value)));
         tables(data);
         }
         
         VBox vbox = new VBox(tableView);
         Scene scene = new Scene(vbox, 400, 700);
         setScene(scene);
         setTitle("TableView from Text File");
         show();
     });
     
     btnQuizFR.setOnAction((event)->{
         try {
             String nx = txtNumber.getText();
        if(isDigit(nx)==true && !nx.isEmpty()){
          wordCount = Integer.parseInt(nx);
          FRorENG = "FR";
          //System.out.println("ada " + wordCount + " s " + nx);
          if(wordCount!=0){
          Quiz obj = new Quiz(wordCount, FRorENG);
          }
          else if(wordCount==0){
          Quiz obj = new Quiz(wordCtr, FRorENG);
          }
          lbWarning.setText(" ");
        }
        else{
            lbWarning.setText("Only input numbers");
        }
         } catch (IOException ex) {
             Logger.getLogger(MainAppController.class.getName()).log(Level.SEVERE, null, ex);
         }
    });
     
     btnQuizENG.setOnAction((event)->{
         try {
             String nx = txtNumber.getText();
        if(isDigit(nx)==true && !nx.isEmpty()){
          wordCount = Integer.parseInt(nx);
          FRorENG = "ENG";
          //System.out.println("ada " + wordCount + " s " + nx);
          if(wordCount!=0){
          Quiz obj = new Quiz(wordCount, FRorENG);
          }
          else if(wordCount==0){
          Quiz obj = new Quiz(wordCtr, FRorENG);
          }
          lbWarning.setText(" ");
        }
        else{
            lbWarning.setText("Only input numbers");
        }
         } catch (IOException ex) {
             Logger.getLogger(MainAppController.class.getName()).log(Level.SEVERE, null, ex);
         }
    });
     
     btnAddWord.setOnAction((event)->{
         try {
             AddWord obj = new AddWord(wordMap);
         } catch (IOException ex) {
             Logger.getLogger(MainAppController.class.getName()).log(Level.SEVERE, null, ex);
         }
     });
    }    
    
    public boolean isDigit(String x){//took from sem 3 assignment 1
        
        boolean flag = true;
        for(int i=0;i<x.length();i++){
            if(!Character.isDigit(x.charAt(i))){
               flag=false;
               break;
            }
        }
        
        if(flag==true){return true;}
        else{return false;}
    }

    @Override
    public int compareTo(MapEntry o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
