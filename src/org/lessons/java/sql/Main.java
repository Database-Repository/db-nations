package org.lessons.java.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //parametri di connesione
        String url = "jdbc:mysql://localhost:3306/db_nations";
        String user = "root";
        String password = "root";

        try (Connection connection = DriverManager.getConnection(url, user, password)){

        }catch (SQLException e) {
            System.out.println("Unable to open connection");
            e.printStackTrace();
        }

        scanner.close();
    }

}
