package org.example.securitywebapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "qwaza1337";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println(" Подключение");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
