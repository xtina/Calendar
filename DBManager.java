package calendar;
import java.sql.*;

/**
 * This class is intended be used to be a skelton to create
 *      a database manage class.
 * 
 * Creating connection is expensive so we want to reuse a connection
 *      such that only one connection is created through out the live
 *      of your program.
 * 
 * You should customized this class to fit the needs of your project.
 * 
 * I didn't test it!!!
 * 
 * You must call connect() method the first time you want to
 *      use database. After a connection is established,
 *      whenever you need the connection, you can simply call
 *      DBManager.getConnection(). If no error, getConnection()
 *      should give you the connection. The user of this class
 *      can then use the connection to create a statement to
 *      execute sql statements.
 * 
 * The user of this class is in charge of closing
 *      the statement object that is created outside of this class.
 * 
 * You can add some service functions int this class,
 *      such as getAllUsers(), createAccount(), etc.
 *      if you use these functions a lot.
 * 
 * 
 * @author Kuo-Chuan (Martin) Yeh
 * 
 */
public class DBManager {
    private static Connection dbConnection = null;
    private static final String dbName = "dbDemo";

    /**
     * This method starts embedded Derby engine
     *  and creates a connection.
     * Consequent calls to this function will not
     *  create new connection; instead the existing
     *  will be returned.
     * @return a connection to the database; null if failed
     */
    public static Connection connect() {
        if (dbConnection != null) {
            return dbConnection;
        }
        try {
            // start Embedded Derby engine
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            // create a connection
            dbConnection = DriverManager.getConnection("jdbc:derby:" + dbName + ";create=true");
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            // handle exceptions here
        }
        finally {
            return dbConnection;
        }
    }
    
    /**
     * This method returns the connection we created earlier
     * @return the stored database connection
     */
    public static Connection getConnection() {
        if ( dbConnection == null ) {
            // either return null or try connect again
            return null;
        }
        return dbConnection;
    }
    
    /**
     * This method close the connection. This should be called only
     * when we know the connection is no longed needed.
     */
    public static void close() {
        if (dbConnection != null) {
            try {
            dbConnection.close();
            dbConnection = null;
            } catch (SQLException ex) {
               // exception handle here
            }
        }
    }
    
}