package org.lessons.java.sql;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //parametri di connessione
        String url = "jdbc:mysql://localhost:3306/db_nations";
        String user = "root";
        String password = "root";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {

            System.out.print("Search one country: ");
            String searchString = scanner.nextLine();

            String query =
                    "SELECT  c.country_id, c.name, r.name, c2.name "
                    + "FROM countries c "
                    + "JOIN regions r  ON r.region_id  =  c.region_id "
                    + "JOIN continents c2 ON c2.continent_id = r.continent_id "
                    + "WHERE c.name LIKE ? "
                    + "ORDER BY c.name ";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setString(1, '%' + searchString + '%');

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int countryId = resultSet.getInt("country_id");
                        String countryName = resultSet.getString("c.name");
                        String regionName = resultSet.getString("r.name");
                        String continentName = resultSet.getString("c2.name");

                        System.out.println(countryId + " || " + countryName + " || " + regionName + " || " + continentName);
                    }
                } catch (SQLException e) {
                    System.out.println("Unable to execute query");
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                System.out.println("Unable to prepare statement");
                e.printStackTrace();
            }

            String query2 =
                    "SELECT * FROM countries c "
                    +"JOIN country_languages cl ON c.country_id  = cl.country_id "
                    +"JOIN languages l ON cl.language_id = l.language_id "
                    + "WHERE cl.country_id = 107 "
                    + "ORDER BY c.name ";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query2)) {

                try (ResultSet resultSet2 = preparedStatement.executeQuery()) {
                    System.out.println("\n");
                    while (resultSet2.next()) {
                        int countryId = resultSet2.getInt("country_id");


                        System.out.println(countryId);
                    }
                } catch (SQLException e) {
                    System.out.println("Unable to execute query");
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                System.out.println("Unable to execute query");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Unable to open connection");
            e.printStackTrace();
        }
        scanner.close();
    }

}
