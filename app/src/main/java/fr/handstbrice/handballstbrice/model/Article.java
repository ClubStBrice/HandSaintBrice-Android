package fr.handstbrice.handballstbrice.model;

import java.util.Date;

public class Article {

    private int id;
    private Date date;
    private String titre;
    private String texte;
    private String imgUrl;

    public Article (int id, Date date, String titre, String texte, String imgUrl){
        assert titre != null;
        assert texte != null;

        this.id = id;
        this.date = date;
        this.titre = titre;
        this.texte = texte;
        this.imgUrl = imgUrl;

    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getTitre() {
        return titre;
    }

    public String getTexte() {
        return texte;
    }

    public String getImgUrl() {
        return imgUrl;
    }



}
