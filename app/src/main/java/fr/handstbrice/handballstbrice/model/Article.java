package fr.handstbrice.handballstbrice.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Article
{
    private int id;
    private long date;
    private String titre;
    private String texte;
    private String imgUrl;
    private String url;

    public Article (int id, String date, String titre, String texte, String imgUrl, String url){
        assert titre != null;
        assert texte != null;
        assert date != null;

        this.id = id;
        try {
            this.date = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss ZZZZZ").parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            this.date=System.currentTimeMillis();
        }
        this.titre = titre;
        this.texte = texte;
        this.imgUrl = imgUrl;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public long getDate() {
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

    public String getUrl() {
        return url;
    }
}
