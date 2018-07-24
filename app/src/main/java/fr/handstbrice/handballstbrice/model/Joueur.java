package fr.handstbrice.handballstbrice.model;

public class Joueur {

    private int id;
    private String nom;
    private String prenom;
    private Equipe equipe;

    public Joueur(int id, String nom, String prenom, Equipe equipe){
        assert nom != null;
        assert equipe != null;
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.equipe = equipe;
        equipe.addJoueur(this);
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




}
