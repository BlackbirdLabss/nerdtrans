package main;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {
    private Stage primaryStage;
    private Parent rootLayout;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("NerdTrans");
        
        rootLayout();
    }
    public void rootLayout(){
          try{
            Parent rootLayout = (Parent) FXMLLoader.load(getClass().getResource("/view/WelcomePage.fxml"));
            
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);            
            primaryStage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
  
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
