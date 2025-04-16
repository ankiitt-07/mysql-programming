package com.address_book.daos;

import com.address_book.entities.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/address_book";
    private static final String USER = "root";
    private static final String PASS = "root";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public List<Contact> getAllContacts() throws SQLException {
        List<Contact> list = new ArrayList<>();
        String query = "SELECT * FROM address_book";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                list.add(new Contact(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("address"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getString("zip"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("type")
                ));
            }
        }
        return list;
    }

    public void addContact(Contact contact) throws SQLException {
        String insertQuery = "INSERT INTO address_book VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
            stmt.setString(1, contact.getFirstName());
            stmt.setString(2, contact.getLastName());
            stmt.setString(3, contact.getAddress());
            stmt.setString(4, contact.getCity());
            stmt.setString(5, contact.getState());
            stmt.setString(6, contact.getZip());
            stmt.setString(7, contact.getPhone());
            stmt.setString(8, contact.getEmail());
            stmt.setString(9, contact.getType());
            stmt.executeUpdate();
        }
    }

    public void updatePhone(String firstName, String newPhone) throws SQLException {
        String update = "UPDATE address_book SET phone = ? WHERE first_name = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(update)) {
            stmt.setString(1, newPhone);
            stmt.setString(2, firstName);
            stmt.executeUpdate();
        }
    }

    public List<Contact> getContactsByCity(String city) throws SQLException {
        List<Contact> list = new ArrayList<>();
        String query = "SELECT * FROM address_book WHERE city = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, city);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Contact(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("address"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getString("zip"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("type")
                ));
            }
        }
        return list;
    }

    public int getCountByState(String state) throws SQLException {
        String query = "SELECT COUNT(*) FROM address_book WHERE state = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, state);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }
}