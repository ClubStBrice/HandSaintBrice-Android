package fr.handstbrice.handballstbrice.rss;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MatchRSSHandler extends DefaultHandler {
    private String rssResult="";
    private String title=null;
    private String link=null;
    private boolean item=false;
    private boolean titleR=false;
    private boolean linkR=false;

    /*
    Cette fonction est exécuté au moment de la lecture de chaque balise XML
     */
    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attrs) throws SAXException {
        if (localName.equals("title"))//s'il s'agit de la base 'title'
            titleR=true;

        if (localName.equals("link"))//s'il s'agit de la balise 'link'
            linkR=true;

    }

    /*@Override
    public void endElement(String namespaceURI, String localName,
                           String qName) throws SAXException {
    }*/

    /*
    Cette fonction est executée pour lire le contenu d'une balise.
    Pour savoir qu'elle balise nous sommes en train de lire, se référer à la fonction 'startElement'
     */
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        //on transforme le tableau de caractères 'ch' en une chaine de caractères de type String
        String cdata = new String(ch, start, length);

        if (titleR ) {//si nous sommes en train de lire la balise 'title'
            titleR=false;
            title = (cdata.trim()).replaceAll("\\s+", " ");
        }
        if (linkR) {//si nous sommes en train de lire la balise 'link'
            linkR=false;
            link = (cdata.trim()).replaceAll("\\s+", " ");
        }

        if (title!=null && link!=null)//si nous avons lu un lien et un titre dans le flux RSS
        {
            //on cumul au résultat le code HTML suivant
            rssResult=rssResult+"<a href='"+link+"'>"+title+"</a><br>";
            //on réinitilise les variables suivantes afin de préprarer la lecture de la suite
            //du flux
            title=null;
            link=null;
        }
    }

    /*
    Cette fonction sert à récupérer sous format HTML la lecture de tout le flux RSS.
     */
    public Match getRssResult()
    {
        return rssResult;
    }
}
