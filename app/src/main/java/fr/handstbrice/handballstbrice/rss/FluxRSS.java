package fr.handstbrice.handballstbrice.rss;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.util.ArrayList;
import java.util.List;

import fr.handstbrice.handballstbrice.model.Equipe;
import fr.handstbrice.handballstbrice.model.Match;
import fr.handstbrice.handballstbrice.model.Partenaire;

// http://dev-handstbrice.fr/api-stbrice/?action=get_list_partenaires
// http://dev-handstbrice.fr/api-stbrice/?action=get_list_equipes
// http://dev-handstbrice.fr/api-stbrice/?action=get_last_match
// http://dev-handstbrice.fr/api-stbrice/?action=get_next_match
// http://dev-handstbrice.fr/page-article/feed

public class FluxRSS {
    public static AsyncTask<String,Void, List<Partenaire>> scanListPartenaires() {
    }

    public static AsyncTask<String,Void, List<Equipe>> scanListEquipes() {
    }

    public static AsyncTask<String,Void, List<Article>> scanArticles() {
    }

    public static AsyncTask<String,Void, List<Match>> scanNextMatch() {
    }

    public static AsyncTask<String,Void, List<Match>> scanLastMatch()
    {
        return new AsyncTask<String, Void, List<Match>>()
        {
            //lorsqu'android est prêt, la tâche est executée ici
            @Override
            protected List<Match> doInBackground(String... urls) {
                try {
                    //à partir de cette URL
                    URL rssUrl = new URL("http://dev-handstbrice.fr/api-stbrice/?action=get_last_match");
                    //on construit un parseur (parcoureur) de contenu XML
                    SAXParserFactory factory = SAXParserFactory.newInstance();
                    SAXParser saxParser = factory.newSAXParser();
                    XMLReader xmlReader = saxParser.getXMLReader();

                    //grâce à notre lecteur de contenu de RSS
                    MatchRSSHandler rssHandler = new MatchRSSHandler();
                    //on informe notre parser quelle classe il devra utiliser lors de la lecture
                    //de chaque élément XML
                    xmlReader.setContentHandler(rssHandler);
                    //on cré un flux de données à partir de l'URL définie plus haut
                    InputSource inputSource = new InputSource(rssUrl.openStream());

                    //on demande à notre parser de parcourir tout le contenu XML renvoyé par
                    //le flux de données inputSource
                    xmlReader.parse(inputSource);

                    //on retourne le résultat
                    return rssHandler.getRssResult();

                } catch (Exception e) {
                    e.printStackTrace();
                    return new ArrayList<>();
                }
            }

        };
    }
}
