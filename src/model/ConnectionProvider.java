package model;


import java.sql.*;
import static model.Provider.*;

public class ConnectionProvider {
    private static Connection con=null;
    
    static{
        try{
            Class.forName(DRIVER);
            con = DriverManager.getConnection(CONNECTION_URL,USERNAME,PASSWORD);
        }catch(Exception e){}        
        }
        
        public static Connection getCon(){
            return con;
        }
    
        public static void CloseConnection() {
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
        
         public static ResultSet getResultFromSqlQuery(String SqlQueryString) {
        //Creating Resultset object
        ResultSet rs = null;
        try {
            //Checking whether the connection is null or null
            if (con == null) {
                getCon();
            }
            //Querying the query
            rs = getCon().createStatement().executeQuery(SqlQueryString);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rs;
    }
         public static ResultSet getResultIdFromResult(String SqlQuery, int id){
              ResultSet rs = null;
            try {
            if (con == null) {
                getCon();
            }
            PreparedStatement ps = getCon().prepareStatement(SqlQuery);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            }catch(Exception ex){
                ex.printStackTrace();
            }
            return rs;
         }
    
             public static int insertUpdateFromSqlQuery(String SqlQueryString) {
        int i = 2;
        try {
            //Checking whether the connection is null or null
            if (con == null) {
                getCon();
            }
            //Querying the query
            i = con.createStatement().executeUpdate(SqlQueryString);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return i;
    }
}
