package fr.handstbrice.handballstbrice.model;

import android.net.Uri;

import java.io.Serializable;

public class Partenaire implements Serializable{

    private int id;
    private String nom;
    private String url;
    private String urlImg;
    private String offre;


    public Partenaire(int id, String nom, String url, String urlImg, String offre){
        this.id = id;
        this.nom = nom;
        if (url!=null) {
            if (!url.startsWith("http"))
                url = "http://" + url;

        }
        this.url = url;
        if (urlImg!=null)
        {
            if (!urlImg.startsWith("http"))
                urlImg = "http://" + urlImg;
        }
        this.urlImg = urlImg;
        this.offre = offre;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public Uri getUrl() {
        return url==null?null:Uri.parse(url);
    }

    public Uri getUrlImg() {
        return urlImg==null?null:Uri.parse(urlImg);
    }

    public String getOffre() {
        return offre;
    }


}
