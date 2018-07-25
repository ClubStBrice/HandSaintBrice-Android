package fr.handstbrice.handballstbrice.rss;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

import fr.handstbrice.handballstbrice.model.Match;

public class MatchRSSHandler extends DefaultHandler
{
    private int id; private String equipeLocale; private String equipeExterieure; private int scoreEquipeLocale; private int scoreEquipeExterieure; private String date; private String heure; private String urlEquipeRec; private String urlEquipeVis;
    private boolean saisieId=false, saisieEquipeLocale=false, saisieEquipeExterieure=false, saisieScoreEquipeLocale=false, saisieScoreEquipeExterieure=false, saisieDate=false, saisieHeure=false, saisieUrlEquipeRec=false, saisieUrlEquipeVis=false;
    private boolean saisieNouveauMatch=false;
    private List<Match> matchsList=new ArrayList<>();

    /**
    * Cette fonction est exécuté au moment de la lecture de chaque balise XML
    */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        switch (localName.toLowerCase())
        {
            case "id":
                saisieId=true;
                break;
            case "team_rec":
                saisieEquipeLocale=true;
                break;
            case "team_vis":
                saisieEquipeExterieure=true;
                break;
            case "res_rec":
                saisieScoreEquipeLocale=true;
                break;
            case "res_vis":
                saisieScoreEquipeExterieure=true;
                break;
            case "date_match":
                saisieDate=true;
                break;
            case "heure_match":
                saisieHeure=true;
                break;
            case "urlrec":
                saisieUrlEquipeRec=true;
                break;
            case "urlvis":
                saisieUrlEquipeVis=true;
                break;
            case "match":
                validerSaisieSiNecessaire();
                saisieNouveauMatch=true;
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
        saisieEquipeLocale=false;
        saisieEquipeExterieure=false;
        saisieScoreEquipeLocale=false;
        saisieScoreEquipeExterieure=false;
        saisieDate=false;
        saisieHeure=false;
        saisieUrlEquipeRec=false;
        saisieUrlEquipeVis=false;

    }

    private void validerSaisieSiNecessaire()
    {
        if (saisieNouveauMatch)
        {
            resetSaisie();

            try {
                matchsList.add(new Match(id, equipeLocale, equipeExterieure, scoreEquipeLocale, scoreEquipeExterieure, date, heure, urlEquipeRec, urlEquipeVis));
            } catch (Exception e) {
                e.printStackTrace();
            }

            saisieNouveauMatch=false;
            id=-1;
            equipeLocale=null;
            equipeExterieure=null;
            scoreEquipeLocale=0;
            scoreEquipeExterieure=0;
            date=null;
            heure=null;
            urlEquipeRec=null;
            urlEquipeVis=null;
        }
    }

    /**
    * Cette fonction est executée pour lire le contenu d'une balise.
    * Pour savoir qu'elle balise nous sommes en train de lire, se référer à la fonction 'startElement'
    */
    @Override
    public void characters(char[] ch, int start, int length)
             {
        //on transforme le tableau de caractères 'ch' en une chaine de caractères de type String
        String cdata = new String(ch, start, length);
        if (saisieId) {
            id = Integer.parseInt(cdata);
            saisieId = false;
        } else if (saisieEquipeLocale) {
            equipeLocale = cdata;
            saisieEquipeLocale = false;
        } else if (saisieEquipeExterieure) {
            equipeExterieure = cdata;
            saisieEquipeExterieure = false;
        } else if (saisieScoreEquipeLocale) {
            scoreEquipeLocale = Integer.parseInt(cdata);
            saisieScoreEquipeLocale = false;
        }  else if (saisieScoreEquipeExterieure) {
            scoreEquipeExterieure = Integer.parseInt(cdata);
            saisieScoreEquipeExterieure = false;
        } else if (saisieDate) {
            Log.i("datesaisie", cdata);
            date = cdata;
            saisieDate = false;
        }  else if (saisieHeure) {
            heure = cdata;
            saisieHeure = false;
        } else if (saisieUrlEquipeRec) {
            urlEquipeRec = cdata;
            saisieUrlEquipeRec = false;
        } else if (saisieUrlEquipeVis) {
            urlEquipeVis = cdata;
            saisieUrlEquipeVis = false;
        }
    }

    /**
    * Cette fonction sert à récupérer sous format HTML la lecture de tout le flux RSS.
    */
    public List<Match> getRssResult()
    {
        validerSaisieSiNecessaire();
        return matchsList;
    }
}
