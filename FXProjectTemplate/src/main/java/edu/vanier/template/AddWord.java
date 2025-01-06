/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.template;

import java.io.IOException;
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
    
    public AddWord() throws IOException{
       setTitle("Add a new word");
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
    }
}
