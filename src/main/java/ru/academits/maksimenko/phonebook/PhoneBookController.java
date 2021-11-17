package ru.academits.maksimenko.phonebook;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.academits.maksimenko.model.Contact;
import ru.academits.maksimenko.model.ContactValidation;
import ru.academits.maksimenko.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/phoneBook/rpc/api/v1")
public class PhoneBookController {
    private static final Logger logger = LoggerFactory.getLogger(PhoneBookController.class);

    private final ContactService contactService;

    public PhoneBookController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping(value = "getAllContacts", method = RequestMethod.GET)
    @ResponseBody
    public List<Contact> getAllContacts() {
        logger.info("called method getAllContacts");
        return contactService.getAllContacts();
    }

    @RequestMapping(value = "addContact", method = RequestMethod.POST)
    @ResponseBody
    public ContactValidation addContact(@RequestBody Contact contact) {
        return contactService.addContact(contact);
    }

    @RequestMapping(value = "deleteContact", method = RequestMethod.POST)
    @ResponseBody
    public void deleteContact(@RequestBody String id) {
        int ids = Integer.parseInt(id);
        contactService.deleteContact(ids);
    }

    @RequestMapping(value = "deleteContacts", method = RequestMethod.POST)
    @ResponseBody
    public void deleteContacts(@RequestBody String id) {
        id = id.substring(1, id.length() - 1);

        int[] indexList = Arrays.stream(id.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        for (int i : indexList) {
            contactService.deleteContact(i);
        }
    }

    @RequestMapping(value = "filter", method = RequestMethod.POST)
    @ResponseBody
    public void filter(@RequestBody String text) {
        text = text.substring(1, text.length() - 1);
        System.out.println(text);
        contactService.filter(text);
    }

    @RequestMapping(value = "resetFilter", method = RequestMethod.POST)
    @ResponseBody
    public void resetFilter() {
        contactService.resetFilter();
    }

    @RequestMapping(value = "download", method = RequestMethod.POST)
    @ResponseBody
    public void download() throws IOException {
        contactService.download();
    }
}