/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.template;

import static edu.vanier.template.MainAppController.wordMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author 15144
 */
public class Quiz extends Stage{
    
    int wordsCtr;
    
    @FXML
    Label lbTop, lbBottom, lbNumber;
    
    @FXML
    Button btnNext;
    
    LinkedHashMap<String, String> quiz;
    ArrayList <String> french;
    ArrayList <String> english;

    int i=0;
    
    boolean flag = true;
    
    String FRorENG = " ";
    
    public Quiz(int num, String x) throws IOException{

       setTitle("Quiz");
       this.wordsCtr=num;
       this.FRorENG=x;
       um();
    }

    public void um() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/quiz.fxml"));
        loader.setController(this);
        Parent root = loader.load();

        Scene scene = new Scene(root,600,400);
        setScene(scene);
        setResizable(false);
        show();
        
        loadingQuiz();  
        
        if(FRorENG.equals("FR")){
        buttonsFR();
        }
        else if(FRorENG.equals("ENG")){
        buttonsENG();
        }
    }
    
    public void buttonsFR(){     

        btnNext.setOnAction((event)->{
            
          if(i<wordsCtr-1 && flag == false){   
          i++;//sets the new word
          lbTop.setText(french.get(i));
          lbBottom.setVisible(false);
          lbNumber.setText("#" + (i+1));
          flag=true;
          btnNext.setText("Reveal");
          }

          else if(flag==true){
          lbBottom.setVisible(true);//reveals the english
          String eng = quiz.get(french.get(i));
          lbBottom.setText(eng);
          btnNext.setText("Next");
          flag=false;
          }
          
          else if(i==wordsCtr-1){
          btnNext.setText("Exit");
          lbBottom.setVisible(false);
          lbTop.setText("Quiz over");
          i++;
          }
          else if(i==wordsCtr){
          close();
          }
        });       
    }
    
    public void buttonsENG() {
    btnNext.setOnAction((event) -> {
        if (i < wordsCtr - 1 && flag == false) {
            i++; // Sets the new word
            lbTop.setText(english.get(i)); // Show the English word
            lbBottom.setVisible(false); // Hide the French word initially
            lbNumber.setText("#" + (i + 1));
            flag = true;
            btnNext.setText("Reveal");
        } else if (flag == true) {
            lbBottom.setVisible(true); // Reveal the French word
            String eng = english.get(i); // Get the English word at index i
            String fr = quiz.get(eng); // Get the corresponding French word from the quiz map
            lbBottom.setText(fr); // Display the French word in lbBottom
            btnNext.setText("Next");
            flag = false;
        } else if (i == wordsCtr - 1) {
            btnNext.setText("Exit");
            lbBottom.setVisible(false);
            lbTop.setText("Quiz over");
            i++;
        } else if (i == wordsCtr) {
            close();
        }
    });
}
    
        public void loadingQuiz() {
    if (FRorENG.equals("FR")) {
        int ctr = 0;
        LinkedHashMap<String, String> copy = MainAppController.wordMap;
        quiz = new LinkedHashMap<String, String>();
        french = new ArrayList<String>();

        // Populate the french list with keys (French words)
        for (String key : copy.keySet()) {
            french.add(key);
        }

        // Shuffle the french list
        Collections.shuffle(french);

        // Populate the quiz map with French word (key) and corresponding English word (value)
        for (String m : french) {
            if (ctr < wordsCtr) {
                quiz.put(m, copy.get(m)); // French word -> English word mapping
                ctr++;
            }
        }

        lbTop.setText(french.get(0));
        } 
    
    else if (FRorENG.equals("ENG")) {
        int ctr = 0;
        LinkedHashMap<String, String> copy = MainAppController.wordMap;
        quiz = new LinkedHashMap<String, String>();
        english = new ArrayList<String>();

        // Populate the english list with values (English words)
        for (String key : copy.keySet()) {
            String value = copy.get(key);
            english.add(value);
        }

        // Shuffle the english list
        Collections.shuffle(english);

        // Populate the quiz map with English word (key) and corresponding French word (value)
        for (String m : english) {
            if (ctr < wordsCtr) {
                quiz.put(m, copy.get(m)); // English word -> French word mapping
                ctr++;
            }
        }

        lbTop.setText(english.get(0)); // Set the first English word to lbTop
    }
}
    }
    

