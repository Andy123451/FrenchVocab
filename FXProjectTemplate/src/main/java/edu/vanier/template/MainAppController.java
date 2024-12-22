/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
public class MainAppController extends Stage{

    @FXML
    TextField txtNumber;
    
    @FXML
    Button btnShowWords, btnQuiz;
        
    @FXML
    Label lbWarning;
    TableView<MapEntry> tableView;
    
    public static LinkedHashMap<String, String> wordMap  = new LinkedHashMap<>();
            
    int wordCtr=0;
    
    public void initialize() throws Exception{        
        loadingMap();
        buttons();
        initModality(Modality.APPLICATION_MODAL);
        initStyle(StageStyle.UTILITY);
        lbWarning.setText(" ");        
    }
    
    public void loadingMap() {
        //wordMap = new LinkedHashMap<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(MainApp.class.getResourceAsStream("/Vocab/Voc.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line into two parts by space
                String[] parts = line.split(" ");

                // Ensure the line has at least two parts
                if (parts.length == 2) {
                    // Replace underscores with spaces
                    String key = parts[0].replace("_", " ");
                    String value = parts[1].replace("_", " ");

                    // Add the key-value pair to the HashMap
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
           wordCtr++;// System.out.println("Key: " + key + ", Value: " + map.get(key));
        }
        
        btnShowWords.setText("Show all the words there are (" + wordCtr + ")");

        //System.out.println("total number of words: " + wordCtr);
        ObservableList<MapEntry> data = FXCollections.observableArrayList();
        wordMap.forEach((key, value) -> data.add(new MapEntry(key, value)));

        tableView = new TableView<>();

        // Create the Key column
        TableColumn<MapEntry, String> keyColumn = new TableColumn<>("French");
        keyColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        keyColumn.setPrefWidth(300);
       // keyColumn.setResizable(false);
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
       // valueColumn.setResizable(false);
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
        tableView.setPrefSize(600, 600);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
         
        tableView.setRowFactory(tv -> {
            TableRow<MapEntry> row = new TableRow<>();
            row.setPrefHeight(40); // Set row height
            return row;
        });
        
    }

            int wordCount = 0;

    public void buttons(){
        
        
     btnShowWords.setOnAction((event)->{
         VBox vbox = new VBox(tableView);
         Scene scene = new Scene(vbox, 400, 700);
         setScene(scene);
         setTitle("TableView from Text File");
         show();
     });
     
     btnQuiz.setOnAction((event)->{
         try {
             String nx = txtNumber.getText();
        if(isDigit(nx)==true && !nx.isEmpty()){
          wordCount = Integer.parseInt(nx);
          //System.out.println("ada " + wordCount + " s " + nx);
          Quiz obj = new Quiz(wordCount);
          lbWarning.setText(" ");
        }
        else{
            lbWarning.setText("Only input numbers");
        }
         } catch (IOException ex) {
             Logger.getLogger(MainAppController.class.getName()).log(Level.SEVERE, null, ex);
         }
    });
    }    
    
    public boolean isDigit(String x){
        
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
}
