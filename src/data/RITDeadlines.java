package data;

import java.util.*;
import java.sql.*;

public class RITDeadlines implements DatabaseObject {
    private int term;
    private String addDropDate;
    private String gradesDue;

    private static final Database db = Database.getInstance();

    public RITDeadlines() {
        this(0, "", "");
    }

    public RITDeadlines(int term, String addDropDate, String gradesDue) {
        this.term = term;
        this.addDropDate = addDropDate;
        this.gradesDue = gradesDue;
    }

    public RITDeadlines(int term) {
        this.term = term;
        fetch();
    }
    
    static {
        db.connect();
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public String getAddDropDate() {
        return addDropDate;
    }

    public void setAddDropDate(String addDropDate) {
        this.addDropDate = addDropDate;
    }

    public String getGradesDue() {
        return gradesDue;
    }

    public void setGradesDue(String gradesDue) {
        this.gradesDue = gradesDue;
    }

    public void fetch() {
        String query = "SELECT AddDropDate, GradesDue FROM RITDeadlines WHERE Term = ?;";
        List<String> list = new ArrayList(Arrays.asList(Integer.toString(term)));

        ArrayList<ArrayList<String>> status = db.getData(query, list);

        addDropDate = status.get(0).get(0);
        gradesDue = status.get(0).get(1);
    }

    public void put() {
        String query = "UPDATE RITDeadlines SET AddDropDate = ?, GradesDue = ? WHERE Term = ?;";
        List<String> list = new ArrayList(Arrays.asList(addDropDate, gradesDue, Integer.toString(term)));
        db.setData(query, list);
    }

    public void post() {
        String query = "INSERT INTO RITDeadlines(Term, AddDropDate, GradesDue) VALUES (?, ?, ?);";
        List<String> list = new ArrayList(Arrays.asList(Integer.toString(term), addDropDate, gradesDue));

        db.setData(query, list);
    }

    public void delete() {
        String query = "DELETE FROM RITDeadlines WHERE Term = ?;";
        List<String> list = new ArrayList(Arrays.asList(Integer.toString(term)));
        db.setData(query, list);
    }
}