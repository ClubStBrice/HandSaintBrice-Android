package fr.handstbrice.handballstbrice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.handstbrice.handballstbrice.model.Match;
import fr.handstbrice.handballstbrice.rss.FluxRSS;

public class DernierMatchActivite extends AppCompatActivity {

    private Match match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dernier_match_activite);
        List<Match>l= null;
        try {
            l = FluxRSS.scanLastMatchs().execute().get();
            if (l.size() > 0) {
                match = l.get(0);




            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}
