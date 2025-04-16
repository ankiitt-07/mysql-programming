package com.address_book;

import com.address_book.daos.AddressBookDAO;
import com.address_book.entities.Contact;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {
        List<Contact>contacts = new ArrayList();
        AddressBookDAO dao = new AddressBookDAO();

        contacts=dao.getAllContacts();

        dao.getContactsByCity("San Francisco");


    }
}