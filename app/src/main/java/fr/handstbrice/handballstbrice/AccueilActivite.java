package fr.handstbrice.handballstbrice;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.handstbrice.handballstbrice.model.Equipe;
import fr.handstbrice.handballstbrice.model.Match;
import fr.handstbrice.handballstbrice.rss.FluxRSS;

public class AccueilActivite extends AppCompatActivity {
    Intent mServiceIntent;
    private MatchCheckerService matchCheckerService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_activite);
        matchCheckerService = new MatchCheckerService();
        mServiceIntent = new Intent(this, matchCheckerService.getClass());
        if (!isMyServiceRunning(matchCheckerService.getClass())) {
            startService(mServiceIntent);
        }

        update();

    }

    private void update()
    {
        List<Match> lastMatchs = FluxRSS.scanLastMatchs(this);
        if (lastMatchs.size()>0) {
            Match m=lastMatchs.get(0);


            CircleImageView tvEquipeDroite = (CircleImageView) findViewById(R.id.equipe_droite);
            if (m.getUrlImgExterieure()!=null)
                new DownloadImageTask(tvEquipeDroite)
                        .execute(m.getUrlImgExterieure().toString());


            TextView tvScoreEquipeDroite = (TextView) findViewById(R.id.score_droit);
            tvScoreEquipeDroite.setText(""+m.getScoreEquipeExterieure());

            CircleImageView tvEquipeGauche = (CircleImageView) findViewById(R.id.equipe_gauche);
            if (m.getUrlImgLocale()!=null)
                new DownloadImageTask(tvEquipeGauche)
                        .execute(m.getUrlImgLocale().toString());

            TextView tvScoreEquipeGauche = (TextView) findViewById(R.id.score_gauche);
            tvScoreEquipeGauche.setText(""+m.getScoreEquipeLocale());

            View.OnClickListener listener= new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i= new Intent(AccueilActivite.this, DernierMatchActivite.class);
                    startActivity(i);
                }
            };
            tvEquipeDroite.setOnClickListener(listener);
            tvScoreEquipeDroite.setOnClickListener(listener);
            tvEquipeGauche.setOnClickListener(listener);
            tvScoreEquipeGauche.setOnClickListener(listener);
            findViewById(R.id.titreDernier).setOnClickListener(listener);
            findViewById(R.id.separation).setOnClickListener(listener);
        }

        List<Match> nextMatchs = FluxRSS.scanNextMatchs(this);
        if (nextMatchs.size()>0) {
            Match m=nextMatchs.get(0);

            CircleImageView tvEquipeDroite = (CircleImageView) findViewById(R.id.equipe_prochain_droite);
            if (m.getUrlImgExterieure()!=null)
                new DownloadImageTask(tvEquipeDroite)
                        .execute(m.getUrlImgExterieure().toString());

            CircleImageView tvEquipeGauche = (CircleImageView) findViewById(R.id.equipe_prochain_gauche);
            if (m.getUrlImgLocale()!=null)
                new DownloadImageTask(tvEquipeGauche)
                        .execute(m.getUrlImgLocale().toString());

            View.OnClickListener listener= new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i= new Intent(AccueilActivite.this, NouveauxMatchsActivite.class);
                    startActivity(i);
                }
            };
            tvEquipeDroite.setOnClickListener(listener);
            tvEquipeGauche.setOnClickListener(listener);
            findViewById(R.id.separationTitre).setOnClickListener(listener);
        }

        Button partenaires=(Button)findViewById(R.id.partenaires);
        partenaires.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(AccueilActivite.this, PartenairesActivite.class);
                startActivity(i);
            }
        });

        Button equipes=(Button)findViewById(R.id.equipes);
        equipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(AccueilActivite.this, ListeEquipesActivite.class);
                startActivity(i);
            }
        });

        Button articles=(Button)findViewById(R.id.articles);
        articles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(AccueilActivite.this, ArticlesActivite.class);
                startActivity(i);
            }
        });
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }

}
