import com.address_book.daos.AddressBookDAO;
import com.address_book.entities.Contact;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;

public class AddressBookDAOTest {
    static AddressBookDAO dao;

    @BeforeAll
    static void init() {
        dao = new AddressBookDAO();
    }

    @Test
    void testAddAndFetch() throws Exception {
        Contact contact = new Contact("Test", "User", "123 Lane", "City", "State",
                "123456", "1112223333", "test@db.com", "Friend");
        dao.addContact(contact);
        List<Contact> contacts = dao.getAllContacts();
        Assertions.assertTrue(contacts.stream().anyMatch(c -> c.getFirstName().equals("Test")));
    }

    @Test
    void testUpdatePhone() throws Exception {
        dao.updatePhone("Test", "9999999999");
        List<Contact> contacts = dao.getAllContacts();
        Contact test = contacts.stream().filter(c -> c.getFirstName().equals("Test")).findFirst().get();
        Assertions.assertEquals("9999999999", test.getPhone());
    }

    @Test
    void testCountByState() throws Exception {
        int count = dao.getCountByState("NSW");
        Assertions.assertTrue(count >= 0);
    }
}