package com.szalay.OpencourtWebApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DecisionDatabase {

    private List<Decision> myDecisionList = new ArrayList<>();

    public DecisionDatabase() {
        this.myDecisionList = new ArrayList<>();
        myDecisionList.add(new Decision("fvyfd", "fvyfd", "fvyfd", "fvyfd", "fvyfd",
                "fvyfd", "fvyfd", "fvyfd", "fvyfd", "fvyfd", "fvyfd",
                "fvyfd", "fvyfd", "fvyfd", "fvyfd", "fvyfd"));
    }

    public List<Decision> getDecisions() {
        return myDecisionList;
    }

    public void reset() {
        myDecisionList.clear();
    }

    public void fullSearch(String searchedTerm) {

        // our SQL SELECT query.
        // if you only need a few columns, specify them by name instead of using "*"
        String query = "SELECT * FROM opencourtdatabase.pp_felulvizsgalat " +
                "WHERE MATCH(bevezeto, rendelkezo, tenyallas, jogiindokolas, zaro)" +
                "AGAINST(" + "\"" + searchedTerm + "\"" + ", IN NATURAL LANGUAGE MODE);";

        try {
            ResultSet rs = getResultSet(query);
            // iterate through the java resultset
            while (rs.next()) {
                myDecisionList.add(new Decision(
                                rs.getString("szam"),
                                rs.getString("hatarozatszoveg"),
                                rs.getString("bevezeto"),
                                rs.getString("rendelkezo"),
                                rs.getString("tenyallas"),
                                rs.getString("jogiindokolas"),
                                rs.getString("zaro"),
                                rs.getString("birosag"),
                                rs.getString("szekhely"),
                                rs.getString("ugytipus"),
                                rs.getString("eljarastipus"),
                                rs.getString("eljarasszakasz"),
                                rs.getString("targy"),
                                rs.getString("dontes"),
                                rs.getString("dontesmasodfok"),
                                rs.getString("donteselsofok")
                        )
                );

            }
        } catch (Exception e) {
            System.err.println("Got an exception in FULLSEARCH method! ");
            System.err.println(e.getMessage());
        }
    }

    private static ResultSet getResultSet(String query) throws ClassNotFoundException {

//               try {
//            Class.forName("com.mysql.jdbc.Driver");
//        }
//        catch (ClassNotFoundException e) {
//            System.err.println("CANNOT FIND CLASS! ");
//            e.printStackTrace();
//        }

        // Defines the JDBC URL. As you can see, we are not specifying
        // the database name in the URL.
        String url = "jdbc:mysql://localhost:3306/opencourtdatabase";
        // "/opencourtdatabase" rész elhagyható, ha még nem készült el az adatbázis

//        root@127.0.0.1:3306

        // Defines username and password to connect to database server.
        String username = "root";
        String password = "Usd04201";

        // SQL command to create a database in MySQL.
//        String sql = "CREATE DATABASE IF NOT EXISTS DEMOADATBAZIS";

        ResultSet rs = null;

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

//            stmt.execute("USE opencourtdatabase;");
            rs= stmt.executeQuery(query);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("NO CONNECTION! ");

        }
//
//        try (Connection conn = DriverManager.getConnection(url, username, password);
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            // execute the query, and get a java resultset
//            stmt.execute("USE opencourtdatabase;");
//            rs = stmt.executeQuery(query);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
        return rs;

    }


}
