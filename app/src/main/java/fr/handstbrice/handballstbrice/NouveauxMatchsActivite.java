package fr.handstbrice.handballstbrice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.handstbrice.handballstbrice.model.Match;
import fr.handstbrice.handballstbrice.rss.FluxRSS;

public class NouveauxMatchsActivite extends AppCompatActivity {
    private List<Match> matches;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouveaux_matchs_activite);
        update();
    }

    private void update()
    {

            matches= FluxRSS.scanNextMatchs(this);
            LinearLayout ll=(LinearLayout)findViewById(R.id.contenuProchainsMatchs);
            for (Match m : matches)
            {
                View ligne=LayoutInflater.from(this).inflate(R.layout.nouveau_match_item,ll, false );
                CircleImageView equipe_gauche_prochain=ligne.findViewById(R.id.equipe_gauche_prochain);
                if (m.getUrlImgLocale()!=null)
                    new DownloadImageTask(equipe_gauche_prochain)
                            .execute(m.getUrlImgLocale().toString());


                TextView nom_equipe_gauche_prochain = (TextView)ligne.findViewById(R.id.nom_equipe_gauche_prochain);
                nom_equipe_gauche_prochain.setText(m.getEquipeLocale());

                TextView contenuProchainsMatchs = (TextView)ligne.findViewById(R.id.titreDernier_prochain_match);
                SimpleDateFormat df=new SimpleDateFormat(getString(R.string.date_format));
                SimpleDateFormat hf=new SimpleDateFormat(getString(R.string.heure_format));
                Date date=new Date(m.getDateUTC());
                contenuProchainsMatchs.setText(String.format(getString(R.string.date_match),df.format(date), hf.format(date)));

                CircleImageView equipe_droite_prochain = (CircleImageView) ligne.findViewById(R.id.equipe_droite_prochain);
                if (m.getUrlImgLocale()!=null)
                    new DownloadImageTask(equipe_droite_prochain)
                            .execute(m.getUrlImgExterieure().toString());


                TextView nom_equipe_droit_prochain = (TextView)ligne.findViewById(R.id.nom_equipe_droit_prochain);
                nom_equipe_droit_prochain.setText(m.getEquipeExterieure());
                ll.addView(ligne);
            }


    }
}
