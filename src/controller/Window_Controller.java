package controller;

import java.util.logging.Level;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import main.Main;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class Window_Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    ImageView myImageView;
    
        @FXML
        public void switchToLayoutMainpage(ActionEvent event)throws IOException{ 
            Parent root = (Parent) FXMLLoader.load(getClass().getResource("/view/LayoutMainpage.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        
         @FXML
        public void switchToHalamanPrediksi(ActionEvent event)throws IOException{ 
            Parent root = (Parent) FXMLLoader.load(getClass().getResource("/view/HalamanPrediksi.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/asset/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        }
        
        
        @FXML
        private Button idPilihGambar;
        
        @FXML
        private ImageView view;
        
        @FXML
        private Pane viewPrediksi;
        
        @FXML
        private HBox labelPrediksi; 
        
        @FXML
        private Label predict;
        
        @FXML
        public void uploadImage(ActionEvent event)throws IOException{
            String charset = "UTF-8";
            String requestURL = "http://127.0.0.1:5000/predict2";
            EventHandler<ActionEvent> uploadImage = new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                view = new ImageView();     
                FileChooser fileChooser = new FileChooser();

                //extensi gambar
                FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG","(*.png)", "*.PNG");
                FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
                FileChooser.ExtensionFilter extFilterJFIF = new FileChooser.ExtensionFilter("JFIF files (*.jfif)", "*.JFIF");
                fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG, extFilterJFIF);

                //membuka file dialog
                File file = fileChooser.showOpenDialog(null);
               labelPrediksi.getChildren().remove(predict);
                Image load;
                if(file!=null){
                    load = new Image(file.toURI().toString());
                    view.setImage(load);
                    view.setFitHeight(130);
                    view.setFitWidth(130);
                    viewPrediksi.getChildren().add(view);
                    System.out.println(view.getImage());
                    try {
                        RestAPI_controller multipart = new RestAPI_controller(requestURL, charset);

                        multipart.addHeaderField("User-Agent", "CodeJava");
                        multipart.addHeaderField("Test-Header", "Header-Value");

                        multipart.addFormField("description", "Cool Pictures");
                        multipart.addFormField("keywords", "Java,upload,Spring");

                        multipart.addFilePart("file", file);

                        List<String> response = multipart.finish();

                        System.out.println("SERVER REPLIED:");

                        for (String line : response) {
                            System.out.println(line);
                        }
                    } catch (IOException ex) {
                        System.err.println(ex);
                    }
                }
               System.out.println(file.getPath());
            }
            };


               idPilihGambar.setOnAction(uploadImage);
        }
        
        @FXML
        public void prediksi()throws Exception
        {
             Object obj = new JSONParser().parse(new FileReader("C:/Users/A S U S/Documents/NetBeansProjects/UasWAD/src/serverFlask/data.json"));
             
             // typecasting obj to JSONObject
            JSONObject jo = (JSONObject) obj;
            boolean success = (boolean) jo.get("success");
            String file = (String) jo.get("file");
            long label = (long) jo.get("label");
            predict = new Label(jenisMotor(label));
            labelPrediksi.getChildren().add(predict);
            
            System.out.println("success: "+success+" file: "+file+" label: "+label);
        }
        
        public String jenisMotor(long label){
            String jenisMotor="";
            if(label==0){
                jenisMotor="Motor Moped";
            }if(label==1){
                jenisMotor="Motor Sport";
            }else{
                jenisMotor="Motor Scooter";
            }
            return jenisMotor;
        }
        
      
        
}
