package fr.handstbrice.handballstbrice;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    private CircleImageView tvEquipeGauche, tvEquipeDroite;
    private TextView tvScoreEquipeGauche, tvScoreEquipeDroite;
    private List<Equipe> equipes;
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

        try {
            equipes=FluxRSS.scanListEquipes().execute().get();
            List<Match> lastMatchs = FluxRSS.scanLastMatchs().execute().get();
            if (lastMatchs.size()>0) {
                Match m=lastMatchs.get(0);

                Equipe ed=getEquipeByName(m.getEquipeExterieure());
                tvEquipeDroite = (CircleImageView) findViewById(R.id.equipe_droite);
                if (ed!=null && ed.getSrcImg()!=null)
                    tvEquipeGauche.setImageURI(ed.getSrcImg());

                Equipe eg=getEquipeByName(m.getEquipeLocale());
                tvEquipeGauche = (CircleImageView) findViewById(R.id.equipe_gauche);
                if (eg!=null && eg.getSrcImg()!=null)
                    tvEquipeGauche.setImageURI(eg.getSrcImg());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private Equipe getEquipeByName(String name)
    {
        for (Equipe e : equipes)
            if (e.getNom().equals(name))
                return e;
        return null;

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
