package data;

/*
 * @author Spencer Green
 * @author Gabe Landau
 * @author Brajon Andrews
 * @author Jason Kirshner
 * @version 11/10/17
 *
 * DatabaseObject.java interface is to be implemented
 * in each database object class as a guidline
 */

public interface DatabaseObject {
    public void fetch();
    public void put();
    public void post();
    public void delete();
}
