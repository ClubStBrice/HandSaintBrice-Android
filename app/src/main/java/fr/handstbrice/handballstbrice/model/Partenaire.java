package fr.handstbrice.handballstbrice.model;

public class Partenaire {

    private int id;
    private String nom;
    private String url;
    private String img;
    private String offre;


    public Partenaire(int id, String nom, String url, String img, String offre){
        this.id = id;
        this.nom = nom;
        this.url = url;
        this.img = img;
        this.offre = offre;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getUrl() {
        return url;
    }

    public String getImg() {
        return img;
    }

    public String getOffre() {
        return offre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setOffre(String offre) {
        this.offre = offre;
    }
}
