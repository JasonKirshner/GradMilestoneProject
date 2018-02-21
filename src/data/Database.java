package data;

/*
 * @author Spencer Green
 * @author Gabe Landau
 * @author Brajon Andrews
 * @author Jason Kirshner
 * @version 11/10/17
 *
 * Database.java class is to communicate with
 * the remote mysql database and performs the
 * physical tasks in order to obtain or update
 * necessary data from the individual tbales.
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static Database dbInstance = null;
    
    private String uri;
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private String user;
    private String password;

    private Connection conn = null;
    private boolean connected;
    
    public Database() {
      this("", "", "");
    }
    
    public Database(String uri, String user, String password) {
        this.uri = uri;
        this.user = user;
        this.password = password;
        connected = false;
    }

    //static method that returns a new database instance with the application's uri and credentials
    public static Database getInstance() {
        if (dbInstance == null) {
            dbInstance = new Database("jdbc:mysql://127.0.0.1:3306/CapstoneTracker?autoReconnect=true&useSSL=false", "student", "student");
        }
        return dbInstance;
    }

    public boolean connect() {
        if (!connected) {
            try {
                Class.forName(DRIVER);
                System.out.println("MySQL Driver loaded: " + DRIVER);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Oops! There was an error.");
                return false;
            }

            try {
                conn = DriverManager.getConnection(uri, user, password);
                System.out.println("MySQL DB open: " + uri);
                connected = true;
                return true;
            } catch(Exception e) {
                e.printStackTrace();
                System.out.println("Oops! There was an error.");
                return false;
            }
        }
        else {
            return true;
        }
    }

    public boolean close() {
        if (connected) {
            try {
                conn.close();
                System.out.println("MySQL Database closed");
                connected = false;
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Oops! There was an error.");
                return false;
            }
        }
        else {
            return true;
        }
    }

    public ArrayList<ArrayList<String>> getData(String sql, List<String> params) {
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            for (int i = 0; i < params.size(); i++) {
                stmt.setString(i + 1, params.get(i));
            }
            
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int numCols = rsmd.getColumnCount();
            
            int row = 0;
            while (rs.next()) {
                data.add(new ArrayList<String>());
                for (int i = 1; i <= numCols; i++) {
                    data.get(row).add(rs.getString(i));
                }
                row = row + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Oops! There was an error.");
        }
        
        return data;
    }

    public boolean setData(String sql, List<String> params) {
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            for (int i = 0; i < params.size(); i++) {
                stmt.setString(i + 1, params.get(i));
            }
            
            int rc = stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Oops! There was an error.");
            return false;
        }
    }
}