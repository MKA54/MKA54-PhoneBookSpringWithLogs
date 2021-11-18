package ru.academits.maksimenko.dao;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Repository;
import ru.academits.maksimenko.model.Contact;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ContactDao {
    private final List<Contact> contactList = new ArrayList<>();
    private int newId = 0;

    public ContactDao() {
        Contact contact = new Contact();
        contact.setId(getNewId());
        contact.setFirstName("Иван");
        contact.setLastName("Иванов");
        contact.setPhone("9123456789");
        contact.setShown(true);
        contactList.add(contact);
    }

    private int getNewId() {
        newId++;

        return newId;
    }

    public List<Contact> getAllContacts() {
        return contactList;
    }

    public void add(Contact contact) {
        contact.setId(getNewId());
        contactList.add(contact);
    }

    public void deleteContact(int id) {
        contactList.removeIf(contact -> contact.getId() == id);
    }

    public void filter(String text) {
        for (Contact contact : contactList) {
            if (text.equals(contact.getLastName()) || text.equals(contact.getFirstName()) || text.equals(contact.getPhone())) {
                continue;
            }

            contact.setShown(false);
        }
    }

    public void resetFilter() {
        for (Contact contact : contactList) {
            contact.setShown(true);
        }
    }

    public void download() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Phone book");

        XSSFCellStyle style = createStyleForTitle(workbook);

        Cell cell;
        Row row;

        int rownum = 0;

        row = sheet.createRow(rownum);

        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("№");
        cell.setCellStyle(style);

        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Фамилия");
        cell.setCellStyle(style);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Имя");
        cell.setCellStyle(style);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Телефон");
        cell.setCellStyle(style);

        int number = 1;

        for (Contact contact : contactList) {
            rownum++;
            row = sheet.createRow(rownum);

            cell = row.createCell(0, CellType.NUMERIC);
            cell.setCellValue(number);

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(contact.getFirstName());

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(contact.getLastName());

            cell = row.createCell(3, CellType.NUMERIC);
            cell.setCellValue(contact.getPhone());

            number++;
        }

        workbook.write(new FileOutputStream("phoneBook.xlsx"));
        workbook.close();
    }

    private static XSSFCellStyle createStyleForTitle(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }
}