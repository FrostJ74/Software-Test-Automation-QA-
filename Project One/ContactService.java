package contactAssignment;

import java.util.HashMap;
import java.util.Map;

/** In memory storage for Contact options. No database used.
 * 
 * A HashMap on contactId makes the "unique ID" requirement easily enforced
 * while giving a 0(1) lookup.
 */

public class ContactService {
	
	private final Map<String, Contact> contacts = new HashMap<>();
	
	/**To add a contact the ID can not already exist.
	 */
	public void addContact(Contact contact) {
		if (contact == null) {
			throw new IllegalArgumentException("Contact cannot be null");
		}
		if (contacts.containsKey(contact.getContactId())) {
			throw new IllegalArgumentException("Contact ID already exists");
		}
		contacts.put(contact.getContactId(), contact);
	}
	
	/** Deletes contact by ID.
	 */
	public void deleteContact(String contactId) {
		requireExisting(contactId);
		contacts.remove(contactId);
	}
	
	public void updateFirstName(String contactId, String firstName) {
		requireExisting(contactId).setFirstName(firstName);
	}
	
	public void updateLastName(String contactId, String lastName) {
		requireExisting(contactId).setLastName(lastName);
	}
	
	public void updatePhone(String contactId, String phone) {
		requireExisting(contactId).setPhone(phone);
	}
	
	public void updateAddress(String contactId, String address) {
		requireExisting(contactId).setAddress(address);
	}
	
	/** Helper for test. Returns null when not found.
	 */
	public Contact getContact(String contactId) {
		return contacts.get(contactId);
	}
	
	/**Returns contact for given Id or throws if not there.
	 */
	private Contact requireExisting(String contactId) {
		Contact contact = contacts.get(contactId);
		if (contact == null) {
			throw new IllegalArgumentException("Contact ID not found");
		}
		return contact;
	}

}
