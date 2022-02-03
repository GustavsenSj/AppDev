package com.example.demo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class App {

    public static void main(String[] args) {
        JDBCLogic test = new JDBCLogic();

        ResultSet rs;

        try {
            test.initialise();

            rs = test.displayUsers();

            while(rs.next()) {
                System.out.println(rs.getString("fname") + " " + rs.getString("lname"));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        test.updateBorrowersNameAndAdress("Sjur",1,"hjemme");

        test.displayBorrowersByTitlePerBranch("bok1");
    }
}
