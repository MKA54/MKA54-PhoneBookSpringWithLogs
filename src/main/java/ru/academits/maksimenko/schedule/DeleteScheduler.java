package ru.academits.maksimenko.schedule;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.academits.maksimenko.model.Contact;
import ru.academits.maksimenko.phonebook.PhoneBookController;

import java.util.List;
import java.util.Random;

@EnableAutoConfiguration
@EnableScheduling
@Component
public class DeleteScheduler {
    @Scheduled(fixedRate = 10000)
    public void deletingRandomContact(){
        List<Contact> contactList = PhoneBookController.getContactService().getAllContacts();

        if (contactList.size() <= 1){
            return;
        }

        Random random = new Random();

        int index = random.nextInt(contactList.size() - 1);

        contactList.remove(index);
    }
}