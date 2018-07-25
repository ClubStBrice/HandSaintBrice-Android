package fr.handstbrice.handballstbrice.rss;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

import fr.handstbrice.handballstbrice.model.Equipe;
import fr.handstbrice.handballstbrice.model.Joueur;

public class JoueurRSSHandler extends DefaultHandler
{
    private int id; private String nom; private String prenom; private int idEquipe; private String urlJoueur;
    private boolean saisieId=false, saisieNom=false, saisiePrenom=false, saisieIdEquipe=false, saisieUrlJoueur=false;
    private boolean saisieNouveauJoueur=false;
    private final List<Equipe> equipesList;
    private List<Joueur> joueursList=new ArrayList<>();

    public JoueurRSSHandler(List<Equipe> equipesList) {
        this.equipesList = equipesList;
    }

    private Equipe getEquipeById(int id)
    {
        for (Equipe e : equipesList)
            if (e.getId()==id)
                return e;
        return null;
    }

    /**
     * Cette fonction est exécuté au moment de la lecture de chaque balise XML
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXException {
        switch (localName.toLowerCase())
        {
            case "id":
                saisieId=true;
                break;
            case "nom":
                saisieNom=true;
                break;
            case "prenom":
                saisiePrenom=true;
                break;
            case "id_equipe":
                saisieIdEquipe=true;
                break;
            case "photo_joueur":
                saisieUrlJoueur=true;
                break;
            case "joueurs":
                validerSaisieSiNecessaire();
                saisieNouveauJoueur=true;
                break;
            default:
                resetSaisie();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        resetSaisie();
    }

    private void resetSaisie()
    {
        saisieId=false;
        saisieNom=false;
        saisiePrenom=false;
        saisieIdEquipe=false;
        saisieUrlJoueur=false;


    }

    private void validerSaisieSiNecessaire()
    {
        if (saisieNouveauJoueur)
        {
            resetSaisie();

            Equipe e = getEquipeById(idEquipe);
            if(e != null)
                joueursList.add(new Joueur(id, nom, prenom, e, urlJoueur));

            saisieNouveauJoueur=false;
            id=-1;
            idEquipe=-1;
            nom=null;
            prenom=null;
            urlJoueur=null;
        }
    }

    /**
     * Cette fonction est executée pour lire le contenu d'une balise.
     * Pour savoir qu'elle balise nous sommes en train de lire, se référer à la fonction 'startElement'
     */
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        //on transforme le tableau de caractères 'ch' en une chaine de caractères de type String
        String cdata = new String(ch, start, length);
        if (saisieId) {
            id = Integer.parseInt(cdata);
            saisieId = false;
        } else if (saisieNom) {
            nom = cdata;
            saisieNom = false;
        } else if (saisiePrenom) {
            prenom = cdata;
            saisiePrenom = false;
        } else if (saisieIdEquipe) {
            idEquipe = Integer.parseInt(cdata);
            saisieIdEquipe = false;
        } else if (saisieUrlJoueur) {
            urlJoueur = cdata;
            saisieUrlJoueur = false;
        }
    }

    /**
     * Cette fonction sert à récupérer sous format HTML la lecture de tout le flux RSS.
     */
    public List<Joueur> getRssResult()
    {
        validerSaisieSiNecessaire();
        return joueursList;
    }
}
