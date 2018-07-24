package fr.handstbrice.handballstbrice.model;

import android.net.Uri;

import java.io.Serializable;

public class Partenaire implements Serializable{

    private int id;
    private String nom;
    private String url;
    private Uri urlImg;
    private String offre;


    public Partenaire(int id, String nom, String url, String urlImg, String offre){
        this.id = id;
        this.nom = nom;
        this.url = url;
        this.urlImg = Uri.parse(urlImg);
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

    public Uri getUrlImg() {
        return urlImg;
    }

    public String getOffre() {
        return offre;
    }


}
