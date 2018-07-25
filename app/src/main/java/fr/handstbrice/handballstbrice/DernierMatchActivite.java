package fr.handstbrice.handballstbrice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.handstbrice.handballstbrice.model.Equipe;
import fr.handstbrice.handballstbrice.model.Match;
import fr.handstbrice.handballstbrice.rss.FluxRSS;

public class DernierMatchActivite extends AppCompatActivity {

    private Match match;
    private List<Equipe> equipes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dernier_match_activite);

        List<Match>l= null;

            equipes=FluxRSS.scanListEquipes(this);
            l = FluxRSS.scanLastMatchs(this);
            if (l.size() > 0) {
                match = l.get(0);

                CircleImageView imgG=(CircleImageView)findViewById(R.id.equipe_gauche_dernier);
                if (match.getUrlImgLocale()!=null)
                    new DownloadImageTask(imgG)
                        .execute(match.getEquipeLocale().toString());

                TextView nom_equipe_gauche_dernier = (TextView)findViewById(R.id.nom_equipe_gauche_dernier);
                nom_equipe_gauche_dernier.setText(match.getEquipeLocale());

                TextView score_gauche_dernier = (TextView)findViewById(R.id.score_gauche_dernier);
                score_gauche_dernier.setText(match.getScoreEquipeLocale());

                TextView score_droit_dernier = (TextView)findViewById(R.id.score_droit_dernier);
                score_droit_dernier.setText(match.getScoreEquipeExterieure());

                CircleImageView imgD=(CircleImageView)findViewById(R.id.equipe_droite_dernier);
                imgD.setImageURI(getEquipeByName(match.getEquipeLocale()).getSrcImg());

                if (match.getUrlImgExterieure()!=null)
                    new DownloadImageTask(imgD)
                            .execute(match.getUrlImgExterieure().toString());

                TextView nom_equipe_droit_dernier = (TextView)findViewById(R.id.nom_equipe_droit_dernier);
                nom_equipe_droit_dernier.setText(match.getEquipeExterieure());

                TextView date_dernier_match = (TextView)findViewById(R.id.date_dernier_match);
                SimpleDateFormat df=new SimpleDateFormat(getString(R.string.date_format));
                SimpleDateFormat hf=new SimpleDateFormat(getString(R.string.heure_format));
                Date date=new Date(match.getDateUTC());
                date_dernier_match.setText(String.format(getString(R.string.date_match),df.format(date), hf.format(date)));





            }



    }

    private Equipe getEquipeByName(String name)
    {
        for (Equipe e : equipes)
            if (e.getNom().equals(name))
                return e;
        return null;

    }
}
