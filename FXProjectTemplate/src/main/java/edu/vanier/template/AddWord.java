/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.template;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author theli
 */
public class AddWord extends Stage{
    
    @FXML
    Label lbWarning;
    
    @FXML
    Button btnAdd;
    
    @FXML
    TextField tfFrench, tfEnglish;
    
    LinkedHashMap<String, String> checkWordMap  = new LinkedHashMap<>();

    boolean repeatedWord = false;
    
    public AddWord(LinkedHashMap map) throws IOException{
       setTitle("Add a new word");
       this.checkWordMap = map;
       add();
    }
    
    public void add() throws IOException{
    
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addWord.fxml"));
        loader.setController(this);
        Parent root = loader.load();

        Scene scene = new Scene(root,600,400);
        setScene(scene);
        setResizable(false);
        show();
        
        lbWarning.setVisible(false);
        
        System.out.println(tfFrench.getText());
            
    btnAdd.setOnAction((event)->{
        
       if(tfFrench.getText().isEmpty() || tfEnglish.getText().isEmpty()){
        lbWarning.setVisible(true);
        lbWarning.setText("Both fields need to be filled out");
        }
       
       else if(!tfFrench.getText().isEmpty() && !tfEnglish.getText().isEmpty()){
       lbWarning.setVisible(false);  
     for(String key : checkWordMap.keySet()){
        if(key.equals(tfFrench.getText().trim())){
        lbWarning.setVisible(true);
        lbWarning.setText("This word already exists");
        repeatedWord = true;
        break;
        }
        else{repeatedWord = false;}
      }
     
     if(repeatedWord == false){
         try {
             String wordFr = tfFrench.getText().trim().replace(" ", "_");//original word with spaces
             String wordEng = tfEnglish.getText().trim().replace(" ", "_");
             //System.out.println("Attempting to write to file: " + wordFr + " " + wordEng);

                    File file = new File("src/main/resources/Vocab/Voc.txt");
                    //System.out.println("File Path: " + file.getAbsolutePath()); // Debugging line
//System.out.println("Working Directory: " + System.getProperty("user.dir"));

                    //File parentDir = file.getParentFile();
                   // if (!parentDir.exists()) {
                      //  parentDir.mkdirs(); // Ensure parent directories exist
                     //   System.out.println("Directories created: " + parentDir.getAbsolutePath()); // Debugging line
                    //}

                    // Check if the file exists
                   // if (!file.exists()) {
                  //      System.out.println("File does not exist, creating a new one.");
                  //  } else {
                    //    System.out.println("File already exists.");
                    //}

                    
                    // Write to file
                    try (FileWriter writer = new FileWriter(file, true)) { // Append mode
                        writer.write("\n" + wordFr + " " + wordEng);
                        writer.flush();
                    }

                    close(); // Close the subwindow
                } catch (IOException ex) {
                    System.out.println("Error writing to file: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
    });
    }
}