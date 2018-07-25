package fr.handstbrice.handballstbrice.model;

import android.net.Uri;

public class Joueur {

    private int id;
    private String nom;
    private String prenom;
    private Equipe equipe;
    private Uri imgURL;
    public Joueur(int id, String nom, String prenom, Equipe equipe){
        this(id, nom, prenom, equipe,null);
    }
    public Joueur(int id, String nom, String prenom, Equipe equipe, String imgUrl){
        assert nom != null;
        assert equipe != null;
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.equipe = equipe;
        equipe.addJoueur(this);
        if (imgUrl==null)
            this.imgURL=null;
        else {
            if (!imgUrl.startsWith("http"))
                imgUrl = "http://" + imgUrl;
            this.imgURL = Uri.parse(imgUrl);
        }
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public Uri getImgURL() {
        return imgURL;
    }
}
