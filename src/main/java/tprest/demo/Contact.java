package tprest.demo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Contact {
    private String prenom;
    private String nom;
    private int annee;
    private int id;

    public Contact() {
    }

    public Contact(String prenom, String nom, int annee) {
        this.prenom = prenom;
        this.nom = nom;
        this.annee = annee;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
