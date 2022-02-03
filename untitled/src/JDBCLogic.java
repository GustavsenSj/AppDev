import javax.xml.transform.Result;
import java.sql.*;

/**
 * This class Interfaces with the db file and handles the SQL logic
 */
public class JDBCLogic {
    private static Connection connect;
    private static boolean hasData;
    public String bookTitle;
    public String libraryBranch;


    public JDBCLogic() {
        this.hasData = false;
        try {
            getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void initialise() throws SQLException {
        if (!hasData) {
            hasData = true;
            // check for database table
            Statement state = connect.createStatement();
            ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='user'");
            if (!res.next()) {
                System.out.println("Building the User table with prepopulated values.");
                // need to build the table
                Statement state2 = connect.createStatement();
                state2.executeUpdate("create table user(id integer,"
                        + "fName varchar(60)," + "lname varchar(60)," + "primary key (id));");

                // inserting some sample data
                PreparedStatement prep = connect.prepareStatement("insert into user values(?,?,?);");
                prep.setString(2, "John");
                prep.setString(3, "McNeil");
                prep.execute();

                PreparedStatement prep2 = connect.prepareStatement("insert into user values(?,?,?);");
                prep2.setString(2, "Paul");
                prep2.setString(3, "Smith");
                prep2.execute();

            }

        }
    }

    /**
     *

    private void getConnection() {

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //String localDirectory = System.getProperty("Users/sjurgustavsen");
        String jdbc = "jdbc:sqlite:/";
        String jdbcUrl = jdbc +  "untitled/library.db";


        try {
            connect = DriverManager.getConnection(jdbcUrl);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            initialise();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     */
    private void getConnection() throws ClassNotFoundException, SQLException {
        // sqlite driver
        Class.forName("org.sqlite.JDBC");
        // database path, if it's new database, it will be created in the project folder
        connect = DriverManager.getConnection("jdbc:sqlite:library.db");
        initialise();
    }

    public String updateBorrowerNameAndPassword(String BorrowerName, int BorrowerID, String BorrowerAdress) {
        PreparedStatement prepared;
        try {
            prepared = connect.prepareStatement("UPDATE Borrower SET Name = (?), Adress = (?) WHERE borrowerID = (?);");
            prepared.setString(1, BorrowerName);
            prepared.setString(2, BorrowerAdress);
            prepared.setInt(3, BorrowerID);
            prepared.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String successfulUpdate = "Update successful";

        return successfulUpdate;
    }

    public ResultSet displayUsers() throws SQLException, ClassNotFoundException {
        if(connect == null) {
            // get connection
            getConnection();
        }
        Statement state = connect.createStatement();
        ResultSet res = state.executeQuery("select fname, lname from user");
        return res;
    }
}