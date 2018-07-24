package fr.handstbrice.handballstbrice.model;

import java.util.ArrayList;
import java.util.List;

public class Equipe {

    private int id;
    private String nom;
    private List<Joueur> joueurs=new ArrayList<>();

    public Equipe(int id, String nom){
        assert nom != null;
        this.id = id;
        this.nom = nom;
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
}
