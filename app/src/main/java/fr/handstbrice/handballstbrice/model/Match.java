package fr.handstbrice.handballstbrice.model;

import android.net.Uri;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Match {

    private int id;
    private String equipeLocale;
    private String equipeExterieure;
    private int scoreEquipeLocale;
    private int scoreEquipeExterieure;
    private long dateUTC;
    private Uri urlImgLocale, urlImgExterieure;


    public Match(int id, String equipeLocale, String equipeExterieure, int scoreEquipeLocale, int scoreEquipeExterieure, String date, String heure, String urlImgLocale, String urlImgExterieure) throws ParseException {
        this.id = id;
        this.equipeLocale = equipeLocale;
        this.equipeExterieure = equipeExterieure;
        this.scoreEquipeLocale = scoreEquipeLocale;
        this.scoreEquipeExterieure = scoreEquipeExterieure;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateUTC=format.parse(date+" "+heure).getTime();
        if (!urlImgLocale.startsWith("http"))
            urlImgLocale="http://"+urlImgLocale;
        if (!urlImgExterieure.startsWith("http"))
            urlImgExterieure="http://"+urlImgExterieure;
        this.urlImgLocale=Uri.parse(urlImgLocale);
        this.urlImgExterieure=Uri.parse(urlImgExterieure);
    }

    public int getId() {
        return id;
    }

    public String getEquipeLocale() {
        return equipeLocale;
    }

    public String getEquipeExterieure() {
        return equipeExterieure;
    }

    public int getScoreEquipeLocale() {
        return scoreEquipeLocale;
    }

    public int getScoreEquipeExterieure() {
        return scoreEquipeExterieure;
    }

    public long getDateUTC() {
        return dateUTC;
    }

    public Uri getUrlImgLocale() {
        return urlImgLocale;
    }

    public Uri getUrlImgExterieure() {
        return urlImgExterieure;
    }
}
