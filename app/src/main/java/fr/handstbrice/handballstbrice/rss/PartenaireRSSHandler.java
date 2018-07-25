package fr.handstbrice.handballstbrice.rss;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import fr.handstbrice.handballstbrice.model.Partenaire;

public class PartenaireRSSHandler extends DefaultHandler
{
    private int id; private String nom; private String url; private String urlImg; private String offre;
    private boolean saisieId=false, saisieNom=false, saisieUrl=false, saisieUrlImg=false, saisieOffre=false;
    private boolean saisieNouveauPartenaire=false;
    private List<Partenaire> partenairesList=new ArrayList<>();

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
            case "libelle":
                saisieNom=true;
                break;
            case "url":
                saisieUrl=true;
                break;
            case "photo":
                saisieUrlImg=true;
                break;
            case "offre":
                saisieOffre=true;
                break;
            case "partenaires":
                validerSaisieSiNecessaire();
                saisieNouveauPartenaire=true;
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
        saisieOffre=false;
        saisieUrl=false;
        saisieUrlImg=false;
    }

    private void validerSaisieSiNecessaire()
    {
        if (saisieNouveauPartenaire)
        {
            resetSaisie();

            partenairesList.add(new Partenaire(id, nom, url, urlImg, offre));
            saisieNouveauPartenaire=false;
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
        } else if (saisieUrl) {
            url = cdata;
            saisieUrl = false;
        } else if (saisieUrlImg) {
            urlImg = cdata;
            saisieUrlImg = false;
        }  else if (saisieOffre) {
            offre = cdata;
            saisieOffre = false;
        }
    }

    /**
     * Cette fonction sert à récupérer sous format HTML la lecture de tout le flux RSS.
     */
    public List<Partenaire> getRssResult()
    {
        validerSaisieSiNecessaire();
        return partenairesList;
    }
}
