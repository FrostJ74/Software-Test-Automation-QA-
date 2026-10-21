package Test;

import contactAssignment.Contact;
import contactAssignment.ContactService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContactServiceTest {

    private ContactService service;

    @BeforeEach
    void setUp() {
        service = new ContactService();
        service.addContact(new Contact("ID123", "Jane", "Doe", "5551234567", "123 Main St"));
    }

    // ---- add contact ----

    @Test
    void testAddContactStoresIt() {
        Contact added = service.getContact("ID123");

        assertNotNull(added);
        assertEquals("Jane", added.getFirstName());
    }

    @Test
    void testAddContactRejectsDuplicateId() {
        Contact duplicate = new Contact("ID123", "John", "Smith", "9998887777", "456 Oak Ave");

        assertThrows(IllegalArgumentException.class, () -> service.addContact(duplicate));
    }

    @Test
    void testAddContactRejectsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.addContact(null));
    }

    @Test
    void testAddMultipleUniqueContacts() {
        service.addContact(new Contact("ID456", "John", "Smith", "9998887777", "456 Oak Ave"));

        assertNotNull(service.getContact("ID123"));
        assertNotNull(service.getContact("ID456"));
    }

    // ---- delete contact ----

    @Test
    void testDeleteContactRemovesIt() {
        service.deleteContact("ID123");

        assertNull(service.getContact("ID123"));
    }

    @Test
    void testDeleteContactRejectsUnknownId() {
        assertThrows(IllegalArgumentException.class, () -> service.deleteContact("NOPE"));
    }

    // ---- update contact ----

    @Test
    void testUpdateFirstName() {
        service.updateFirstName("ID123", "John");

        assertEquals("John", service.getContact("ID123").getFirstName());
    }

    @Test
    void testUpdateLastName() {
        service.updateLastName("ID123", "Smith");

        assertEquals("Smith", service.getContact("ID123").getLastName());
    }

    @Test
    void testUpdatePhone() {
        service.updatePhone("ID123", "9998887777");

        assertEquals("9998887777", service.getContact("ID123").getPhone());
    }

    @Test
    void testUpdateAddress() {
        service.updateAddress("ID123", "456 Oak Ave");

        assertEquals("456 Oak Ave", service.getContact("ID123").getAddress());
    }

    @Test
    void testUpdateRejectsUnknownId() {
        assertThrows(IllegalArgumentException.class,
                () -> service.updateFirstName("NOPE", "John"));
        assertThrows(IllegalArgumentException.class,
                () -> service.updateLastName("NOPE", "Smith"));
        assertThrows(IllegalArgumentException.class,
                () -> service.updatePhone("NOPE", "9998887777"));
        assertThrows(IllegalArgumentException.class,
                () -> service.updateAddress("NOPE", "456 Oak Ave"));
    }

    @Test
    void testUpdateRejectsInvalidValues() {
        assertThrows(IllegalArgumentException.class,
                () -> service.updateFirstName("ID123", "Bartholomew"));
        assertThrows(IllegalArgumentException.class,
                () -> service.updateLastName("ID123", null));
        assertThrows(IllegalArgumentException.class,
                () -> service.updatePhone("ID123", "555"));
        assertThrows(IllegalArgumentException.class,
                () -> service.updateAddress("ID123", "1234567890123456789012345678901"));
    }

    @Test
    void testFailedUpdateLeavesContactUnchanged() {
        assertThrows(IllegalArgumentException.class,
                () -> service.updateFirstName("ID123", "Bartholomew"));

        assertEquals("Jane", service.getContact("ID123").getFirstName());
    }
}
