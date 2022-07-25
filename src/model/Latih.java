package model;
import java.sql.Blob;
import java.sql.Date;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Latih {
    private int id_latih;
    private String label;
    private Long labelLong;
    private Blob gambar;
    private ImageView convertGambar;
    private double acc_scooter;
    private double acc_sport;
    private double acc_moped;

    public int getId_latih() {
        return id_latih;
    }

    public void setId_latih(int id_latih) {
        this.id_latih = id_latih;
    }

    public String getLabel() {
        return label;
    }

    public Long getLabelLong() {
        return labelLong;
    }

    public void setLabelLong(Long labelLong) {
        this.labelLong = labelLong;
    }
    

    public void setLabel(String label) {
        this.label = label;
    }

    public Blob getGambar() {
        return gambar;
    }

    public void setGambar(Blob gambar) {
        this.gambar = gambar;
    }
    
    public void convertGambar(Image gambar){
        this.convertGambar = new ImageView();
        this.convertGambar.setImage(gambar);
        this.convertGambar.setFitHeight(50);
        this.convertGambar.setFitWidth(50);
    }
    public ImageView getConvertGambar(){
        if(this.convertGambar==null){
            System.out.println("this is null");
        }
        return this.convertGambar;
    }

    public double getAcc_scooter() {
        return acc_scooter;
    }

    public void setAcc_scooter(double acc_scooter) {
        this.acc_scooter = acc_scooter;
    }

    public double getAcc_sport() {
        return acc_sport;
    }

    public void setAcc_sport(double acc_sport) {
        this.acc_sport = acc_sport;
    }

    public double getAcc_moped() {
        return acc_moped;
    }

    public void setAcc_moped(double acc_moped) {
        this.acc_moped = acc_moped;
    }
    
    
}
