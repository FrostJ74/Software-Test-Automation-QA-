package Test;

import contactAssignment.Contact;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

	class ContactTest {

    @Test
    void testContact() {
        Contact contact = new Contact("ID123", "Jane", "Doe", "5551234567", "123 Main St");

        assertEquals("ID123", contact.getContactId());
        assertEquals("Jane", contact.getFirstName());
        assertEquals("Doe", contact.getLastName());
        assertEquals("5551234567", contact.getPhone());
        assertEquals("123 Main St", contact.getAddress());
    }

    @Test
    void testBoundaryLengthsAreAccepted() {
        Contact contact = new Contact("1234567890", "1234567890", "1234567890",
                "0123456789", "123456789012345678901234567890");

        assertEquals("1234567890", contact.getContactId());
        assertEquals(30, contact.getAddress().length());
    }

    // ---- contact ID ----

    @Test
    void testContactIdCannotBeNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact(null, "Jane", "Doe", "5551234567", "123 Main St"));
    }

    @Test
    void testContactIdToLong() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("12345678901", "Jane", "Doe", "5551234567", "123 Main St"));
    }

    @Test
    void testContactIdIsNotUpdatable() throws NoSuchMethodException {
        // There should be no setter for contactId at all.
        assertThrows(NoSuchMethodException.class,
                () -> Contact.class.getMethod("setContactId", String.class));
    }

    // ---- first name ----

    @Test
    void testFirstNameCannotBeNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID123", null, "Doe", "5551234567", "123 Main St"));
    }

    @Test
    void testFirstNameToLong() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID123", "Bartholomew", "Doe", "5551234567", "123 Main St"));
    }

    // ---- last name ----

    @Test
    void testLastNameCannotBeNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID123", "Jane", null, "5551234567", "123 Main St"));
    }

    @Test
    void testLastNameToLong() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID123", "Jane", "Vandersonlee", "5551234567", "123 Main St"));
    }

    // ---- phone ----

    @Test
    void testPhoneCannotBeNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID123", "Jane", "Doe", null, "123 Main St"));
    }

    @Test
    void testPhoneToAhort() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID123", "Jane", "Doe", "555123456", "123 Main St"));
    }

    @Test
    void testPhoneToLong() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID123", "Jane", "Doe", "55512345678", "123 Main St"));
    }

    @Test
    void testPhoneMustBeAllDigits() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID123", "Jane", "Doe", "555-123456", "123 Main St"));
    }

    // ---- address ----

    @Test
    void testAddressCannotBeNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID123", "Jane", "Doe", "5551234567", null));
    }

    @Test
    void testAddressToLong() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID123", "Jane", "Doe", "5551234567",
                        "1234567890123456789012345678901"));
    }

    // ---- setters ----

    @Test
    void testSettersUpdateValidValues() {
        Contact contact = new Contact("ID123", "Jane", "Doe", "5551234567", "123 Main St");

        contact.setFirstName("John");
        contact.setLastName("Smith");
        contact.setPhone("9998887777");
        contact.setAddress("456 Oak Ave");

        assertEquals("John", contact.getFirstName());
        assertEquals("Smith", contact.getLastName());
        assertEquals("9998887777", contact.getPhone());
        assertEquals("456 Oak Ave", contact.getAddress());
    }

    @Test
    void testSettersRejectInvalidValues() {
        Contact contact = new Contact("ID123", "Jane", "Doe", "5551234567", "123 Main St");

        assertThrows(IllegalArgumentException.class, () -> contact.setFirstName(null));
        assertThrows(IllegalArgumentException.class, () -> contact.setLastName("Vandersonlee"));
        assertThrows(IllegalArgumentException.class, () -> contact.setPhone("12345"));
        assertThrows(IllegalArgumentException.class,
                () -> contact.setAddress("1234567890123456789012345678901"));
    }
}