package ru.academits.maksimenko;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.academits.maksimenko.dao.ContactDao;
import ru.academits.maksimenko.model.Contact;
import ru.academits.maksimenko.service.ContactService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class PhoneBookSpringWithLogsApplicationTests {
    @Autowired
    private ContactService contactService;

    private ContactDao contactDao;
    private List<Contact> contactList;
    private Contact contact;

    @Before
    public void initTest() {
        contactDao = contactDao.getContactDao();
        contactList = contactDao.getAllContacts();

        contact = new Contact();
        contact.setId(2);
        contact.setFirstName("Петр");
        contact.setLastName("Петров");
        contact.setPhone("1234");
        contact.setShown(true);
    }

    @Test
    public void test() {
        assertThat(contactService).isNotNull();
    }

    public void addContactTest() {
        contactList.add(contact);

        Assertions.assertEquals(2, contactList.size());
    }

    public void deleteContactTest() {
        contactList.removeIf(contact -> contact.getId() == 2);

        Assertions.assertEquals(1, contactList.size());
    }
}