package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Latih;
import model.LatihDao;

public class HalamanLatih_Controller implements Initializable{
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TableView latihTable;
    
     @FXML
    private TableColumn<Latih, String> labelColumn;
    @FXML
    private TableColumn<Latih, ImageView> gambarColumn;
    @FXML
    private TableColumn<Latih, Double> mopedColumn;
    @FXML
    private TableColumn<Latih, Double> scooterColumn;
    @FXML
    private TableColumn<Latih, Double> sportColumn;
    
    private Executor exec;
    @FXML
    private Button idTentang;
    
    public void initialize(URL url, ResourceBundle rb){
        exec = Executors.newCachedThreadPool((runnable)->{
        Thread t = new Thread(runnable);
        t.setDaemon(true);
        return t;
    });
    
    labelColumn.setCellValueFactory(new PropertyValueFactory<Latih, String>("label"));
    gambarColumn.setCellValueFactory(new PropertyValueFactory<Latih, ImageView>("convertGambar"));
    mopedColumn.setCellValueFactory(new PropertyValueFactory<Latih, Double>("acc_moped"));
    scooterColumn.setCellValueFactory(new PropertyValueFactory<Latih, Double>("acc_scooter"));
    sportColumn.setCellValueFactory(new PropertyValueFactory<Latih, Double>("acc_sport"));
    }
    
    @FXML
    private void cariData(ActionEvent actionEvent) throws SQLException, ClassNotFoundException{
        ObservableList<Latih> latihData = LatihDao.getAllLatih();
        populateLatih(latihData);
    }
    
    private void populateLatih(ObservableList<Latih> latihData) throws ClassNotFoundException{
        latihTable.setItems(latihData);
    }

    @FXML
    private void switchLatihToHalamanBeranda(ActionEvent event)throws IOException{
        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/LayoutMainpage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/asset/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
