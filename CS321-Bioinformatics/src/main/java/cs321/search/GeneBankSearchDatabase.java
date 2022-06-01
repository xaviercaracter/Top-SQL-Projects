package cs321.search;

import cs321.btree.BTree;
import cs321.common.ParseArgumentException;
import cs321.common.ParseArgumentUtils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class GeneBankSearchDatabase
{

    public static void main(String[] args) throws Exception
    {
        Connection connection = null;
        try
        {
          // create a database connection
          connection = DriverManager.getConnection("jdbc:sqlite:genebanksearch.db");
          Statement statement = connection.createStatement();
          statement.setQueryTimeout(30);  // set timeout to 30 sec.

          statement.executeUpdate("drop table if exists genebanksearch");
          statement.executeUpdate("create table genebanksearch (sequence string, frequency integer)");
          statement.executeUpdate("insert into genebanksearch values('aatg', 42)");
          //statement.executeUpdate("insert into person values(2, 'CS-HU310')");
          ResultSet rs = statement.executeQuery("select * from genebanksearch");
          while(rs.next())
          {
            // read the result set
            System.out.println("Sequence = " + rs.getString("sequence"));
            System.out.println("Frequency = " + rs.getInt("frequency"));
          }
        }
        catch(SQLException e)
        {
          // if the error message is "out of memory",
          // it probably means no database file is found
          System.err.println(e.getMessage());
        }
        finally
        {
          try
          {
            if(connection != null)
              connection.close();
          }
          catch(SQLException e)
          {
            // connection close failed.
            System.err.println(e.getMessage());
          }
        }

    }

}
