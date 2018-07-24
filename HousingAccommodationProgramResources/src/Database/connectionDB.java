package Database;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class connectionDB {

        public connectionDB() {

        }

        public Connection returnConnection() {
    
      try {
      String dbName = "housingdatabase";
      String userName = "musab";
      String password = "Eliter543";
      String hostname = "project.cq0qm9sc13w5.eu-west-2.rds.amazonaws.com";
      String port = "3306";
      String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password+"&?autoReconnect=true&useSSL=false";
      Connection con = DriverManager.getConnection(jdbcUrl);
      return con;
    }
    catch (SQLException e) { System.out.println(e.toString());}
        return null;
        }
    }



