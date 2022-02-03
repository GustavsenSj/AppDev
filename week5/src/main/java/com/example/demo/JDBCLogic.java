package com.example.demo;

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
     * private void getConnection() {
     * <p>
     * try {
     * Class.forName("org.sqlite.JDBC");
     * } catch (ClassNotFoundException e) {
     * e.printStackTrace();
     * }
     * <p>
     * //String localDirectory = System.getProperty("Users/sjurgustavsen");
     * String jdbc = "jdbc:sqlite:/";
     * String jdbcUrl = jdbc +  "untitled/library.db";
     * <p>
     * <p>
     * try {
     * connect = DriverManager.getConnection(jdbcUrl);
     * } catch (SQLException throwables) {
     * throwables.printStackTrace();
     * }
     * <p>
     * try {
     * initialise();
     * } catch (SQLException e) {
     * e.printStackTrace();
     * }
     * }
     */
    private void getConnection() throws ClassNotFoundException, SQLException {
        // sqlite driver
        Class.forName("org.sqlite.JDBC");
        // database path, if it's new database, it will be created in the project folder
        connect = DriverManager.getConnection("jdbc:sqlite:library.db");
        initialise();
    }

    public String updateBorrowersNameAndAdress(String BorrowerName, int BorrowerID, String BorrowerAdress) {
        PreparedStatement prepared;
        try {
            prepared = connect.prepareStatement("UPDATE Borrowers SET Name = (?), address = (?) WHERE borrowerID = (?);");
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
        if (connect == null) {
            // get connection
            getConnection();
        }
        Statement state = connect.createStatement();
        ResultSet res = state.executeQuery("select fname, lname from user");
        return res;
    }

    public ResultSet displayBorrowersByTitlePerBranch(String bookTitle) {
        PreparedStatement prepared;
        ResultSet result = null;
        try {
            prepared = connect.prepareStatement("SELECT borrowers.name FROM borrowers " +
                    "INNER JOIN loans USING(borrowerID) " +
                    "INNER JOIN bookInSystem USING(bookID) " +
                    "INNER JOIN books USING(ISBN) " +
                    "WHERE title = (?);");
            prepared.setString(1, bookTitle);
            result = prepared.executeQuery();
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //This can just stay like this as I need a default return statement
        //If this returns, it has already failed anyway.
        System.out.println("if you see this then query failed");
        return result;
    }
}