package fr.handstbrice.handballstbrice.model;

public class Joueur {

    private int id;
    private String nom;
    private String prenom;
    private int idEquipe;

    public Joueur(int id, String nom, String prenom, int idEquipe){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.idEquipe = idEquipe;
    }

    public int getId(){
        return id;
    }

    public String getNom(){
        return nom;
    }

    public String getPrenom(){
        return prenom;
    }

    public int getIdEquipe(){
        return idEquipe;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public void setPrenom(String prenom){
        this.prenom = prenom;
    }

    public void setIdEquipe(int idEquipe){
        this.idEquipe = idEquipe;
    }


}
