package org.lessons.java.sql;

import java.sql.*;
import java.time.Year;
import java.util.ArrayList;
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
            System.out.printf("%-5s %-15s %-25s%n", "ID", "COUNTRY", "REGION", "CONTINENT");

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

                        System.out.printf("%-5s %-15s %-25s%n", countryId, countryName, regionName, continentName);
                    }
                } catch (SQLException e) {
                    System.out.println("Unable to execute query");
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                System.out.println("Unable to prepare statement");
                e.printStackTrace();
            }

            System.out.print("Choose a country id: ");
            String searchString2 = scanner.nextLine();

            String query2 =
                    "SELECT c.country_id, c.name, l.language  FROM countries c "
                    +"JOIN country_languages cl ON c.country_id  = cl.country_id "
                    +"JOIN languages l ON cl.language_id = l.language_id "
                    + "WHERE c.country_id LIKE ? "
                    + "ORDER BY c.name ";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query2)) {

                preparedStatement.setString(1, '%' + searchString2 + '%');

                try (ResultSet resultSet2 = preparedStatement.executeQuery()) {

                    if (resultSet2.next()){
                        String countryName = resultSet2.getString("c.name");
                        System.out.println();
                        System.out.println("Details for country: " + countryName);
                    }
                    ArrayList<String> languages = new ArrayList<>();
                    while (resultSet2.next()) {
                        languages.add(resultSet2.getString("language"));
                    }
                    System.out.print("Languages: " + languages);

                } catch (SQLException e) {
                    System.out.println("Unable to execute query");
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                System.out.println("Unable to prepare statement");
                e.printStackTrace();
            }

            String query3 = "SELECT * FROM country_stats cs "
                            + "WHERE cs.country_id LIKE ? "
                            + "ORDER BY cs.year DESC LIMIT 1 ";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query3)) {

                preparedStatement.setString(1, '%' + searchString2 + '%');

                try (ResultSet resultSet3 = preparedStatement.executeQuery()) {
                    System.out.println("\n" + "Most recent stats");
                    while (resultSet3.next()) {
                        String countryYear = resultSet3.getString("cs.year");
                        String countryPopulation = resultSet3.getString("cs.population");
                        String countryGdp= resultSet3.getString("cs.gdp");
                        System.out.println("Year: " + countryYear);
                        System.out.println("Population: " + countryPopulation);
                        System.out.println("GDP: " + countryGdp);
                    }

                } catch (SQLException e) {
                    System.out.println("Unable to execute query");
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                System.out.println("Unable to prepare statement");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Unable to open connection");
            e.printStackTrace();
        }
        scanner.close();
    }

}
