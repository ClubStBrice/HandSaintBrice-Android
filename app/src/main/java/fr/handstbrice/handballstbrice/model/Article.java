package fr.handstbrice.handballstbrice.model;

import android.net.Uri;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Article
{
    private int id;
    private long date;
    private String titre;
    private String texte;
    private Uri imgUrl;
    private Uri url;

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
        this.imgUrl = Uri.parse(imgUrl);
        this.url = Uri.parse(url);
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

    public Uri getImgUrl() {
        return imgUrl;
    }

    public Uri getUrl() {
        return url;
    }
}
