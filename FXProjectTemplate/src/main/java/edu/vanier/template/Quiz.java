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
    Label lbFrench, lbEnglish, lbNumber;
    
    @FXML
    Button btnNext;
    
    LinkedHashMap<String, String> quiz;
    ArrayList <String> french;
    
    int i=0;
    
    boolean flag = true;
    
    public Quiz(int num) throws IOException{

       setTitle("Quiz");
       this.wordsCtr=num;
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
        buttons();
    }
    
    public void buttons(){     

        btnNext.setOnAction((event)->{
            
          if(i<wordsCtr-1 && flag == false){   
          i++;//sets the new word
          lbFrench.setText(french.get(i));
          lbEnglish.setVisible(false);
          lbNumber.setText("#" + (i+1));
          flag=true;
          btnNext.setText("Reveal");
          }

          else if(flag==true){
          lbEnglish.setVisible(true);//reveals the english
          String eng = quiz.get(french.get(i));
          lbEnglish.setText(eng);
          btnNext.setText("Next");
          flag=false;
          }
          
          else if(i==wordsCtr-1){
          btnNext.setText("Exit");
          lbEnglish.setVisible(false);
          lbFrench.setText("Quiz over");
          i++;
          }
          else if(i==wordsCtr){
          close();
          }
        });       
    }
    
    
    public void loadingQuiz(){
         int ctr = 0;
         LinkedHashMap<String, String> copy = MainAppController.wordMap;
         quiz = new LinkedHashMap<String, String>();
                 
         french = new ArrayList<String>();

         for(String key: copy.keySet()){            
             french.add(key);//adds keys to list french
         }
         
         Collections.shuffle(french);//shuffles the list

         for(String m : french){//adds shuffled keys and corresponding values to new map
             if(ctr<wordsCtr){
             quiz.put(m,copy.get(m));
             ctr++;
             }
         }
         
         for (String key : quiz.keySet()) {
        //   System.out.println("Key: " + key + ", Value: " + quiz.get(key));
        }
         
         lbFrench.setText(french.get(0));
    }
    
}
