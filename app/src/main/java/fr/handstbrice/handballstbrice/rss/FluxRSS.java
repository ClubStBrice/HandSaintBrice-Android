package fr.handstbrice.handballstbrice.rss;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Base64;
import android.widget.TextView;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import fr.handstbrice.handballstbrice.R;
import fr.handstbrice.handballstbrice.model.Article;
import fr.handstbrice.handballstbrice.model.Equipe;
import fr.handstbrice.handballstbrice.model.Joueur;
import fr.handstbrice.handballstbrice.model.Match;
import fr.handstbrice.handballstbrice.model.Partenaire;

public class FluxRSS
{
    private static URL getURL(String relativeURL, Context context) throws MalformedURLException {
        String base=context.getString(R.string.base_url);
        if (relativeURL.startsWith("/") && base.startsWith("/"))
            relativeURL=relativeURL.substring(1, relativeURL.length()-1);
        String separator;
        if (!relativeURL.startsWith("/") && !base.startsWith("/"))
            separator="/";
        else
            separator="";
        return new URL(base+separator+relativeURL);
    }

    private static InputStream getInputStream(URL url, Context context) throws IOException {

        String name=context.getString(R.string.authenticatiion_name);
        String pwd=context.getString(R.string.authenticatiion_password);
        if (name.length() > 0) {
            URLConnection uc = url.openConnection();
            String userpass =name +":" + pwd;
            String basicAuth = "Basic" + new String(Base64.encode(userpass.getBytes(), Base64.DEFAULT));
            uc.setRequestProperty("Authorization", basicAuth);
            return uc.getInputStream();
        }
        else
        {
            return url.openStream();
        }
    }


    public static AsyncTask<String,Void, List<Partenaire>> scanListPartenaires(final Context context)
    {
        return new AsyncTask<String, Void, List<Partenaire>>()
        {
            //lorsqu'android est prêt, la tâche est executée ici
            @Override
            protected List<Partenaire> doInBackground(String... urls) {
                try {
                    //à partir de cette URL
                    URL rssUrl = getURL("api-stbrice/?action=get_list_partenaires", context);
                    //on construit un parseur (parcoureur) de contenu XML
                    SAXParserFactory factory = SAXParserFactory.newInstance();
                    SAXParser saxParser = factory.newSAXParser();
                    XMLReader xmlReader = saxParser.getXMLReader();

                    //grâce à notre lecteur de contenu de RSS
                    PartenaireRSSHandler rssHandler = new PartenaireRSSHandler();
                    //on informe notre parser quelle classe il devra utiliser lors de la lecture
                    //de chaque élément XML
                    xmlReader.setContentHandler(rssHandler);
                    //on cré un flux de données à partir de l'URL définie plus haut
                    InputSource inputSource = new InputSource(getInputStream(rssUrl, context));

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

    public static AsyncTask<String,Void, List<Equipe>> scanListEquipes(final Context context)
    {
        return new AsyncTask<String, Void, List<Equipe>>()
        {
            //lorsqu'android est prêt, la tâche est executée ici
            @Override
            protected List<Equipe> doInBackground(String... urls) {
                try {
                    //à partir de cette URL
                    URL rssUrl = getURL("api-stbrice/?action=get_list_equipes", context);

                    //on construit un parseur (parcoureur) de contenu XML
                    SAXParserFactory factory = SAXParserFactory.newInstance();
                    SAXParser saxParser = factory.newSAXParser();
                    XMLReader xmlReader = saxParser.getXMLReader();

                    //grâce à notre lecteur de contenu de RSS
                    EquipeRSSHandler rssHandler = new EquipeRSSHandler();
                    //on informe notre parser quelle classe il devra utiliser lors de la lecture
                    //de chaque élément XML
                    xmlReader.setContentHandler(rssHandler);
                    //on cré un flux de données à partir de l'URL définie plus haut
                    InputSource inputSource = new InputSource(getInputStream(rssUrl, context));

                    //on demande à notre parser de parcourir tout le contenu XML renvoyé par
                    //le flux de données inputSource
                    xmlReader.parse(inputSource);


                    scanListJoueurs(rssHandler.getRssResult(), context);


                    //on retourne le résultat
                    return rssHandler.getRssResult();

                } catch (Exception e) {
                    e.printStackTrace();
                    return new ArrayList<>();
                }
            }
        };
    }

    private static List<Joueur> scanListJoueurs(List<Equipe> equipesList, final Context context)
    {
        try {
            //à partir de cette URL
            URL rssUrl = getURL("api-stbrice/?action=get_list_joueurs", context);

            //on construit un parseur (parcoureur) de contenu XML
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();

            //grâce à notre lecteur de contenu de RSS:
            JoueurRSSHandler rssHandler = new JoueurRSSHandler(equipesList);
            //on informe notre parser quelle classe il devra utiliser lors de la lecture
            //de chaque élément XML
            xmlReader.setContentHandler(rssHandler);
            //on cré un flux de données à partir de l'URL définie plus haut
            InputSource inputSource = new InputSource(getInputStream(rssUrl, context));

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


    public static AsyncTask<String,Void, List<Article>> scanArticles(final Context context)
    {
        return new AsyncTask<String, Void, List<Article>>()
        {
            //lorsqu'android est prêt, la tâche est executée ici
            @Override
            protected List<Article> doInBackground(String... urls) {
                try {
                    //à partir de cette URL
                    URL rssUrl = getURL("page-article/feed", context);

                    //on construit un parseur (parcoureur) de contenu XML
                    SAXParserFactory factory = SAXParserFactory.newInstance();
                    SAXParser saxParser = factory.newSAXParser();
                    XMLReader xmlReader = saxParser.getXMLReader();

                    //grâce à notre lecteur de contenu de RSS
                    ArticleRSSHandler rssHandler = new ArticleRSSHandler();
                    //on informe notre parser quelle classe il devra utiliser lors de la lecture
                    //de chaque élément XML
                    xmlReader.setContentHandler(rssHandler);
                    //on cré un flux de données à partir de l'URL définie plus haut
                    InputSource inputSource = new InputSource(getInputStream(rssUrl, context));

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

    public static AsyncTask<String,Void, List<Match>> scanNextMatchs(final Context context)
    {
        return new AsyncTask<String, Void, List<Match>>()
        {
            //lorsqu'android est prêt, la tâche est executée ici
            @Override
            protected List<Match> doInBackground(String... urls) {
                try {
                    //à partir de cette URL
                    URL rssUrl = getURL("api-stbrice/?action=get_next_match", context);

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
                    InputSource inputSource = new InputSource(getInputStream(rssUrl, context));

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

    public static AsyncTask<String,Void, List<Match>> scanLastMatchs(final Context context)
    {
        return new AsyncTask<String, Void, List<Match>>()
        {
            //lorsqu'android est prêt, la tâche est executée ici
            @Override
            protected List<Match> doInBackground(String... urls) {
                try {
                    //à partir de cette URL
                    URL rssUrl = getURL("api-stbrice/?action=get_last_match", context);

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
                    InputSource inputSource = new InputSource(getInputStream(rssUrl, context));

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
