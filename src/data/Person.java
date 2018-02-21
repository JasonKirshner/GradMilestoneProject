package data;

/*
 * @author Jason Kirshner
 * @author Spencer Green
 * @author Gabe Landau
 * @author Brajon Andrews
 * @version 11/10/17
 *
 * People.java Object class handles CRUD operations 
 * for the People table in association with Database.java
 */
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;
import java.sql.*;

public class Person implements DatabaseObject {

    //IVARS
    private int personID;
    private String firstName;
    private String lastName;
    private String hashedPassword;
    private String email;
    private String username;
    private String gradDate;
    private String department;
    private String roleName;
    private String major;
    private ArrayList<Project> projects;
    private static final Database db = Database.getInstance();

    //CONSTRUCTORS
    public Person() {
        this("", "", "", "", "", "", "", "", "");
    }

    public Person(int personID) {
        this.personID = personID;
        this.fetch();
    }

    static {
        db.connect();
    }

    public Person(String firstName, String lastName,
            String password, String email,
            String username, String gradDate,
            String department, String roleName, String major) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.setPassword(password);
        this.email = email;
        this.username = username;
        this.gradDate = gradDate;
        this.department = department;
        this.roleName = roleName;
        this.major = major;
    }

    //MUTATORS
    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.hashedPassword = hashString(password);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setGradDate(String gradDate) {
        this.gradDate = gradDate;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    //ACCESSORS
    public int getPersonID() {
        return personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public String getGradDate() {
        return gradDate;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getUsername() {
        return username;
    }

    public String getMajor() {
        return major;
    }

    //DATABASE OPERATIONS
    public ArrayList<Integer> getProjects() {
        String query = "SELECT ProjectID FROM PersonProject WHERE PersonID = ?;";
        List<String> list = new ArrayList(Arrays.asList(Integer.toString(personID)));
        ArrayList<ArrayList<String>> results = db.getData(query, list);

        ArrayList<Integer> projects = new ArrayList<Integer>();

        for (ArrayList<String> row : results) {
            projects.add(Integer.parseInt(row.get(0)));
        }

        return projects;
    }

    public void addToProject(int projectID, String role) {
        String query = "INSERT INTO PersonProject (PersonID, ProjectID, Role) VALUES (?, ?, ?);";
        List<String> list = new ArrayList(Arrays.asList(Integer.toString(personID), Integer.toString(projectID), role));
        db.setData(query, list);
    }

    public void removeFromProject(int projectID) {
        String query = "DELETE FROM PersonProject WHERE PersonID = ? AND ProjectID = ?;";
        List<String> list = new ArrayList(Arrays.asList(Integer.toString(personID), Integer.toString(projectID)));
        db.setData(query, list);
    }

    //Stores selected person data into the object variables
    public void fetch() {
        String query
                = "SELECT FirstName, LastName, UserName, UserPassword, Email, GraduationDate, Department, Major, RoleName From Person WHERE PersonID = ?;";

        List<String> list = new ArrayList(Arrays.asList(Integer.toString(personID)));

        ArrayList<ArrayList<String>> ppl = db.getData(query, list);
        if (ppl.size() > 0) {
            this.firstName = ppl.get(0).get(0);
            this.lastName = ppl.get(0).get(1);
            this.username = ppl.get(0).get(2);
            this.hashedPassword = ppl.get(0).get(3);
            this.email = ppl.get(0).get(4);
            this.gradDate = ppl.get(0).get(5);
            this.department = ppl.get(0).get(6);
            this.major = ppl.get(0).get(7);
            this.roleName = ppl.get(0).get(8);
        } else {
            this.firstName = null;
            this.lastName = null;
            this.username = null;
            this.hashedPassword = null;
            this.email = null;
            this.gradDate = null;
            this.department = null;
            this.major = null;
            this.roleName = null;
        }
    }

    //Updates selected person pertaining to current stored variables
    public void put() {
        String query = "UPDATE Person SET FirstName = ?, LastName = ?"
                + ", UserName = ?, UserPassword = ?, Email = ?, "
                + "GraduationDate = ?, Department = ?, Major = ?, RoleName = ? WHERE PersonID = ?;";

        List<String> list = new ArrayList(Arrays.asList(firstName, lastName, username, hashedPassword,
                email, gradDate, department, major,
                roleName, Integer.toString(personID)));
        db.setData(query, list);
    }

    //Inserts new person into the person table
    public void post() {
        String query = "INSERT INTO Person (FirstName, LastName, UserName, UserPassword, Email, GraduationDate, Department, Major, RoleName)"
                + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";

        List<String> list = new ArrayList(Arrays.asList(firstName, lastName, username,
                hashedPassword, email, gradDate, department, major, roleName));

        db.setData(query, list);

        //get new ID after inserting
        String idQuery = "SELECT last_insert_id() AS LAST_ID FROM Person";
        ArrayList<ArrayList<String>> idAL = db.getData(idQuery, Arrays.asList());
        this.personID = Integer.parseInt(idAL.get(0).get(0));
    }

    //Deletes selected person record
    public void delete() {
        String query = "DELETE FROM Person WHERE PersonID = ?;";
        List<String> list = new ArrayList(Arrays.asList(Integer.toString(personID)));

        db.setData(query, list);
    }

    public static List<Person> getAll() {
        ArrayList<Person> people = new ArrayList<Person>();

        String query = "SELECT PersonID FROM Person;";
        List<String> params = new ArrayList();
        ArrayList<ArrayList<String>> results = db.getData(query, params);

        ArrayList<Integer> ids = new ArrayList<Integer>();

        for (ArrayList<String> row : results) {
            ids.add(Integer.parseInt(row.get(0)));
        }

        for (Integer i : ids) {
            Person p = new Person(i);
            people.add(p);
        }

        return people;
    }

    public static Person attemptLogin(String username, String password) {
        String query = "SELECT PersonID FROM Person WHERE UserName = ? AND UserPassword = ?;";
        List<String> params = new ArrayList();
        
        String hashedPassword = hashString(password);
        
        params.add(username);
        params.add(hashedPassword);
        ArrayList<ArrayList<String>> results = db.getData(query, params);

        int id;
        
        if (results.size() > 0) {
            id = Integer.parseInt(results.get(0).get(0));
            return new Person(id);
        } else {
            return null;
        }
    }

    public int getProjectForStudent() {
        String query = "SELECT ProjectID FROM PersonProject WHERE PersonID = ?;";
        List<String> params = new ArrayList();
        params.add(this.personID + "");

        ArrayList<ArrayList<String>> results = db.getData(query, params);

        if (results.size() > 0) {
            return Integer.parseInt(results.get(0).get(0));
        } else {
            return -1;
        }
    }

    public static String hashString(String s) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] digest = sha256.digest(s.getBytes("UTF-8"));
            String hashed = String.format("%064x", new BigInteger(1, digest));
            return hashed;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
