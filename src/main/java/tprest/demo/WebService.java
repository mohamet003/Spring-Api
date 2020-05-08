package tprest.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

@RestController
@RequestMapping("api")
public class WebService {

    private Contacts contacts;

    public WebService() {
       this.contacts = new Contacts();
       this.readFromXml();
    }


    /*
        recuperation d'un contact par son id
     */

    @GetMapping(value = "/contacts/{id}",produces = "application/xml")
    Contact getContactById(@PathVariable int id){
        Contact cont = null;
        for (Contact contact : contacts.getContacts()) {
            if (contact.getId() == id)
                cont = contact;
        }
        return cont;
        }


    /*
        recuperation de tous les contacts
     */
    @GetMapping(value = "/contacts",produces = "application/json")
    Contacts getContacts(HttpServletRequest request, @RequestParam(defaultValue = "0") int annee ){

        if (annee != 0){
            Contacts newcon = new Contacts();
            for (Contact c : contacts.getContacts()) {
                if (c.getAnnee() == annee){
                    newcon.getContacts().add(c);
                }
            }
            return newcon;
        }

        return this.contacts;
    }

    /*
        ajout d'un contact
     */
    @PostMapping(value = "/contacts",produces = "application/json")
    Contacts addContacts(@RequestBody Contact contact){
        contact.setId(this.contacts.getContacts().size()+1);
        contacts.getContacts().add(contact);
        this.saveToXml();
        return this.contacts;
    }

    /*
         éditer d'un contact
     */
    @PutMapping(value = "/contacts/{id}",produces = "application/xml")
    ResponseEntity editContacts(@PathVariable int id,  @RequestBody Contact contact ) {
        Contact contactt = null;
        for (Contact c : contacts.getContacts()) {
            if (c.getId() == id) contactt = c;
        }
        if (contactt != null) {
            contactt.setAnnee(contact.getAnnee());
            contactt.setPrenom(contact.getPrenom());
            contactt.setNom(contact.getNom());
            this.saveToXml();
            return new ResponseEntity<>(contacts, HttpStatus.OK);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /*
        suppression d'un contact
     */
    @DeleteMapping(value="/contact/{id}")
    ResponseEntity deleteContact(@PathVariable int id) {
        Contact contact = null;
        for (Contact c : contacts.getContacts()) {
            if (c.getId() == id) contact = c;
        }
        if (contact != null) {
            contacts.getContacts().remove(contact);
            this.saveToXml();
            return new ResponseEntity<>(contacts, HttpStatus.OK);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



    private String path ="/Users/mohametkone/Desktop/ApiXmlFiles";
    private void readFromXml() {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Contacts.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            File f = new File(path+"/contacts.xml");
            contacts = (Contacts) jaxbUnmarshaller.unmarshal(f);
        } catch (Exception ex) {
            System.out.println("Erreur lecture du fichier:"+ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void saveToXml() {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Contacts.class);
            Marshaller march = jaxbContext.createMarshaller();
            march.marshal(contacts, new File(path+"/contacts.xml"));
        } catch (Exception ex) {
            System.out.println("Erreur écriture du fichier:"+ex.getMessage());
            ex.printStackTrace();
        }
    }

}
