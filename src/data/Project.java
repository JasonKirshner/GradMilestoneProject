package data;

/*
 * @author Jason Kirshner
 * @author Brajon Andrews
 * @author Spencer Green
 * @author Gabe Landau
 * @version 11/10/17
 *
 * Project.java Object class handles CRUD operations for 
 * the Project table in association with Database.java
 */
import java.sql.*;
import java.util.*;

public class Project implements DatabaseObject {

    //IVARS
    private int projectID;
    private String name;
    private String type;
    private int startTerm;
    private String deadline;
    private String description;
    private int plagiarismScore;
    private int grade;
    private static final Database db = Database.getInstance();

    // Default Constructor
    public Project() {
        this("", "", "", 0, "", 0, 0);
    }

    // Constructor that accepts and sets the id.
    public Project(int projectID) {
        this.projectID = projectID;
        this.fetch();
    }

    public Project(String name, String type, String description, int startTerm, String deadline, int plagiarismScore, int grade) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.startTerm = startTerm;
        this.deadline = deadline;
        this.plagiarismScore = plagiarismScore;
        this.grade = grade;
    }

    static {
        db.connect();
    }

    //Accessor Methods
    public int getProjectID() {
        return projectID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public int getStartTerm() {
        return startTerm;
    }

    public String getDeadline() {
        return deadline;
    }

    public int getPlagiarismScore() {
        return plagiarismScore;
    }

    public int getGrade() {
        return grade;
    }

    //Mutator Methods
    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStartTerm(int startTerm) {
        this.startTerm = startTerm;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setPlagiarismScore(int plagiarismScore) {
        this.plagiarismScore = plagiarismScore;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    //DATABASE OPERATIONS
    public ArrayList<Integer> getPeople() {
        String query = "SELECT PersonID FROM PersonProject WHERE ProjectID = ?;";
        List<String> list = new ArrayList(Arrays.asList(Integer.toString(projectID)));
        ArrayList<ArrayList<String>> results = db.getData(query, list);

        ArrayList<Integer> people = new ArrayList<Integer>();

        for (ArrayList<String> row : results) {
            people.add(Integer.parseInt(row.get(0)));
        }

        return people;
    }

    public void addStatus(int statusID, String dateGiven, String comments) {
        String query = "INSERT INTO ProjectStatus (StatusID, ProjectID, DateGiven, Comments) VALUES (?, ?, ?, ?);";
        List<String> list = new ArrayList(Arrays.asList(Integer.toString(statusID), Integer.toString(projectID), dateGiven, comments));
        db.setData(query, list);
    }

    public void removeStatus(int statusID) {
        String query = "DELETE FROM ProjectStatus WHERE StatusID = ? AND ProjectID = ?;";
        List<String> list = new ArrayList(Arrays.asList(Integer.toString(statusID), Integer.toString(projectID)));
        db.setData(query, list);
    }

    public ArrayList<Integer> getStatus() {
        String query = "SELECT * FROM ProjectStatus WHERE ProjectID = ? ORDER BY DateGiven DESC;";
        List<String> list = new ArrayList(Arrays.asList(Integer.toString(projectID)));
        ArrayList<ArrayList<String>> results = db.getData(query, list);

        ArrayList<Integer> status = new ArrayList<Integer>();

        System.out.println("Project ID: " + projectID);
        
        for (ArrayList<String> row : results) {
            status.add(Integer.parseInt(row.get(0)));
        }

        return status;
    }
    
    public String getCurrentStatus() {
        ArrayList<Integer> statuses = getStatus();
        
        String query = "SELECT Name FROM Status WHERE StatusID = ?;";
        List<String> params = new ArrayList(Arrays.asList(Integer.toString(statuses.get(0))));
        ArrayList<ArrayList<String>> results = db.getData(query, params);
        
        return results.get(0).get(0);
    }

    //Stores selected project data into the object variables
    public void fetch() {
        String query
                = "SELECT Name, ProjectType, Description, StartTerm, Deadline, PlagiarismScore, Grade FROM Project WHERE ProjectID = ?;";

        List<String> list = new ArrayList<String>(Arrays.asList(Integer.toString(projectID)));

        ArrayList<ArrayList<String>> prj = db.getData(query, list);
        if (prj.size() > 0) {
            this.name = prj.get(0).get(0);
            this.type = prj.get(0).get(1);
            this.description = prj.get(0).get(2);
            this.startTerm = Integer.parseInt(prj.get(0).get(3));
            this.deadline = prj.get(0).get(4);
            this.plagiarismScore = Integer.parseInt(prj.get(0).get(5));
            this.grade = Integer.parseInt(prj.get(0).get(6));
        } else {
            this.name = null;
            this.type = null;
            this.description = null;
            this.startTerm = 0;
            this.deadline = null;
            this.plagiarismScore = 0;
            this.grade = 0;
        }
    }

    //Updates selected project pertaining to current stored variables
    public void put() {
        String query = "UPDATE Project SET Name = ?, ProjectType = ?"
                + ", Description = ?, StartTerm = ?, Deadline = ?, "
                + "PlagiarismScore = ?, Grade = ? WHERE ProjectId = ?;";

        List<String> list = new ArrayList<String>(Arrays.asList(name, type, description, Integer.toString(startTerm),
                deadline, Integer.toString(plagiarismScore),
                Integer.toString(grade),
                Integer.toString(projectID)));
        db.setData(query, list);
    }

    //Inserts new projects into the project table
    public void post() {
        String query = "INSERT INTO Project (Name, ProjectType, Description, StartTerm, Deadline, PlagiarismScore, Grade)"
                + " VALUES(?, ?, ?, ?, ?, ?, ?);";

        List<String> list = new ArrayList<String>(Arrays.asList(name, type, description,
                Integer.toString(startTerm), deadline, Integer.toString(plagiarismScore),
                Integer.toString(grade)));

        db.setData(query, list);
    }

    //Deletes selected project
    public void delete() {
        String query = "DELETE FROM Project WHERE ProjectId = ?;";
        List<String> list = new ArrayList<String>(Arrays.asList(Integer.toString(projectID)));

        db.setData(query, list);
    }

    public static List<Project> getAll() {
        ArrayList<Project> projects = new ArrayList<Project>();

        String query = "SELECT ProjectID FROM Project;";
        List<String> params = new ArrayList();
        ArrayList<ArrayList<String>> results = db.getData(query, params);

        ArrayList<Integer> ids = new ArrayList<Integer>();

        for (ArrayList<String> row : results) {
            ids.add(Integer.parseInt(row.get(0)));
        }

        for (Integer i : ids) {
            Project p = new Project(i);
            projects.add(p);
        }

        return projects;
    }
    
    public List<Person> getStudentsFromProject() {
        String query = "SELECT PersonID FROM PersonProject WHERE ProjectID = ? AND Role = 'Student';";
        List<String> params = new ArrayList();
        params.add(this.projectID + "");

        ArrayList<ArrayList<String>> results = db.getData(query, params);
        List<Person> people = new ArrayList<>();

        if (results.size() > 0) {
            for (ArrayList<String> list : results) {
                for (String id : list) {
                    people.add(new Person(Integer.parseInt(id)));
                }
            }
            
            return people;
        } else {
            return new ArrayList<Person>();
        }
    }
    
    public boolean addStudentToProject(String username) {
        String query = "SELECT PersonID FROM Person WHERE UserName = ?";
        List<String> params = new ArrayList();
        params.add(username);
        
        int result = Integer.parseInt(db.getData(query, params).get(0).get(0));
        
        query = "INSERT INTO PersonProject VALUES (?, ?, ?);";
        params = new ArrayList();
        params.add(result + "");
        params.add(this.projectID + "");
        params.add("Student");
        
        return db.setData(query, params);
    }
}
