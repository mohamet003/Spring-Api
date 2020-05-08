package tprest.demo;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Contacts {
    private List<Contact> contact;

    public Contacts() {
        this.contact = new ArrayList<>();
    }


    public List<Contact> getContacts() {
        return contact;
    }

    public void setContacts(List<Contact> contacts) {
        this.contact = contacts;
    }
}
