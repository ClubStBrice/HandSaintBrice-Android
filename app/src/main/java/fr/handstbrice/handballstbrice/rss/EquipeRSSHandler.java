package fr.handstbrice.handballstbrice.rss;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

import fr.handstbrice.handballstbrice.model.Equipe;

public class EquipeRSSHandler extends DefaultHandler
{
    private int id; private String nom; private String srcImg;
    private boolean saisieId=false, saisieNom=false, saisieUrlImg=false;
    private boolean saisieNouvelleEquipe=false;
    private List<Equipe> equipesList=new ArrayList<>();

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
            case "nom_equipe":
                saisieNom=true;
                break;
            case "photo":
                saisieUrlImg=true;
                break;
            case "equipes":
                validerSaisieSiNecessaire();
                saisieNouvelleEquipe=true;
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
        saisieUrlImg=false;
    }

    private void validerSaisieSiNecessaire()
    {
        if (saisieNouvelleEquipe)
        {
            resetSaisie();

            equipesList.add(new Equipe(id, nom, srcImg));
            saisieNouvelleEquipe=false;
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
        } else if (saisieUrlImg) {
            srcImg = cdata;
            saisieUrlImg = false;
        }
    }

    /**
     * Cette fonction sert à récupérer sous format HTML la lecture de tout le flux RSS.
     */
    public List<Equipe> getRssResult()
    {
        validerSaisieSiNecessaire();
        return equipesList;
    }
}
