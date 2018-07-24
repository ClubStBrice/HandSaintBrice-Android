package fr.handstbrice.handballstbrice.model;

public class Partenaire {

    private int id;
    private String nom;
    private String url;
    private String urlImg;
    private String offre;


    public Partenaire(int id, String nom, String url, String urlImg, String offre){
        this.id = id;
        this.nom = nom;
        this.url = url;
        this.urlImg = urlImg;
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

    public String getUrlImg() {
        return urlImg;
    }

    public String getOffre() {
        return offre;
    }


}
