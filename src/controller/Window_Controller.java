package controller;

import java.util.logging.Level;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
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
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import model.Latih;
import model.LatihDao;
import javafx.animation.RotateTransition;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;
import javafx.animation.Interpolator;
import javafx.scene.SubScene;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;

public class Window_Controller implements Initializable{
    private Stage stage;
    private Scene scene;
    private Parent root;
    private static String path = "";
    
    ImageView myImageView;
    private Button idBeranda;
    
    @FXML
    private Button idTentang;
    
    @FXML
    private AnchorPane idAnchorPaneCircle;
    
    @FXML
    private Circle c1;
    
    @FXML
    private Circle c2;
    
    @FXML
    private Circle c3;
    
    
    @Override
    public void initialize(URL location, ResourceBundle rb){
        setRotate(c1, true, 360, 10);
        setRotate(c2, true, 180, 18);
        setRotate(c3, true, 145, 24);
        audio("C:/Users/A S U S/Documents/NetBeansProjects/UasWAD/src/asset/sound1.mp3", true);
    }
    
    private void setRotate(Circle c, boolean reverse,int angle, int duration){
        
        RotateTransition rt = new RotateTransition(Duration.seconds(duration),c);
        
        rt.setAutoReverse(reverse);
        
        rt.setByAngle(angle);
        rt.setDelay(Duration.seconds(0));
        rt.setRate(3);
        rt.setCycleCount(18);
        rt.play();
    }
    
    
    public void audio(String path, boolean bol){
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);  
        mediaPlayer.setAutoPlay(true); 
    }
    
    

        @FXML
        public void switchToLayoutMainpage(ActionEvent event)throws IOException{ 
            AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/LayoutMainpage.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/asset/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        }
        @FXML
        public void switchToHalamanPrediksi(ActionEvent event)throws IOException{ 
            AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/HalamanPrediksi.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/asset/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        }
        @FXML
        public void switchToHalamanLatih(ActionEvent event)throws IOException{ 
            AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/HalamanLatih.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/asset/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        }
        
    @FXML   
    public void switchToHalamanGrafik(ActionEvent event)throws IOException{ 
        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/HalamanGrafik.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1000, 900);
        scene.getStylesheets().add(getClass().getResource("/asset/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToHalamanBeranda(ActionEvent event)throws IOException{ 
        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/LayoutMainpage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/asset/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
        @FXML
        private Button idPilihGambar;
        
        @FXML
        private Button idPrediksi;
        
        @FXML
        private ImageView view;
        @FXML
        private Pane viewPrediksi;
        @FXML
        private HBox labelPrediksi; 
        
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
                    try {
                        RestAPI_controller multipart = new RestAPI_controller(requestURL, charset);

                        multipart.addHeaderField("User-Agent", "CodeJava");
                        multipart.addHeaderField("Test-Header", "Header-Value");

                        multipart.addFormField("description", "Cool Pictures");
                        multipart.addFormField("keywords", "Java,upload,Spring");

                        multipart.addFilePart("file", file);

                        List<String> response = multipart.finish();

                        System.out.println("Server terhubung dengan Flask");

                        for (String line : response) {
                            System.out.println(line);
                        }
                        //disable button idPrediksi when status HTTP Responses bad
                            idPrediksi.setDisable(false);
                        
                    } catch (IOException ex) {
                        idPrediksi.setDisable(true);
                        System.err.println(ex);
                        System.out.println("eror");
                    }
                }else{
                    return;
                }
                setPathFile(file.getPath());
               System.out.println(file.getPath());
            }
            };
               idPilihGambar.setOnAction(uploadImage);
        }
        @FXML
        public void prediksi()throws Exception, SQLException, ClassNotFoundException
        {
            Latih l = new Latih();
            Object obj = new JSONParser().parse(new FileReader("C:/Users/A S U S/Documents/NetBeansProjects/UasWAD/src/serverFlask/data.json"));
             
             // typecasting obj to JSONObject
            JSONObject jo = (JSONObject) obj;
            boolean success = (boolean) jo.get("success");
            String file = (String) jo.get("file");
            long label = (long) jo.get("label");
            l.setLabelLong(label);
            double acc_moped = (double) jo.get("acc_moped");
            l.setAcc_moped(acc_moped);
            double acc_sport = (double) jo.get("acc_sport");
            l.setAcc_sport(acc_sport);
            double acc_scooter = (double) jo.get("acc_scooter");
            l.setAcc_scooter(acc_scooter);
            LatihDao.insertLatih(l);
            predict = new Label(jenisMotor(label));
            labelPrediksi.getChildren().add(predict);
        }
        
        public String jenisMotor(long label){
            String jenisMotor="";
            if(label==0){
                jenisMotor="Motor Moped";
            }if(label==1){
                jenisMotor="Motor Sport";
            }if(label==2){
                jenisMotor="Motor Scooter";
            }
            return jenisMotor;
        }
          
        
      public void setPathFile(String pathFile){
          this.path=pathFile;
      }
      
      public static String getPathFile(){
          return path;
      }
    @FXML
    private void switchPrediksiToHalamanBeranda(ActionEvent event) throws IOException {
        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/LayoutMainpage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/asset/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void switchLayoutToHalamanBeranda(ActionEvent event) throws IOException {
        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/WelcomePage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/asset/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void tentang(ActionEvent event) {
         Label label = new Label(" NerdTrans adalah organisasi yang bergerak \ndibidang transportasi.");
         label.setStyle(" -fx-background-color: white;");
         label.setMinWidth(100);
         label.setMinHeight(50);
        // create a popup
        Popup popup = new Popup();
        popup.getContent().add(label);
         EventHandler<ActionEvent> eventTentang = new EventHandler<ActionEvent>() {
   
            public void handle(ActionEvent event)
            {
                if (!popup.isShowing())
                    popup.show(idBeranda, 340, 80);
                else
                    popup.hide();
            }
        };
         idTentang.setOnAction(eventTentang);
    }
        
}
