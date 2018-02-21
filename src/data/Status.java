package data;

import java.util.*;
import java.sql.*;

public class Status implements DatabaseObject {
    private int statusID;
    private String name;
    private String description;

    private static final Database db = Database.getInstance();

    public Status() {
        this("", "");
    }

    public Status(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Status(int statusID) {
        this.statusID = statusID;
        fetch();
    }
    
    static {
        db.connect();
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void fetch() {
        String query = "SELECT Name, Description FROM Status WHERE StatusID = ?;";
        List<String> list = new ArrayList(Arrays.asList(Integer.toString(statusID)));

        ArrayList<ArrayList<String>> status = db.getData(query, list);

        name = status.get(0).get(0);
        description = status.get(0).get(1);
    }

    public void put() {
        String query = "UPDATE Status SET Name = ?, Description = ? WHERE StatusID = ?;";
        List<String> list = new ArrayList(Arrays.asList(name, description, Integer.toString(statusID)));
        db.setData(query, list);
    }

    public void post() {
        String query = "INSERT INTO Status(Name, Description) VALUES (?, ?);";
        List<String> list = new ArrayList(Arrays.asList(name, description));

        db.setData(query, list);

        //get new ID after inserting
        String idQuery = "SELECT last_insert_id() AS LAST_ID FROM Status";
        ArrayList<ArrayList<String>> idAL = db.getData(idQuery, Arrays.asList());
        this.statusID = Integer.parseInt(idAL.get(0).get(0));
    }

    public void delete() {
        String query = "DELETE FROM Status WHERE StatusID = ?;";
        List<String> list = new ArrayList(Arrays.asList(Integer.toString(statusID)));
        db.setData(query, list);
    }
}