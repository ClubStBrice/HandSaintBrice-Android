package fr.handstbrice.handballstbrice.rss;

import android.os.AsyncTask;

import java.util.List;

import fr.handstbrice.handballstbrice.model.Match;

public class FluxRSS {
    public static AsyncTask<String,Void, Match> scanLastMatch()
    {
        return new AsyncTask<String, Void, Match>()
        {
            //lorsqu'android est prêt, la tâche est executée ici
            @Override
            protected Match doInBackground(String... urls) {
                try {
                    //à partir de cette URL
                    URL rssUrl = new URL("https://www.lemonde.fr/culture/rss_full.xml");
                    //on construit un parseur (parcoureur) de contenu XML
                    SAXParserFactory factory = SAXParserFactory.newInstance();
                    SAXParser saxParser = factory.newSAXParser();
                    XMLReader xmlReader = saxParser.getXMLReader();

                    //grâce à notre lecteur de contenu de RSS
                    RSSHandler rssHandler = new RSSHandler();
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
                    return e.toString();
                }
            }
            //lorsque la tâche est executée, cette fonction est appelée avec en paramètre le résultat
            //renvoyé par la tâche
            @Override
            protected void onPostExecute(String feed) {
                rssResult=feed;
                //on place dans notre zone d'affiche du code HTML provenant
                // du parcour de notre flux RSS. Le HTML lui même a été généré dans la classe
                // RSSHandler
                textViewRSS.setText(Html.fromHtml(feed));

            }
        };
    }
}
