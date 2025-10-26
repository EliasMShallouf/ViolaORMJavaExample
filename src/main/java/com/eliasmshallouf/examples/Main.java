package com.eliasmshallouf.examples;

import com.eliasmshallouf.examples.tests.TestComplexPrimaryKey;
import com.eliasmshallouf.examples.tests.TestDeclareEntityWithoutAnnotationProcessor;
import com.eliasmshallouf.examples.tests.TestSeminarQueries;
import com.eliasmshallouf.orm.ConnectionManager;

public class Main {
    public static ConnectionManager createConnectionManager(
        String driver,
        String url,
        String user,
        String password
    ) {
        try {
            System.out.println("Creating connection with this properties :");
            System.out.println(" - " + driver);
            System.out.println(" - " + url);
            System.out.println(" - " + user);
            System.out.println(" - " + password);
            return new ConnectionManager(driver, url, user, password);
        } catch (Exception e) {
            System.err.println("Error creating connection with the previous properties");
            return null;
        }
    }

    public static void main(String[] args) {
        ConnectionManager connectionManager = createConnectionManager(
        "com.mysql.cj.jdbc.Driver",
        "jdbc:mysql://localhost:3306/viola_test?zeroDateTimeBehavior=convertToNull",
        "root",
        ""
        );

        TestSeminarQueries.test(connectionManager);
        TestSeminarQueries.testPaging(connectionManager);
        TestComplexPrimaryKey.test(connectionManager);
        TestDeclareEntityWithoutAnnotationProcessor.test(connectionManager);
    }
}