package fr.handstbrice.handballstbrice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
            View ligneEq = LayoutInflater.from(this).inflate(R.layout.equipe_item,ll);

            TextView titre_equipe_item = (TextView)ligneEq.findViewById(R.id.titre_equipe_item);
            titre_equipe_item.setText(e.getNom());

            for (Joueur j : e.getJoueurs())
            {
                View ligneJr = LayoutInflater.from(this).inflate(R.layout.joueur_item,ll);


                ImageView imageJoueur = (ImageView)ligneJr.findViewById(R.id.imageJoueur);
                imageJoueur.setImageURI(j.getEquipe());

                TextView nom_joueur = (TextView)ligneJr.findViewById(R.id.nom_joueur);
                nom_joueur.setText(j.getNom());

                TextView prenom_joueur = (TextView)ligneJr.findViewById(R.id.prenom_joueur);
                prenom_joueur.setText(j.getPrenom());
            }



        }
    }
}
