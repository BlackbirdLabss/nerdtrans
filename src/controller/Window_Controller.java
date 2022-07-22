package controller;

import java.util.logging.Level;
import java.awt.image.BufferedImage;
import java.io.File;
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
        public void uploadImage(ActionEvent event)throws IOException{
            String charset = "UTF-8";
            String requestURL = "http://127.0.0.1:5000/predict2";
            EventHandler<ActionEvent> uploadImage = new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                view = new ImageView();     
                FileChooser fileChooser = new FileChooser();

                //extensi gambar
                FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
                FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
                FileChooser.ExtensionFilter extFilterJFIF = new FileChooser.ExtensionFilter("JFIF files (*.jfif)", "*.jfif");
                fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG, extFilterJFIF);

                //membuka file dialog
                File file = fileChooser.showOpenDialog(null);

                if(file!=null){
                    Image load = new Image(file.toURI().toString());
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
               file.delete();
            }
            };


               idPilihGambar.setOnAction(uploadImage);
        }
        
        @FXML
        public void prediksi(){
            
        }
        
}