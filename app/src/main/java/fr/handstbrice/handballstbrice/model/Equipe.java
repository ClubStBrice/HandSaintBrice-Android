package fr.handstbrice.handballstbrice.model;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Equipe {

    private int id;
    private String nom;
    private Uri srcImg;
    private List<Joueur> joueurs=new ArrayList<>();

    public Equipe(int id, String nom, String srcImg){
        assert nom != null;
        this.id = id;
        this.nom = nom;
        this.srcImg=Uri.parse(srcImg);

    }

    void addJoueur(Joueur j){
        assert j.getEquipe().id == this.id;
        joueurs.add(j);
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }



    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    public Uri getSrcImg() {
        return srcImg;
    }
}
