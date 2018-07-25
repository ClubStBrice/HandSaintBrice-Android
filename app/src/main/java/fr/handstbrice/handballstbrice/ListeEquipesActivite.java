package fr.handstbrice.handballstbrice;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import fr.handstbrice.handballstbrice.model.Article;
import fr.handstbrice.handballstbrice.model.Equipe;
import fr.handstbrice.handballstbrice.model.Joueur;
import fr.handstbrice.handballstbrice.rss.FluxRSS;

public class ListeEquipesActivite extends AppCompatActivity {

    private List<Equipe> listEquipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_equipes_activite);
        update();
    }

    private void update() {
        listEquipe = FluxRSS.scanListEquipes(this);

        LinearLayout ll = findViewById(R.id.layoutEquipes);
        for (final Equipe e : listEquipe) {
            if (e.getJoueurs().size()==0)
                continue;
            LinearLayout ligneEq = (LinearLayout)LayoutInflater.from(this).inflate(R.layout.equipe_item,ll, false);

            TextView titre_equipe_item = (TextView)ligneEq.findViewById(R.id.titre_equipe_item);
            titre_equipe_item.setText(e.getNom());
            LinearLayout layoutEquipe=ligneEq.findViewById(R.id.layoutListeJoueurs);

            for (Joueur j : e.getJoueurs())
            {
                View ligneJr = LayoutInflater.from(this).inflate(R.layout.joueur_item,layoutEquipe, false);


                ImageView imageJoueur = (ImageView)ligneJr.findViewById(R.id.imageJoueur);
                //new DownloadImageTask(imageJoueur).execute("https://www.cgl.fr/350-thickbox_default/camion-benne-cabine-simple.jpg");

                TextView nom_joueur = (TextView)ligneJr.findViewById(R.id.nom_joueur);
                nom_joueur.setText(j.getNom());

                TextView prenom_joueur = (TextView)ligneJr.findViewById(R.id.prenom_joueur);
                prenom_joueur.setText(j.getPrenom());
                layoutEquipe.addView(ligneJr);
            }

            ll.addView(ligneEq);

        }
    }
}
