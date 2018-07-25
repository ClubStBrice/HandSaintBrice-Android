package fr.handstbrice.handballstbrice.rss;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

import fr.handstbrice.handballstbrice.model.Article;

public class ArticleRSSHandler extends DefaultHandler
{
    private int id = -1; private String date; private String titre; private String texte; private String imgUrl = null; private String url;
    private boolean saisieId=false, saisieDate=false, saisieTitre=false, saisieTexte=false, saisieImgUrl=false, saisieUrl=false;
    private boolean saisieNouveauArticle=false;
    private List<Article> articlesList=new ArrayList<>();

    /**
     * Cette fonction est exécuté au moment de la lecture de chaque balise XML
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXException {
        switch (localName.toLowerCase())
        {
            case "id": // ???
                saisieId=true;
                break;
            case "pubDate":
                saisieDate=true;
                break;
            case "title":
                saisieTitre=true;
                break;
            case "description":
                saisieTexte=true;
                break;
            case "imgUrl":
                saisieImgUrl=true;
                break;
            case "link":
                saisieUrl=true;
                break;
            case "item":
                validerSaisieSiNecessaire();
                saisieNouveauArticle=true;
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
        saisieDate=false;
        saisieTitre=false;
        saisieTexte=false;
        saisieImgUrl=false;
        saisieUrl=false;
    }

    private void validerSaisieSiNecessaire()
    {
        if (saisieNouveauArticle)
        {
            resetSaisie();
            articlesList.add(new Article(id, date, titre, texte, imgUrl, url));
            saisieNouveauArticle=false;
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
        } else if (saisieDate) {
            date = cdata;
            saisieDate = false;
        } else if (saisieTitre) {
            titre = cdata;
            saisieTitre = false;
        } else if (saisieTexte) {
            texte = cdata;
            saisieTexte = false;
        }  else if (saisieImgUrl) {
            imgUrl = cdata;
            saisieImgUrl = false;
        } else if (saisieUrl) {
            url = cdata;
            saisieUrl = false;
        }
    }

    /**
     * Cette fonction sert à récupérer sous format HTML la lecture de tout le flux RSS.
     */
    public List<Article> getRssResult()
    {
        validerSaisieSiNecessaire();
        return articlesList;
    }
}
