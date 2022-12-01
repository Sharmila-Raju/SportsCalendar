import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
* Connect to SQL Server, execute a SELECT query, print the results.
*
*/  
public class SelectFromSQLServer
{
 
  private static final String jdbcDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
  
  private static final String jdbcURL = "jdbc:sqlserver://localhost:1433;" +
			"databaseName=master;integratedSecurity=true;";

  
  //private static Connection connection;

  /**
  * main method.
  *
  * @param  args  command line arguments
  */  
  public static void main(String[] args)
  {
    System.out.println("Program started");
    try
    {
       Class.forName(jdbcDriver);
       System.out.println("JDBC driver loaded");
    }
    catch (Exception err)
    {
       System.err.println("Error loading JDBC driver");
       err.printStackTrace(System.err);
       System.exit(0);
    }
    
    Connection databaseConnection= null;
    try
    {
      databaseConnection = DriverManager.getConnection(jdbcURL);
      System.out.println("Connected to the database");
    
      //declare the statement object
      Statement sqlStatement = databaseConnection.createStatement();

      //declare the result set    
      ResultSet rs = null;
  
      //Build the query string, making sure to use column aliases
      String queryString="select * from Calendar";

      //print the query string to the screen
      System.out.println("\nQuery string:");
      System.out.println(queryString);
      
      //execute the query
      rs=sqlStatement.executeQuery(queryString);
      
      //print a header row
      System.out.println("\nDateofEvent\t|\tDayofEvent\t|\tPlace\t|\tNameofGame\t|\tTeams\t");
      System.out.println("----------------------\t|\t----------------\t|\t------------");
      
      //loop through the result set and call method to print the result set row
      while (rs.next())
      {
        printResultSetRow(rs);
      }    
      
      //close the result set
      rs.close();
      System.out.println("Closing database connection");

      //close the database connection
      databaseConnection.close();
    }
    catch (SQLException err)
    {
       System.err.println("Error connecting to the database");
       err.printStackTrace(System.err);
       System.exit(0);
    }
    System.out.println("Program finished");
  }
  
  /**
  * Prints each row in the ResultSet object to the screen.
  *
  * @param  rs  the result set from the SELECT query
  * @throws SQLException SQLException thrown on error
  */  
  public static void printResultSetRow(ResultSet rs) throws SQLException
  {
    //Use the column name alias as specified in the above query
    String DateofEvent= rs.getString("DateofEvent");
    String DayofEvent= rs.getString("DayofEvent");
    String Place= rs.getString("Place");
    String NameofGame= rs.getString("NameofGame");
    String Teams= rs.getString("Teams");
    System.out.println(DateofEvent+"\t|\t"+ DayofEvent+"\t|\t" + Place +"\t|\t" +NameofGame +"\t|\t" + Teams+"\t|\t");  
  }
}