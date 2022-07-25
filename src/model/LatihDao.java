package model;

import controller.Window_Controller;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class LatihDao implements Initializable{
    private Stage stage;
    private Scene scene;
    
    public static void insertLatih(Latih l) throws SQLException, ClassNotFoundException {
        int status=0;
        try{
            Connection conn = model.ConnectionProvider.getCon();
            PreparedStatement ps = conn.prepareStatement("insert into latih_prediksi(label, gambar, acc_scooter, acc_sport, acc_moped"
                    + ") values(?,?,?,?,?)");
            File file = new File(Window_Controller.getPathFile());
            FileInputStream fin=new FileInputStream(file);
            ps.setLong(1, l.getLabelLong());
            ps.setBinaryStream(2, fin);
            ps.setDouble(3, l.getAcc_moped());
            ps.setDouble(4, l.getAcc_sport());
            ps.setDouble(5, l.getAcc_scooter());
            
            status = ps.executeUpdate();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public static ObservableList<Latih> getAllLatih(){
        ObservableList<Latih> list = FXCollections.observableArrayList();
        try{
            Connection con=model.ConnectionProvider.getCon();
            Statement ps=con.createStatement();
            ResultSet rs=ps.executeQuery("select label, gambar, acc_scooter, acc_sport, acc_moped from latih_prediksi");
            while(rs.next()){
                Latih l = new Latih();
                Long Label = rs.getLong(1);
                if(Label==0){
                    l.setLabel("Moped");
                }if(Label==1){
                    l.setLabel("Sport");
                }if(Label==2){
                    l.setLabel("Scooter");
                }
                //blob to buffered image
                Blob convert = rs.getBlob(2);
                InputStream is = convert.getBinaryStream();
                //BufferedImage to Image
                Image image = new Image(is);
                l.convertGambar(image);
                System.out.println("value:"+l.getConvertGambar().getImage());
                l.setAcc_scooter(rs.getDouble(3));
                l.setAcc_sport(rs.getDouble(4));
                l.setAcc_moped(rs.getDouble(5));
                list.add(l);
            }
        }catch(Exception ld){
            ld.printStackTrace();
    }
        return list;
    }
    
    //-------------------------------
    
    @FXML
    private Button idTentang;
    @FXML
    private AnchorPane idAnchor;
    @FXML
    private BarChart<String, Double> barChartSport;
    @FXML
    private NumberAxis idAkurasiSport;
    @FXML
    private CategoryAxis idLabelSport;
    @FXML
    private BarChart<String, Double> barChartScooter;
    @FXML
    private NumberAxis idAkurasiScooter;
    @FXML
    private CategoryAxis idLabelScooter;
    @FXML
    private BarChart<String, Double> barChartMoped;
    @FXML
    private NumberAxis idAkurasiMoped;
    @FXML
    private CategoryAxis idLabelMoped;
    
    public void initialize(URL url, ResourceBundle rb){
        getAllLatihBarAcc_Sport();
        getAllLatihBarAcc_Scooter();
        getAllLatihBarAcc_Moped();
    }
    
    public void getAllLatihBarAcc_Sport(){
        try{
            Connection conSport=model.ConnectionProvider.getCon();
            Statement ps=conSport.createStatement();
            ResultSet rs=ps.executeQuery("select acc_scooter, acc_sport, acc_moped from latih_prediksi where label=1");
            while(rs.next()){
                
            XYChart.Series<String, Double> seriesSport = new XYChart.Series<>();
            seriesSport.setName("Akurasi data");
            seriesSport.getData().add(new XYChart.Data<>("Sport", rs.getDouble(2)));
            
            barChartSport.getData().add(seriesSport);
            
                System.out.println("Data Sport: "+seriesSport.getData());
            }
        }catch(Exception ld){
            ld.printStackTrace();
    }
    }
    
    public void getAllLatihBarAcc_Scooter(){
        try{
            Connection conScooter=model.ConnectionProvider.getCon();
            Statement ps=conScooter.createStatement();
            ResultSet rs=ps.executeQuery("select acc_scooter, acc_sport, acc_moped from latih_prediksi where label=2");
            while(rs.next()){
            
            XYChart.Series<String, Double> seriesScooter = new XYChart.Series<>();
            seriesScooter.setName("Akurasi data");
            seriesScooter.getData().add(new XYChart.Data<>("Scooter", rs.getDouble(1)));
            
            barChartScooter.getData().add(seriesScooter);
            
                System.out.println("Data Scooter: "+seriesScooter.getData());
            }
        }catch(Exception ld){
            ld.printStackTrace();
    }
    }
    
    public void getAllLatihBarAcc_Moped(){
        try{
            Connection conMoped=model.ConnectionProvider.getCon();
            Statement ps=conMoped.createStatement();
            ResultSet rs=ps.executeQuery("select acc_scooter, acc_sport, acc_moped from latih_prediksi where label=0");
            while(rs.next()){
            
            XYChart.Series<String, Double> seriesMoped = new XYChart.Series<>();
            seriesMoped.setName("Akurasi data");
            seriesMoped.getData().add(new XYChart.Data<>("Moped", rs.getDouble(3)));
            
            barChartMoped.getData().add(seriesMoped);
            
                System.out.println("Data Moped: "+seriesMoped.getData());
            }
        }catch(Exception ld){
            ld.printStackTrace();
    }
    }

    @FXML
    private void switchGrafikToHalamanBeranda(ActionEvent event)throws IOException{
        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/LayoutMainpage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/asset/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    
}
